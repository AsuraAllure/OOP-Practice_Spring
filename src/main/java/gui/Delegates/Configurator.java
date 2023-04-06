package gui.Delegates;

import gui.Delegates.Configurators.Exceptions.KeyNotDefineException;
import gui.MainApplicationFrame;
import gui.Windows.GameWindow;
import gui.Windows.LogWindow;

import javax.swing.*;


public interface Configurator {
    void loadMainWindow(MainApplicationFrame mainFrame) ;
    LogWindow loadLogWindow(JDesktopPane pane);
    GameWindow loadGameWindow(JDesktopPane pane);

    void saveMainWindow(MainApplicationFrame mainFrame);
    void saveLogWindow(LogWindow logWindow);
    void saveGameWindow(GameWindow gameWindow);
    void save();
}
