package net.davidsteinsland;

import org.apache.commons.configuration.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ApplicationSettings {
	private static Configuration config;

	public static InetAddress SERVER_ADDRESS;

	public static int SERVER_PORT = 8484;

	static {
		try {
			config = new PropertiesConfiguration("server.properties");

			SERVER_PORT = config.getInt("server.port");
			SERVER_ADDRESS = InetAddress.getByName("0.0.0.0");
		} catch (ConfigurationException e) {
			System.err.println(e.getMessage());
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void setPortNumber(int port) {
		System.out.println("Setting port number to " + port);

		SERVER_PORT = port;
		config.setProperty("server.port", port);
	}

	public static void persist() {
		try {
			((AbstractFileConfiguration)config).save();
		} catch (ConfigurationException e) {
			System.err.println(e.getMessage());
		}
	}
}
