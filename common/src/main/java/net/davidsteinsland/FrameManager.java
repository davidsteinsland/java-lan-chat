package net.davidsteinsland;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class FrameManager {

  private static JFrame activeFrame = null;
  private static WindowListener windowListener = new WindowListener();

  public static void invokeFrame(JFrame frame) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        frame.setLocationRelativeTo (activeFrame);

        if (activeFrame != null) {
          activeFrame.dispose();
        }

        activeFrame = frame;
        activeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        activeFrame.setVisible(true);
        //activeFrame.setResizable(false);
        activeFrame.addWindowListener(windowListener);
      }
    });
  }

  public static void invokePopup(JFrame parent, final JFrame frame) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        if (parent != null) {
          frame.setLocationRelativeTo(activeFrame);
        }

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        if (parent != null) {
          parent.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
              frame.dispose();
            }
          });
        }
      }
    });
  }

  private static class WindowListener extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      if ( UIMessage.showConfirmDialog(e.getWindow(), "Are you sure you will exit?") ) {
        activeFrame.dispose();
        System.exit(0);
      }
    }
  }
}
