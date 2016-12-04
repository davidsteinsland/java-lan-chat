package net.davidsteinsland;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.beans.*;

import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.SocketException;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

/*
 * Created by JFormDesigner on Sat Dec 03 20:39:43 CET 2016
 */



/**
 * @author David Steinsland
 */
public class ServerOverviewFrame extends JFrame {
  public ServerOverviewFrame() {
    initComponents();

    startUdpServer(ApplicationSettings.SERVER_ADDRESS, ApplicationSettings.SERVER_PORT);
    startTcpServer(ApplicationSettings.SERVER_PORT);
  }

  private void startUdpServer(InetAddress address, int port) {
    try {
      responderTask = new ServerSocketResponderTask(messagesArea, address, port);
      responderTask.execute();
    } catch (SocketException e) {
      System.err.println(e.getMessage());
    }
  }

    private void startTcpServer(int port) {
      try {
        server = new ServerSocket(port);

        acceptTask = new ServerSocketAcceptTask(connectedClientsList, messagesArea, clientListModel, server);
        acceptTask.execute();
        acceptTask.addPropertyChangeListener(new PropertyChangeListener() {
          public void propertyChange(PropertyChangeEvent evt) {
            System.out.println(evt.getPropertyName());
            System.out.println(evt.toString());
          }
        });
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void stopUdpServer() {
    responderTask.getSocket().close();
    responderTask.cancel(false);
  }

  private void stopTcpServer() {
    /* stop accepting clients */
    acceptTask.cancel(false);

    try {
      server.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }

  private void connectedClientsListValueChanged(ListSelectionEvent e) {
    ListSelectionModel lsm = ((JList)e.getSource()).getSelectionModel();
    boolean isAdjusting = e.getValueIsAdjusting();

    if (isAdjusting || lsm.isSelectionEmpty()) {
      return;
    }

    closeSocketButton.setEnabled(true);
  }

  private void closeSocketButtonActionPerformed(ActionEvent evt) {
    ListSelectionModel lsm = connectedClientsList.getSelectionModel();

    if (lsm.isSelectionEmpty()) {
      return;
    }

    /* we only allow a single item to be selected */
    int index = lsm.getMinSelectionIndex();

    System.out.println(connectedClientsList.getSelectedValue());
    System.out.println(clientListModel.getSocketAt(index));

    try {
      Socket s = clientListModel.getSocketAt(index);
      s.close();
      clientListModel.remove(s);
      connectedClientsList.updateUI();
    } catch (IndexOutOfBoundsException e) {
      /* no nothing */
    } catch (IOException e) {
      UIMessage.showErrorMessage(e.getMessage());
    }
  }

  private void shutdownButtonActionPerformed(ActionEvent e) {
    stopUdpServer();
    stopTcpServer();

    FrameManager.invokeFrame(new ServerFrame());
  }

  private void quitButtonActionPerformed(ActionEvent e) {
    System.exit(0);
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - David Steinsland
    dialogPane = new JPanel();
    contentPanel = new JPanel();
    label1 = new JLabel();
    scrollPane1 = new JScrollPane();
    connectedClientsList = new JList<>();
    scrollPane3 = new JScrollPane();
    messagesArea = new JTextArea();
    closeSocketButton = new JButton();
    buttonBar = new JPanel();
    shutdownButton = new JButton();
    quitButton = new JButton();

    //======== this ========
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
        contentPanel.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 188, 0};
        ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Connected clients:");
        contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane1 ========
        {

          //---- connectedClientsList ----
          connectedClientsList.addListSelectionListener(e -> connectedClientsListValueChanged(e));
          scrollPane1.setViewportView(connectedClientsList);
        }
        contentPanel.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 5, 5), 0, 0));

        //======== scrollPane3 ========
        {
          scrollPane3.setViewportView(messagesArea);
        }
        contentPanel.add(scrollPane3, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 5, 0), 0, 0));

        //---- closeSocketButton ----
        closeSocketButton.setText("Close selected socket");
        closeSocketButton.setEnabled(false);
        closeSocketButton.addActionListener(e -> closeSocketButtonActionPerformed(e));
        contentPanel.add(closeSocketButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 5), 0, 0));
      }
      dialogPane.add(contentPanel, BorderLayout.CENTER);

      //======== buttonBar ========
      {
        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
        ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

        //---- shutdownButton ----
        shutdownButton.setText("Shutdown");
        shutdownButton.addActionListener(e -> shutdownButtonActionPerformed(e));
        buttonBar.add(shutdownButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 5), 0, 0));

        //---- quitButton ----
        quitButton.setText("Quit");
        quitButton.addActionListener(e -> quitButtonActionPerformed(e));
        buttonBar.add(quitButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 0), 0, 0));
      }
      dialogPane.add(buttonBar, BorderLayout.SOUTH);
    }
    contentPane.add(dialogPane, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(getOwner());
    // JFormDesigner - End of component initialization  //GEN-END:initComponents

    connectedClientsList.setModel(clientListModel);
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - David Steinsland
  private JPanel dialogPane;
  private JPanel contentPanel;
  private JLabel label1;
  private JScrollPane scrollPane1;
  private JList<String> connectedClientsList;
  private JScrollPane scrollPane3;
  private JTextArea messagesArea;
  private JButton closeSocketButton;
  private JPanel buttonBar;
  private JButton shutdownButton;
  private JButton quitButton;
  // JFormDesigner - End of variables declaration  //GEN-END:variables

  private ServerSocket server;
  private ServerSocketResponderTask responderTask;
  private ServerSocketAcceptTask acceptTask;
  private ClientListModel clientListModel = new ClientListModel();
}
