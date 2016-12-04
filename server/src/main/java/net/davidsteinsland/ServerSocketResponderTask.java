package net.davidsteinsland;

import java.net.SocketException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.io.IOException;

import java.util.List;

import javax.swing.SwingWorker;
import javax.swing.JTextArea;

public class ServerSocketResponderTask extends SwingWorker<Void, DatagramPacket> {

  private DatagramSocket socket;

  private JTextArea messagesArea;

  public ServerSocketResponderTask(JTextArea messagesArea, InetAddress address, int port) throws SocketException {
    System.out.println("Starting UDP server at " + address + ":" + port);
    socket = new DatagramSocket(port, address);
    this.messagesArea = messagesArea;
  }

  public DatagramSocket getSocket() {
    return socket;
  }

  @Override
  public void done() {
    if (!socket.isClosed()) {
      socket.close();
    }
  }

  @Override
  protected void process(List<DatagramPacket> packets) {
    for (DatagramPacket packet : packets) {
      synchronized(messagesArea) {
        messagesArea.append("Responding to broadcast from " + packet.getAddress() + ":" + packet.getPort() + "\n");
      }
    }
  }

  @Override
  public Void doInBackground() {
    while (!isCancelled() && !socket.isClosed()) {
      try {
        byte[] recvBuf = new byte[64];
        /* receive broadcast message */
        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
        socket.receive(packet);

        String message = new String(packet.getData()).trim();
        if (message.equals("DISCOVERY_REQUEST")) {
          byte[] sendData = "DISCOVERY_RESPONSE".getBytes();

          publish(packet);

          /* send unicast message to the specific network device */
          DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
          socket.send(sendPacket);
        }
      } catch (SocketException e) {
        System.err.println("Socket error: " + e.getMessage());
      } catch (IOException e) {
        System.err.println("IO error: " + e.getMessage());
        e.printStackTrace();
      }
    }

    return null;
  }

}
