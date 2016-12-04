package net.davidsteinsland;

import javax.swing.AbstractListModel;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

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

		String serverName = null;

		try {
			NetworkInterface netint = NetworkInterface.getByInetAddress(addr);
			if (netint != null) {
				serverName = netint.getDisplayName();
			} else {
				serverName = "[N/A]";
			}

			serverName = serverName + " - " + addr.toString();
		} catch (SocketException e) {
			System.err.println("Socket exception: " + e.getMessage());
		}

		return serverName;
	}
}
