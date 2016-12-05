package net.davidsteinsland;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import java.io.IOException;
import java.io.OutputStream;

/*
 * Created by JFormDesigner on Thu Dec 01 15:13:44 CET 2016
 */



/**
 * @author David Steinsland
 */
public class ChatFrame extends JFrame {
  public ChatFrame(InetAddress server, int port) throws IOException {
    this.server = server;
    this.socket = new Socket(server, port);

    initComponents();

    chatReader = new ChatReader(messagesArea, socket);
    chatReader.execute();
  }

  private void messageTextMouseClicked(MouseEvent e) {
    if (messageText.getText().equals("enter your text here ...")) {
      messageText.setText("");
    }
  }

  private void sendButtonActionPerformed(ActionEvent evt) {
    try {
      OutputStream os = socket.getOutputStream();
      String text = messageText.getText();

      if (text.equals("enter your text here ...")) {
        return;
      }

      System.out.println("Writing: " + text);

      text = text + "\n";
      os.write(text.getBytes());
      os.flush();
    } catch (IOException e) {
      UIMessage.showErrorMessage(e.getMessage());
      disconnectButtonActionPerformed(null);
    }
  }

  private void disconnectButtonActionPerformed(ActionEvent evt) {
    try {
      socket.close();
    } catch (IOException e) {
      UIMessage.showErrorMessage(e.getMessage());
    }

    chatReader.cancel(false);

    FrameManager.invokeFrame(new MainFrame());
  }

  private void messageTextFocusLost(FocusEvent e) {
    if (messageText.getText().equals("")) {
      messageText.setText("enter your text here ...");
    }
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - David Steinsland
    dialogPane = new JPanel();
    contentPanel = new JPanel();
    scrollPane1 = new JScrollPane();
    messagesArea = new JTextArea();
    scrollPane3 = new JScrollPane();
    list1 = new JList<>();
    buttonBar = new JPanel();
    scrollPane2 = new JScrollPane();
    messageText = new JTextArea();
    sendButton = new JButton();
    disconnectButton = new JButton();

    //======== this ========
    setMinimumSize(new Dimension(400, 230));
    setTitle("Connected to server");
    Container contentPane = getContentPane();
    contentPane.setLayout(new BorderLayout());

    //======== dialogPane ========
    {
      dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

      // JFormDesigner evaluation mark
      dialogPane.setBorder(new javax.swing.border.CompoundBorder(
        new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
          "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
          javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
          java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

      dialogPane.setLayout(new BorderLayout());

      //======== contentPanel ========
      {
        contentPanel.setLayout(new BorderLayout(1, 5));

        //======== scrollPane1 ========
        {
          scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
          scrollPane1.setViewportView(messagesArea);
        }
        contentPanel.add(scrollPane1, BorderLayout.CENTER);

        //======== scrollPane3 ========
        {
          scrollPane3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

          //---- list1 ----
          list1.setModel(new AbstractListModel<String>() {
            String[] values = {
              "OSX ",
              "Windows PC"
            };
            @Override
            public int getSize() { return values.length; }
            @Override
            public String getElementAt(int i) { return values[i]; }
          });
          list1.setBorder(new EmptyBorder(5, 5, 5, 5));
          list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
          scrollPane3.setViewportView(list1);
        }
        contentPanel.add(scrollPane3, BorderLayout.EAST);
      }
      dialogPane.add(contentPanel, BorderLayout.CENTER);

      //======== buttonBar ========
      {
        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
        ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

        //======== scrollPane2 ========
        {

          //---- messageText ----
          messageText.setText("enter your text here ...");
          messageText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              messageTextMouseClicked(e);
            }
          });
          messageText.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
              messageTextFocusLost(e);
            }
          });
          scrollPane2.setViewportView(messageText);
        }
        buttonBar.add(scrollPane2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 5), 0, 0));

        //---- sendButton ----
        sendButton.setText("Send");
        sendButton.addActionListener(e -> sendButtonActionPerformed(e));
        buttonBar.add(sendButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 5), 0, 0));

        //---- disconnectButton ----
        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(e -> disconnectButtonActionPerformed(e));
        buttonBar.add(disconnectButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 0), 0, 0));
      }
      dialogPane.add(buttonBar, BorderLayout.SOUTH);
    }
    contentPane.add(dialogPane, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - David Steinsland
  private JPanel dialogPane;
  private JPanel contentPanel;
  private JScrollPane scrollPane1;
  private JTextArea messagesArea;
  private JScrollPane scrollPane3;
  private JList<String> list1;
  private JPanel buttonBar;
  private JScrollPane scrollPane2;
  private JTextArea messageText;
  private JButton sendButton;
  private JButton disconnectButton;
  // JFormDesigner - End of variables declaration  //GEN-END:variables

  private InetAddress server;

  private Socket socket;

  private ChatReader chatReader;
}
