package net.davidsteinsland;

import javax.swing.AbstractListModel;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class ClientListModel extends AbstractListModel<String> {

  private List<Socket> clients;

  public ClientListModel() {
    clients = new ArrayList<>();
  }

  public void clear() {
    clients.clear();
  }

  public void add(Socket socket) {
    clients.add(socket);
  }

  public void remove(Socket socket) {
    clients.remove(socket);
  }

  public Socket getSocketAt(int index) {
    return clients.get(index);
  }

  @Override
  public int getSize() {
    return clients.size();
  }

  @Override
  public String getElementAt(int i) {
    Socket socket = clients.get(i);
    return socket.getInetAddress().toString();
  }
}
