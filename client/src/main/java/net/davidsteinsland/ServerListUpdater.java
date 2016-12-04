package net.davidsteinsland;

import javax.swing.SwingWorker;
import javax.swing.JList;
import javax.swing.JButton;

import java.util.Set;

import java.net.SocketException;
import java.net.InetAddress;

public class ServerListUpdater extends SwingWorker<Set<InetAddress>, Void> {

	private int port;

	private JButton refreshButton;

	private JList<String> serverList;

	private ServerListModel model;

	public ServerListUpdater(int port, JButton refreshButton, JList<String> serverList, ServerListModel model) {
		this.port = port;
		this.refreshButton = refreshButton;
		this.serverList = serverList;
		this.model = model;
	}

	@Override
	public Set<InetAddress> doInBackground() {
		refreshButton.setEnabled(false);
		String text = refreshButton.getText();
		refreshButton.setText("Searching ...");

		Set<InetAddress> servers = null;

		try (Discoverer d = new Discoverer(port)) {
			d.discover(serverList, model);

			Thread.sleep(2000);

			servers = d.stopDiscover();
		} catch (SocketException e) {
			System.err.println("Socket error: " + e.getMessage());
		} catch (InterruptedException e) {
			System.err.println("Error: " + e.getMessage());
		}

		refreshButton.setEnabled(true);
		refreshButton.setText(text);

		return servers;
	}
}
