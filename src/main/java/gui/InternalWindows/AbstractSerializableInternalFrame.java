package gui.InternalWindows;

import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

abstract public class AbstractSerializableInternalFrame extends JInternalFrame {
    AbstractSerializableInternalFrame(String s, boolean b1, boolean b2, boolean b3, boolean b4){
        super(s,b1,b2,b3,b4);
    }
    public abstract void save(ObjectOutputStream out);
    public abstract void load(ObjectInputStream input);

    public abstract void saveConfiguration();
    public abstract void loadConfiguration(JDesktopPane pane) throws InternalFrameLoadException;

}
