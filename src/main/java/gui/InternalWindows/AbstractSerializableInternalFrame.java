package gui.InternalWindows;

import gui.Extends.Configurators.Configurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

abstract public class AbstractSerializableInternalFrame extends JInternalFrame implements Configurator {
    protected AbstractSerializableInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable){
        super(title, resizable, closable, maximizable, iconifiable);
    }
    protected void save(ObjectOutputStream out) {
        try {
            out.writeObject(getSize());
            out.writeObject(getLocation());
            out.writeObject(isIcon);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    protected void load(ObjectInputStream input) {
        try {
            setSize((Dimension) input.readObject());
            setLocation((Point) input.readObject());
            setIcon((boolean) input.readObject());
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }catch (PropertyVetoException ignored){}
    }

    public abstract void saveConfiguration();
    public abstract void loadConfiguration(JDesktopPane pane) throws InternalFrameLoadException;

}
