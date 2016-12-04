package net.davidsteinsland;

import org.apache.commons.configuration.*;

public class ApplicationSettings {
  private static Configuration config;

  public static int SERVER_PORT = 8484;

  static {
    try {
      config = new PropertiesConfiguration("server.properties");

      SERVER_PORT = config.getInt("server.port");
    } catch (ConfigurationException e) {
      System.err.println(e.getMessage());
    }
  }

  public static void setPortNumber(int port) {
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
