package gui;

import gui.InternalWindows.GameLogger;
import gui.InternalWindows.GameWindow;
import gui.InternalWindows.LogWindow;
import log.LogWindowSource;
import log.Logger;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram
{
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    SwingUtilities.invokeLater(() -> {
      MainApplicationFrame frame = new MainApplicationFrame(
              new LogWindow(Logger.getDefaultLogSource(), "logFrame"),
              new GameWindow(),
              new GameLogger(new LogWindowSource(100), false)
      );
      frame.pack();
      frame.setVisible(true);
      frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    });
  }}
