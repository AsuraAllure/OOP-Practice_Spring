package gui.InternalWindows;

import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;

import javax.swing.*;

public interface Configurable {
    void saveConfiguration();
    void loadConfiguration(JDesktopPane pane) throws InternalFrameLoadException;
}
