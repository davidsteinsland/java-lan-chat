package net.davidsteinsland;

import javax.swing.SwingWorker;
import javax.swing.JList;
import javax.swing.JTextArea;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import java.io.IOException;

public class ServerSocketAcceptTask extends SwingWorker<Set<Socket>, Socket> {

  private ServerSocket server;

  private Set<Socket> clients;

  private List<Thread> clientThreads;

  private JList<String> clientsList;

  private JTextArea messagesArea;

  private ClientListModel model;

  public ServerSocketAcceptTask(JList<String> clientsList, JTextArea messagesArea, ClientListModel model, ServerSocket socket) {
    server = socket;
    clients = Collections.synchronizedSet(new HashSet<>());
    clientThreads = new ArrayList<>();

    this.clientsList = clientsList;
    this.model = model;
    this.messagesArea = messagesArea;
  }

  @Override
  protected void done() {
    for (Socket s : clients) {
      try {
        s.close();
      } catch (IOException e) {
        System.err.println("Cannot close socket: " + e.getMessage());
      }
    }
  }

  @Override
  protected void process(List<Socket> sockets) {
    for (Socket s : sockets) {
      model.add(s);
      clientsList.updateUI();

      ServerSocketReaderTask w = new ServerSocketReaderTask(messagesArea, clients, s);
      w.execute();
    }
  }

  @Override
  public Set<Socket> doInBackground() throws IOException {
    while (!isCancelled()) {
      Socket s = server.accept();

      if (clients.add(s)) {
        publish(s);
      }
    }

    return clients;
  }
}
