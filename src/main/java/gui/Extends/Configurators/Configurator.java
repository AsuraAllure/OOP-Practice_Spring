package gui.Extends.Configurators;


import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import gui.InternalWindows.AbstractSerializableInternalFrame;

import javax.swing.JDesktopPane;


public interface Configurator {
    void loadInternalFrame(JDesktopPane pane, AbstractSerializableInternalFrame frame) throws InternalFrameLoadException;
    void saveInternalFrame(AbstractSerializableInternalFrame frame);
}
