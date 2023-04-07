package gui.Delegates;

import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;


public interface Configurator {
    void loadInternalFrame(JDesktopPane pane, JInternalFrame frame, String key);
    void saveInternalFrame(JInternalFrame frame, String key);
    void save();
}
