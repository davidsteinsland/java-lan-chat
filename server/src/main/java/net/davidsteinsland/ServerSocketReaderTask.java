package net.davidsteinsland;

import javax.swing.SwingWorker;
import javax.swing.JTextArea;

import java.net.Socket;
import java.util.Set;
import java.util.List;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ServerSocketReaderTask extends SwingWorker<Void,String> {

  private Set<Socket> clients;

  private Socket socket;

  private JTextArea messagesArea;

  public ServerSocketReaderTask(JTextArea messagesArea, Set<Socket> clients, Socket socket) {
    this.socket = socket;
    this.clients = clients;
    this.messagesArea = messagesArea;
  }

  @Override
  protected void process(List<String> messages) {
    for (String m : messages) {
      messagesArea.append(m);
    }
  }

  @Override
  public Void doInBackground() {
    /* when a client is connected, we should send it some basic
        information about the server first:

        - list of clients connected
        - name of the server, something like a MOTD
    */

    try {
      InputStream is = socket.getInputStream();
      BufferedReader in = new BufferedReader(new InputStreamReader(is));

      String line;
      while ((line = in.readLine()) != null) {
        line = socket.getInetAddress().toString() + " - " + line + "\n";

        publish(line);

        /* echo message to all clients */
        synchronized(clients) {
          for (Socket s : clients) {
            OutputStream os = s.getOutputStream();
            os.write(line.getBytes());
            os.flush();
          }
        }
      }
      /* if line = null, client disconnected on us */
      clients.remove(socket);
    } catch (IOException e) {
      System.err.println("ClientReader: " + e.getMessage());
    }

    return null;
  }
}
