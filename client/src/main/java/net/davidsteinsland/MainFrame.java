package net.davidsteinsland;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;

/*
 * Created by JFormDesigner on Thu Dec 01 15:13:43 CET 2016
 */



/**
 * @author David Steinsland
 */
public class MainFrame extends JFrame {
  public MainFrame() {
    initComponents();
  }

  private void serverListValueChanged(ListSelectionEvent e) {
    ListSelectionModel lsm = ((JList)e.getSource()).getSelectionModel();
    boolean isAdjusting = e.getValueIsAdjusting();

    if (isAdjusting || lsm.isSelectionEmpty()) {
      return;
    }

    /* we only allow a single item to be selected */
    int index = lsm.getMinSelectionIndex();

    System.out.println(serverList.getSelectedValue());
    System.out.println(serverListModel.getAddressAt(index));
  }

  private void updateServerList() {
    serverList.getSelectionModel().clearSelection();
    ServerListUpdater w = new ServerListUpdater(ApplicationSettings.SERVER_PORT, refreshButton, serverList, serverListModel);
    w.execute();
  }

  private void refreshButtonActionPerformed(ActionEvent evt) {
    updateServerList();
  }

  private void connectButtonActionPerformed(ActionEvent e) {
    ListSelectionModel lsm = serverList.getSelectionModel();

    if (lsm.isSelectionEmpty()) {
      return;
    }

    /* we only allow a single item to be selected */
    int index = lsm.getMinSelectionIndex();
    connectToServer(serverListModel.getAddressAt(index));
  }

  private void settingsButtonActionPerformed(ActionEvent e) {
    FrameManager.invokePopup(this, new SettingsFrame());
  }

  private void quitButtonActionPerformed(ActionEvent e) {
    App.terminateApplication();
  }

  private void connectToServer(InetAddress address) {
    try {
      FrameManager.invokeFrame(new ChatFrame(address, ApplicationSettings.SERVER_PORT));
    } catch (IOException e) {
      UIMessage.showErrorMessage(e.getMessage());
    }
  }

  private void serverListMouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2) {
      int index = serverList.locationToIndex(e.getPoint());
      connectToServer(serverListModel.getAddressAt(index));
    }
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - David Steinsland
    dialogPane = new JPanel();
    contentPanel = new JPanel();
    label1 = new JLabel();
    scrollPane1 = new JScrollPane();
    serverList = new JList<>();
    refreshButton = new JButton();
    buttonBar = new JPanel();
    connectButton = new JButton();
    settingsButton = new JButton();
    quitButton = new JButton();

    //======== this ========
    setResizable(false);
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
        ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
        ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 1.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Select server");
        contentPanel.add(label1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 0), 0, 0));

        //======== scrollPane1 ========
        {

          //---- serverList ----
          serverList.addListSelectionListener(e -> serverListValueChanged(e));
          serverList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              serverListMouseClicked(e);
            }
          });
          scrollPane1.setViewportView(serverList);
        }
        contentPanel.add(scrollPane1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 0), 0, 0));

        //---- refreshButton ----
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(e -> refreshButtonActionPerformed(e));
        contentPanel.add(refreshButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 0), 0, 0));
      }
      dialogPane.add(contentPanel, BorderLayout.CENTER);

      //======== buttonBar ========
      {
        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 85, 0};
        ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};

        //---- connectButton ----
        connectButton.setText("Connect");
        connectButton.addActionListener(e -> connectButtonActionPerformed(e));
        buttonBar.add(connectButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 5), 0, 0));

        //---- settingsButton ----
        settingsButton.setText("Settings");
        settingsButton.addActionListener(e -> settingsButtonActionPerformed(e));
        buttonBar.add(settingsButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 5), 0, 0));

        //---- quitButton ----
        quitButton.setText("Quit");
        quitButton.addActionListener(e -> quitButtonActionPerformed(e));
        buttonBar.add(quitButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
          GridBagConstraints.CENTER, GridBagConstraints.BOTH,
          new Insets(0, 0, 0, 0), 0, 0));
      }
      dialogPane.add(buttonBar, BorderLayout.SOUTH);
    }
    contentPane.add(dialogPane, BorderLayout.CENTER);
    setSize(300, 270);
    setLocationRelativeTo(null);
    // JFormDesigner - End of component initialization  //GEN-END:initComponents

    serverList.setModel(serverListModel);
    updateServerList();
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - David Steinsland
  private JPanel dialogPane;
  private JPanel contentPanel;
  private JLabel label1;
  private JScrollPane scrollPane1;
  private JList<String> serverList;
  private JButton refreshButton;
  private JPanel buttonBar;
  private JButton connectButton;
  private JButton settingsButton;
  private JButton quitButton;
  // JFormDesigner - End of variables declaration  //GEN-END:variables

  private ServerListModel serverListModel = new ServerListModel();
}
