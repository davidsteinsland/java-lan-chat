package net.davidsteinsland;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Sat Dec 03 20:34:29 CET 2016
 */



/**
 * @author David Steinsland
 */
public class ServerFrame extends JFrame {
  public ServerFrame() {
    initComponents();
  }

  private void createButtonActionPerformed(ActionEvent e) {
    FrameManager.invokeFrame(new ServerOverviewFrame());
  }

  private void settingsButtonActionPerformed(ActionEvent e) {
    FrameManager.invokePopup(this, new SettingsFrame());
  }

  private void quitButtonActionPerformed(ActionEvent e) {
    System.exit(0);
  }

  private void initComponents() {
    // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - David Steinsland
    dialogPane = new JPanel();
    buttonBar = new JPanel();
    createButton = new JButton();
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

      //======== buttonBar ========
      {
        buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
        buttonBar.setLayout(new GridBagLayout());
        ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 85, 0};
        ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};

        //---- createButton ----
        createButton.setText("Create");
        createButton.addActionListener(e -> createButtonActionPerformed(e));
        buttonBar.add(createButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
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
    pack();
    setLocationRelativeTo(null);
    // JFormDesigner - End of component initialization  //GEN-END:initComponents
  }

  // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
  // Generated using JFormDesigner Evaluation license - David Steinsland
  private JPanel dialogPane;
  private JPanel buttonBar;
  private JButton createButton;
  private JButton settingsButton;
  private JButton quitButton;
  // JFormDesigner - End of variables declaration  //GEN-END:variables
}
