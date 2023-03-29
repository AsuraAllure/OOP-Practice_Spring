package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
  private final JDesktopPane desktopPane = new JDesktopPane();
  private Localizer localizator = new Localizer(UIManager.getDefaults().getDefaultLocale());
  public MainApplicationFrame() {
    //Make the big window be indented 50 pixels from each edge
    //of the screen.
    int inset = 50;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds(inset, inset,
        screenSize.width  - inset*2,
        screenSize.height - inset*2);

    setContentPane(desktopPane);

    localizator.localize();

    LogWindow logWindow = createLogWindow();
    addWindow(logWindow);

    GameWindow gameWindow = new GameWindow();
    gameWindow.setSize(400,  400);
    addWindow(gameWindow);

    setJMenuBar(generateMenuBar());
    addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent e) {
                closeProgram();
        }
    });

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
  }

  protected LogWindow createLogWindow()
  {
    LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
    logWindow.setLocation(10,10);
    logWindow.setSize(300, 800);
    setMinimumSize(logWindow.getSize());
    logWindow.pack();
    Logger.debug(localizator.getString("startMessageLogWindow"));
    return logWindow;
  }

  protected void addWindow(JInternalFrame frame)
  {
    desktopPane.add(frame);
    frame.setVisible(true);
  }

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
//
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
//
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
//
//        return menuBar;
//    }

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
           | IllegalAccessException | UnsupportedLookAndFeelException e)
    {
      // just ignore
    }
  }

    private void closeProgram()
    {
        // Пользователь ничего не выбрал и вышел через закрытие окна
        switch (JOptionPane.showConfirmDialog(
                this,
                localizator.getString("exitConfirmationMessage"),
                localizator.getString("exitProgramOptionName"),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        )) {
            case JOptionPane.CLOSED_OPTION -> Logger.debug(localizator.getString("closeExitLogMessage"));
            case JOptionPane.NO_OPTION -> Logger.debug(localizator.getString("noExitLogMessage"));
            case JOptionPane.YES_OPTION -> {
                Logger.debug(localizator.getString("yesExitLogMessage"));
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                dispose();
            }
        }
    }

    private class MenuBuilder {

        private MenuBuilder() {

        }

        private JMenu createExitMenu()
        {
            JMenu closeMenu = new JMenu(localizator.getString("closeMenuName"));
            closeMenu.getAccessibleContext().setAccessibleDescription(
                    localizator.getString("closeMenuName")
            );

            {
                JMenuItem closeItem = new JMenuItem(localizator.getString("closeMenuItemName"));
                closeItem.setMnemonic(KeyEvent.VK_X);
                closeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.SHIFT_MASK));
                closeItem.addActionListener(actionEvent -> dispatchEvent(new WindowEvent(MainApplicationFrame.this, WindowEvent.WINDOW_CLOSING)));

                closeMenu.add(closeItem);
            }
            return closeMenu;
        }

        private JMenu createTestMenu()
        {
            JMenu testMenu = new JMenu(localizator.getString("testMenuName"));
            testMenu.getAccessibleContext().setAccessibleDescription(
                    localizator.getString("testMenuName"));

            {
                JMenuItem addLogMessageItem = new JMenuItem(localizator.getString("logMessageMenuItemName"));
                addLogMessageItem.setMnemonic(KeyEvent.VK_T);
                addLogMessageItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.SHIFT_MASK));
                addLogMessageItem.addActionListener((event) ->
                        Logger.debug(localizator.getString("logMessageContent"))
                );
                testMenu.add(addLogMessageItem);
            }
            return testMenu;
        }

        private JMenu createLookAndViewMenu()
        {
            JMenu lookAndFeelMenu = new JMenu(localizator.getString("lookAndFeelMenuName"));
            lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                    localizator.getString("lookAndFeelMenuName"));

            {
                JMenuItem systemLookAndFeel = new JMenuItem(localizator.getString("systemLookAndFeelMenuItemName"));
                systemLookAndFeel.setMnemonic(KeyEvent.VK_S);
                systemLookAndFeel.addActionListener((event) -> {
                    MainApplicationFrame.this.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    MainApplicationFrame.this.invalidate();
                });

                lookAndFeelMenu.add(systemLookAndFeel);
            }

            {
                JMenuItem crossplatformLookAndFeel = new JMenuItem(localizator.getString("universalLookAndFeelMenuItemName"));
                crossplatformLookAndFeel.setMnemonic(KeyEvent.VK_U);
                crossplatformLookAndFeel.addActionListener((event) -> {
                    MainApplicationFrame.this.setLookAndFeel(
                            UIManager.getCrossPlatformLookAndFeelClassName());
                    MainApplicationFrame.this.invalidate();
                });
                lookAndFeelMenu.add(crossplatformLookAndFeel);
            }
            return lookAndFeelMenu;
        }
    }
}
