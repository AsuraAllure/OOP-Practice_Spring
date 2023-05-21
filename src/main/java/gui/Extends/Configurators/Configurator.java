package gui.Extends.Configurators;


import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import javax.swing.JDesktopPane;


public interface Configurator {
    void loadInternalFrame(JDesktopPane pane) throws InternalFrameLoadException;
    void saveInternalFrame();
}
