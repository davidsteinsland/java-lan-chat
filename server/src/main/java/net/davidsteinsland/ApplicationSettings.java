package net.davidsteinsland;

import org.apache.commons.configuration.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Files;

public class ApplicationSettings {
  private static Configuration config;

  public static InetAddress SERVER_ADDRESS;

  public static int SERVER_PORT = 8484;

  static {
    try {
      File configFile = getConfigurationFile();

      if (!configFile.exists()) {
        createDefaultConfigFile(configFile);
      }

      config = new PropertiesConfiguration("server.properties");

      SERVER_PORT = config.getInt("server.port");
      SERVER_ADDRESS = InetAddress.getByName("0.0.0.0");
    } catch (ConfigurationException e) {
      System.err.println(e.getMessage());
    } catch (UnknownHostException e) {
      System.err.println(e.getMessage());
    }
  }

  private static void createDefaultConfigFile(File configFile) {
    /* load properties from JAR */
    ClassLoader loader = ApplicationSettings.class.getClassLoader();
    URL res = loader.getResource("server.properties");
    try (InputStream in = res.openStream()) {
      /* copy stream to file */
      Files.copy(in, configFile.toPath());
    } catch (IOException e) {
      System.err.println("IO error: " + e.getMessage());
    }
  }

  public static File getConfigurationFile() {
    /* check the current path */
    return new File(System.getProperty("user.dir"), "server.properties");
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
