package gui;

import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import gui.Extends.Localizer.Localizer;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.GameField.RectangleToricGameField;
import gui.GameVisual.Sapper.Factories.RectangleModelFactory;
import gui.GameVisual.Sapper.Visualizers.RectangleSapperVisualizer;
import gui.InternalWindows.GameLogger;
import gui.InternalWindows.GameWindow;
import gui.InternalWindows.LogWindow;
import gui.InternalWindows.SapperWindows;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.Random;

public class MainApplicationFrame extends JFrame {

  private final LogWindow logWindow;
  private final GameWindow gameWindow;
  private final LogWindow gameLogger;
  private final Localizer localizer = new Localizer(UIManager.getDefaults().getDefaultLocale());
  private final SapperWindows sapperWindows;
  private final JDesktopPane desktopPane;


  public MainApplicationFrame(LogWindow log, GameWindow game, GameLogger gameLog) {
    int inset = 50;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds(inset, inset,
        screenSize.width - inset * 2,
        screenSize.height - inset * 2);
    desktopPane = new JDesktopPane();
    setContentPane(desktopPane);


    sapperWindows = new SapperWindows(
            new RectangleSapperVisualizer(
                    RectangleModelFactory.createModel(
                            new RectangleGameField(9, 9),
                            GAME_LEVEL.EASY,
                            new Random())
            )
    );
    sapperWindows.setField(new RectangleGameField(9, 9));
    sapperWindows.setLevel(GAME_LEVEL.EASY);
    desktopPane.add(sapperWindows);
    sapperWindows.setVisible(true);

    logWindow = log;
    gameWindow = game;
    gameLogger = gameLog;

    game.getModel().addObserver(gameLog);
    localizer.localize();

    try {
      logWindow.loadInternalFrame(desktopPane);
    } catch (InternalFrameLoadException e) {
      logWindow.setSize(400, 400);
      logWindow.setLocation(10, 10);
    }
    logWindow.localization(localizer);

    Logger.debug("Protocol is working.");

    try {
      game.loadInternalFrame(desktopPane);
    } catch (InternalFrameLoadException e) {
      gameWindow.setSize(300, 800);
      gameWindow.setLocation(10, 500);
    }
    gameWindow.localization(localizer);

    try {
      gameLogger.loadInternalFrame(desktopPane);
    } catch (InternalFrameLoadException e) {
      gameLogger.setSize(400, 400);
      gameLogger.setLocation(10, 40);
    }
    gameLogger.localization(localizer);

    setJMenuBar(generateMenuBar());
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        closeProgram();
      }
    });

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }

  private JMenuBar generateMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    MenuBuilder menuBuilder = new MenuBuilder();
    menuBar.add(menuBuilder.createLookAndViewMenu());
    menuBar.add(menuBuilder.createTestMenu());
    menuBar.add(menuBuilder.createExitMenu());
    menuBar.add(menuBuilder.createChangeLanguageMenu());
    menuBar.add(menuBuilder.createLevelMenu());
    menuBar.add(menuBuilder.createChoiceFieldMenu());
    return menuBar;
  }

  private void setLookAndFeel(String className) {
    try {
      UIManager.setLookAndFeel(className);
      SwingUtilities.updateComponentTreeUI(this);
    } catch (ClassNotFoundException | InstantiationException
             | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
    }
  }

  private void closeProgram() {
    switch (JOptionPane.showConfirmDialog(
        this,
        localizer.getString("exitConfirmationMessage"),
        localizer.getString("exitProgramOptionName"),
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    )) {
      case JOptionPane.CLOSED_OPTION -> Logger.debug(localizer.getString("closeExitLogMessage"));
      case JOptionPane.NO_OPTION -> Logger.debug(localizer.getString("noExitLogMessage"));
      case JOptionPane.YES_OPTION -> {
        Logger.debug(localizer.getString("yesExitLogMessage"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        logWindow.saveInternalFrame();
        gameWindow.saveInternalFrame();
        gameLogger.saveInternalFrame();
        sapperWindows.saveInternalFrame();
        dispose();
      }
    }
  }

  private void localization(Locale l) {
    localizer.changeLocale(l);
    localizer.localize();

    JMenuBar bar = getJMenuBar();
    bar.getMenu(0).setText(localizer.getString("lookAndFeelMenuName"));
    bar.getMenu(0).getItem(0).setText(localizer.getString("systemLookAndFeelMenuItemName"));
    bar.getMenu(0).getItem(1).setText(localizer.getString("universalLookAndFeelMenuItemName"));
    bar.getMenu(1).setText(localizer.getString("testMenuName"));
    bar.getMenu(1).getItem(0).setText(localizer.getString("logMessageMenuItemName"));
    bar.getMenu(2).setText(localizer.getString("closeMenuName"));
    bar.getMenu(2).getItem(0).setText(localizer.getString("closeMenuItemName"));
    bar.getMenu(3).setText(localizer.getString("changeLanguageMenuName"));
    bar.getMenu(3).getItem(0).setText(localizer.getString("russianLanguageItem"));
    bar.getMenu(3).getItem(1).setText(localizer.getString("translitLanguageItem"));

    gameLogger.localization(localizer);
    logWindow.localization(localizer);
    gameWindow.localization(localizer);
  }

  private class MenuBuilder {

    private MenuBuilder() {
    }

    private JMenu createChangeLanguageMenu() {

      JMenu changeLanguageMenu = new JMenu(localizer.getString("changeLanguageMenuName"));
      changeLanguageMenu.getAccessibleContext()
          .setAccessibleDescription(localizer.getString("changeLanguageMenuName"));
      JMenuItem russianLanguageItem = new JMenuItem(localizer.getString("russianLanguageItem"));
      JMenuItem translitLanguageItem = new JMenuItem(localizer.getString("translitLanguageItem"));
      changeLanguageMenu.add(russianLanguageItem);
      changeLanguageMenu.add(translitLanguageItem);
      russianLanguageItem.addActionListener((event) -> {
        Logger.debug("Change language: russian");
        MainApplicationFrame.this.localization(new Locale("ru"));
      });
      translitLanguageItem.addActionListener((event) -> {
        Logger.debug("Change language: transliteration");
        MainApplicationFrame.this.localization(new Locale("zh"));
      });
      return changeLanguageMenu;
    }

    private JMenu createExitMenu() {
      JMenu closeMenu = new JMenu(localizer.getString("closeMenuName"));
      closeMenu.getAccessibleContext()
          .setAccessibleDescription(localizer.getString("closeMenuName"));
      JMenuItem closeItem = new JMenuItem(localizer.getString("closeMenuItemName"));
      closeItem.setMnemonic(KeyEvent.VK_X);
      closeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.SHIFT_MASK));
      closeItem.addActionListener(actionEvent -> dispatchEvent(
          new WindowEvent(MainApplicationFrame.this, WindowEvent.WINDOW_CLOSING)));
      closeMenu.add(closeItem);
      return closeMenu;
    }

    private JMenu createTestMenu() {
      JMenu testMenu = new JMenu(localizer.getString("testMenuName"));
      testMenu.getAccessibleContext().setAccessibleDescription(localizer.getString("testMenuName"));
      JMenuItem addLogMessageItem = new JMenuItem(localizer.getString("logMessageMenuItemName"));
      addLogMessageItem.setMnemonic(KeyEvent.VK_T);
      addLogMessageItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.SHIFT_MASK));
      addLogMessageItem.addActionListener((event) -> Logger.debug("New Line"));
      testMenu.add(addLogMessageItem);
      return testMenu;
    }

    private JMenu createLookAndViewMenu() {
      JMenu lookAndFeelMenu = new JMenu(localizer.getString("lookAndFeelMenuName"));
      lookAndFeelMenu.getAccessibleContext()
          .setAccessibleDescription(localizer.getString("lookAndFeelMenuName"));

      JMenuItem systemLookAndFeel = new JMenuItem(
          localizer.getString("systemLookAndFeelMenuItemName"));
      systemLookAndFeel.setMnemonic(KeyEvent.VK_S);
      systemLookAndFeel.addActionListener((event) -> {
        MainApplicationFrame.this.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        MainApplicationFrame.this.invalidate();
      });
      lookAndFeelMenu.add(systemLookAndFeel);

      JMenuItem crossplatformLookAndFeel = new JMenuItem(
          localizer.getString("universalLookAndFeelMenuItemName"));
      crossplatformLookAndFeel.setMnemonic(KeyEvent.VK_U);
      crossplatformLookAndFeel.addActionListener((event) -> {
        MainApplicationFrame.this.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        MainApplicationFrame.this.invalidate();
      });
      lookAndFeelMenu.add(crossplatformLookAndFeel);
      return lookAndFeelMenu;
    }

    private JMenu createChoiceFieldMenu() {
      JMenu sapperF = new JMenu("Поле сапера");
      JMenuItem toric = new JMenuItem("Торическое поле");
      JMenuItem normal = new JMenuItem("Нормальное поле");


      toric.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.SHIFT_MASK));
      toric.addActionListener((actionEvent -> {
        sapperWindows.resetModel(
                RectangleModelFactory.createModel(
                        new RectangleToricGameField(9, 9),
                        sapperWindows.getLevel(),
                        new Random()
                )
        );
        sapperWindows.setField(new RectangleToricGameField(9, 9));
      }));

      normal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK));
      normal.addActionListener((actionEvent -> {
        sapperWindows.resetModel(
                RectangleModelFactory.createModel(
                        new RectangleGameField(9, 9),
                        sapperWindows.getLevel(),
                        new Random()
                )
        );
        sapperWindows.setField(new RectangleGameField(9, 9));
      }));

      sapperF.add(toric);
      sapperF.add(normal);

      return sapperF;
    }

    private JMenu createLevelMenu() {
      JMenu sapperM = new JMenu("Уровни сапера");
      JMenuItem easy = new JMenuItem("Легкая сложность");
      JMenuItem medium = new JMenuItem("Средняя сложность");
      JMenuItem hard = new JMenuItem("Сапер на дежурстве");

      easy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.SHIFT_MASK));
      easy.addActionListener((actionEvent -> {
        sapperWindows.resetModel(
                RectangleModelFactory.createModel(
                        sapperWindows.getField(),
                        GAME_LEVEL.EASY,
                        new Random()
                )
        );
        sapperWindows.setLevel(GAME_LEVEL.EASY);
      }));

      medium.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.SHIFT_MASK));
      medium.addActionListener((actionEvent -> {
        sapperWindows.resetModel(
                RectangleModelFactory.createModel(
                        sapperWindows.getField(),
                        GAME_LEVEL.MEDIUM,
                        new Random()
                )
        );
        sapperWindows.setLevel(GAME_LEVEL.MEDIUM);
      }));
      hard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
      hard.addActionListener((actionEvent -> {
        sapperWindows.resetModel(
                RectangleModelFactory.createModel(
                        sapperWindows.getField(),
                        GAME_LEVEL.HARD,
                        new Random()
                )
        );
        sapperWindows.setLevel(GAME_LEVEL.HARD);
      }));

      sapperM.add(easy);
      sapperM.add(medium);
      sapperM.add(hard);
      return sapperM;
    }
  }
}
