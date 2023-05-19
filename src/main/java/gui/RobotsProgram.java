package gui;

import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.MasterRectangleGameField;
import gui.GameVisual.Sapper.GameField.PlayerRectangleGameField;
import gui.GameVisual.Sapper.Models.RectangleSapperModel;
import gui.GameVisual.Sapper.Visualizers.RectangleSapperVisualizer;
import gui.InternalWindows.SapperWindows;
import gui.InternalWindows.GameLogger;
import gui.InternalWindows.GameWindow;
import gui.InternalWindows.LogWindow;
import log.LogWindowSource;
import log.Logger;

import java.awt.Frame;
import java.util.Random;

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
              new GameLogger(new LogWindowSource(100), true)
      );
      frame.pack();
      frame.setVisible(true);
      frame.setExtendedState(Frame.MAXIMIZED_BOTH);
    });
  }}
