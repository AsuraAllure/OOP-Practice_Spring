package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import gui.Extends.Configurators.Configurator;
import gui.Extends.Configurators.ConfiguratorInstance.FileConfigurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import gui.Extends.Localizer.Localizer;
import gui.InternalWindows.GameWindow;
import gui.InternalWindows.LogWindow;
import log.Logger;
/*
    Заставить окна самим сохранять своё состояние.
    Разные места сохранения окон.
    Спецификация сохранения.
    Добавление нового окна.

 */

public class MainApplicationFrame extends JFrame
{
  private final Configurator configurator;
  private final LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
  private final GameWindow gameWindow = new GameWindow();
  private final Localizer localizer = new Localizer(UIManager.getDefaults().getDefaultLocale());
  public MainApplicationFrame() {
    int inset = 50;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds(inset, inset,
        screenSize.width  - inset*2,
        screenSize.height - inset*2);

    configurator = new FileConfigurator();

    JDesktopPane desktopPane = new JDesktopPane();
    setContentPane(desktopPane);

    localizer.localize();

    try {
        configurator.loadInternalFrame(desktopPane, logWindow, "logFrame");
    }catch (InternalFrameLoadException e) {
        logWindow.setSize(400, 400);
        logWindow.setLocation(10,10);
    }

    Logger.debug("Protocol is working.");

    try {
        configurator.loadInternalFrame(desktopPane, gameWindow, "gameFrame");
    }catch (InternalFrameLoadException e) {
        gameWindow.setSize(300, 800);
        gameWindow.setLocation(10, 500);

    }

    setJMenuBar(generateMenuBar());
    addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
                closeProgram();
        }
    });

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }
  private JMenuBar generateMenuBar()
  {
    JMenuBar menuBar = new JMenuBar();
    MenuBuilder menuBuilder = new MenuBuilder();
    menuBar.add(menuBuilder.createLookAndViewMenu());
    menuBar.add(menuBuilder.createTestMenu());
    menuBar.add(menuBuilder.createExitMenu());
    return menuBar;
  }

  private void setLookAndFeel(String className)
  {
    try
    {
      UIManager.setLookAndFeel(className);
      SwingUtilities.updateComponentTreeUI(this);
    }
    catch (ClassNotFoundException | InstantiationException
           | IllegalAccessException | UnsupportedLookAndFeelException ignored) {}
  }

    private void closeProgram()
    {
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
                configurator.saveInternalFrame(logWindow, "logFrame");
                configurator.saveInternalFrame(gameWindow, "gameFrame");

                dispose();
            }
        }
    }
    private class MenuBuilder {
        private MenuBuilder() {}
        private JMenu createExitMenu()
        {
            JMenu closeMenu = new JMenu(localizer.getString("closeMenuName"));
            closeMenu.getAccessibleContext().setAccessibleDescription(localizer.getString("closeMenuName"));
            JMenuItem closeItem = new JMenuItem(localizer.getString("closeMenuItemName"));
            closeItem.setMnemonic(KeyEvent.VK_X);
            closeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.SHIFT_MASK));
            closeItem.addActionListener(actionEvent -> dispatchEvent(new WindowEvent(MainApplicationFrame.this, WindowEvent.WINDOW_CLOSING)));
            closeMenu.add(closeItem);
            return closeMenu;
        }

        private JMenu createTestMenu()
        {
            JMenu testMenu = new JMenu(localizer.getString("testMenuName"));
            testMenu.getAccessibleContext().setAccessibleDescription(localizer.getString("testMenuName"));
            JMenuItem addLogMessageItem = new JMenuItem(localizer.getString("logMessageMenuItemName"));
            addLogMessageItem.setMnemonic(KeyEvent.VK_T);
            addLogMessageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.SHIFT_MASK));
            addLogMessageItem.addActionListener((event) -> Logger.debug("New Line"));
            testMenu.add(addLogMessageItem);
            return testMenu;
        }

        private JMenu createLookAndViewMenu()
        {
            JMenu lookAndFeelMenu = new JMenu(localizer.getString("lookAndFeelMenuName"));
            lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(localizer.getString("lookAndFeelMenuName"));

            JMenuItem systemLookAndFeel = new JMenuItem(localizer.getString("systemLookAndFeelMenuItemName"));
            systemLookAndFeel.setMnemonic(KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                    MainApplicationFrame.this.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    MainApplicationFrame.this.invalidate();
                });
            lookAndFeelMenu.add(systemLookAndFeel);

            JMenuItem crossplatformLookAndFeel = new JMenuItem(localizer.getString("universalLookAndFeelMenuItemName"));
            crossplatformLookAndFeel.setMnemonic(KeyEvent.VK_U);
            crossplatformLookAndFeel.addActionListener((event) -> {
                    MainApplicationFrame.this.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    MainApplicationFrame.this.invalidate();
                });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
            return lookAndFeelMenu;
        }
    }
}
