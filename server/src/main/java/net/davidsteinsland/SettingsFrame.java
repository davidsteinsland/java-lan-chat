package net.davidsteinsland;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Sun Dec 04 15:21:05 CET 2016
 */



/**
 * @author David Steinsland
 */
public class SettingsFrame extends JFrame {
	public SettingsFrame() {
		initComponents();
	}

	private void okButtonActionPerformed(ActionEvent evt) {
		try {
			ApplicationSettings.setPortNumber(Integer.parseInt(portField.getText()));
			ApplicationSettings.persist();
			this.dispose();
		} catch (NumberFormatException e) {
			UIMessage.showErrorMessage(e.getMessage());
		}
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - David Steinsland
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		portField = new JTextField();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Settings");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(5, 5, 5, 5));

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridLayout(2, 2));

				//---- label1 ----
				label1.setText("Server UDP port:");
				contentPanel.add(label1);
				contentPanel.add(portField);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(5, 5, 5, 5));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 106, 4, 106, 0};
				((GridBagLayout)buttonBar.getLayout()).rowHeights = new int[] {0, 0};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0E-4};
				((GridBagLayout)buttonBar.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

				//---- okButton ----
				okButton.setText("OK");
				okButton.addActionListener(e -> okButtonActionPerformed(e));
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.addActionListener(e -> cancelButtonActionPerformed(e));
				buttonBar.add(cancelButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents

		portField.setText(String.valueOf(ApplicationSettings.SERVER_PORT));
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - David Steinsland
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JTextField portField;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
