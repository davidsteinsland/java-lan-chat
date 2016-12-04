package net.davidsteinsland;

import javax.swing.AbstractListModel;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.io.IOException;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

public class ServerListModel extends AbstractListModel<String> {

	private List<InetAddress> servers;

	public ServerListModel() {
		servers = new ArrayList<>();
	}

	public void clear() {
		servers.clear();
	}

	public void add(InetAddress server) {
		servers.add(server);
	}

	public InetAddress getAddressAt(int i) {
		return servers.get(i);
	}

	@Override
	public int getSize() {
		return servers.size();
	}

	@Override
	public String getElementAt(int i) {
		InetAddress addr = servers.get(i);

		String serverName = "[N/A]";

		try {
			Enumeration<NetworkInterface> netints = NetworkInterface.getNetworkInterfaces();
			while (netints.hasMoreElements()) {
				NetworkInterface netint = netints.nextElement();

				if (addr.isReachable(netint, 0, 500)) {
					serverName = netint.getDisplayName();
					break;
				}
			}
		} catch (SocketException e) {
			System.err.println("Socket exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("IO exception: " + e.getMessage());
		}

		return serverName + " - " + addr.toString();
	}
}
