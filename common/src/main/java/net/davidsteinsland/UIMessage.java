package net.davidsteinsland;

import java.awt.Window;
import javax.swing.JOptionPane;

public class UIMessage {

  public static void showMessage(String m) {
    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), m, "Information", JOptionPane.PLAIN_MESSAGE);
  }

  public static void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public static boolean showConfirmDialog(Window window, String message) {
    int a = JOptionPane.showConfirmDialog(window, message, "Information",
                                     JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

    return a == JOptionPane.YES_OPTION;
  }
}
