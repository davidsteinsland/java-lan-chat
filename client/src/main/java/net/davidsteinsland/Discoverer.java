package net.davidsteinsland;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;

import javax.swing.SwingWorker;
import javax.swing.JList;

public class Discoverer implements AutoCloseable {

	private int port;

	private DatagramSocket socket = null;

	private Thread senderThread;

	private Thread receiverThread;

	private Sender sender;

	private Receiver receiver;

	public Discoverer(int port) {
		this.port = port;
	}

	private void initializeSocket() throws SocketException {
		socket = new DatagramSocket(null);
		socket.setBroadcast(true);

		System.out.println(socket.isBound());
		System.out.println(socket.isConnected());
	}

	public void close() throws InterruptedException {
		if (socket != null) {
			/* cause the Receiver to stop, because it is most likely
				being blocked in a socket.receive() call */
			System.out.println("Closing");
			socket.close();

			if (receiverThread != null) {
				/* should probably check whether the thread is running */
				receiverThread.join();
				receiverThread = null;
			}

			senderThread = null;
			socket = null;
		}
	}

	public void discover(JList<String> serverList, ServerListModel model) throws SocketException, InterruptedException {
		List<InetAddress> addresses = new ArrayList<>();

		if (socket == null) {
			initializeSocket();
		}

		try {
			addresses.add(InetAddress.getByName("255.255.255.255"));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host error: " + e.getMessage());
		}

		try {
			Enumeration<NetworkInterface> it = NetworkInterface.getNetworkInterfaces();
			while (it.hasMoreElements()) {
				NetworkInterface netint = it.nextElement();

				if (!netint.isUp()) {
					System.out.println("Interface is down, skipping");
					continue;
				}

				List<InterfaceAddress> interfaceAddresses = netint.getInterfaceAddresses();
		        for (InterfaceAddress interfaceAddress : interfaceAddresses) {
		        	InetAddress broadcast = interfaceAddress.getBroadcast();

		        	if (broadcast == null) {
		        		continue;
		        	}

		        	addresses.add(broadcast);
		        }
			}
		} catch (SocketException e) {
			System.err.println("Error fetching interfaces: " + e.getMessage());
		}

		sender = new Sender(socket, addresses, port);
		receiver = new Receiver(serverList, model, socket);

		senderThread = new Thread(sender);
		receiverThread = new Thread(receiver);

		System.out.println("Sending packets");
		senderThread.start();
		senderThread.join();

		System.out.println("Receiving packets");
		receiverThread.start();
	}

	public Set<InetAddress> stopDiscover() throws InterruptedException {
		close();
		return receiver.getServers();
	}

	private static class Sender implements Runnable {
		private DatagramSocket socket;

		private List<InetAddress> addresses;

		private int port;

		public Sender(DatagramSocket socket, List<InetAddress> addresses, int port) {
			this.socket = socket;
			this.addresses = addresses;
			this.port = port;
		}

		@Override
		public void run() {
			byte[] msg = "DISCOVERY_REQUEST".getBytes();
			for (InetAddress addr : addresses) {
				try {
					DatagramPacket packet = new DatagramPacket(msg, msg.length, addr, port);
					socket.send(packet);

					System.out.println(socket.isBound());
					System.out.println(socket.getLocalPort());
					System.out.println(socket.isConnected());
				} catch (IOException e) {
					System.err.println("Sender: " + e.getMessage());
				}
			}
		}
	}

	private static class Receiver extends SwingWorker<Set<InetAddress>, InetAddress> {
		private DatagramSocket socket;

		private JList<String> serverList;

		private ServerListModel serverListModel;

		private Set<InetAddress> servers;

		private static int counter = 1;

		public Receiver(JList<String> serverList, ServerListModel model, DatagramSocket socket) {
			this.socket = socket;
			this.servers = new HashSet<>();
			this.serverList = serverList;
			this.serverListModel = model;
		}

		public Set<InetAddress> getServers() {
			return servers;
		}

		@Override
		protected void process(List<InetAddress> servers) {
			for (InetAddress addr : servers) {
				serverListModel.add(addr);
				serverList.updateUI();
			}
		}

		@Override
		public Set<InetAddress> doInBackground() {
			serverListModel.clear();

			while (!socket.isClosed() && !isCancelled()) {
				byte recvbuf[] = new byte[64];
				try {
					DatagramPacket recv = new DatagramPacket(recvbuf, recvbuf.length);
					/* if the socket is closed while trying to receive,
						the receive will throw an SocketException */
					socket.receive(recv);

					InetAddress recvAddr = recv.getAddress();

					if (servers.add(recvAddr)) {
						publish(recvAddr);
					}
				} catch (SocketException e) {
					System.err.println("Socket error: " + e.getMessage());
				} catch (IOException e) {
					System.err.println("IO error: " + e.getMessage());
				}
			}

			return servers;
		}
	}
}
