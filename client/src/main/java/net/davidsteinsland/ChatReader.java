package net.davidsteinsland;

import javax.swing.SwingWorker;
import javax.swing.JTextArea;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.List;

public class ChatReader extends SwingWorker<Void, String> {
  private Socket socket;

  private JTextArea messagesArea;

  public ChatReader(JTextArea messagesArea, Socket socket) {
    this.socket = socket;
    this.messagesArea = messagesArea;
  }

  @Override
  protected void process(List<String> lines) {
    for (String line : lines) {
      messagesArea.setText(messagesArea.getText() + line + "\n");
    }
  }

  @Override
  public Void doInBackground() throws IOException {
    InputStream is = socket.getInputStream();
    BufferedReader in = new BufferedReader(new InputStreamReader(is));

    String line;
    while ((line = in.readLine()) != null) {
      publish(line);
    }
    /* if line = null, server disconnected on us */
    return null;
  }
}
