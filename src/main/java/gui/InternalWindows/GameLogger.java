package gui.InternalWindows;

import gui.Extends.Configurators.Configurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameLogger extends AbstractSerializableInternalFrame {

    private final Configurator configurator;
    public GameLogger(Configurator conf) {
        super("Игровое поле", true, true, true, true);
        configurator = conf;


    }
    @Override
    public void save(ObjectOutputStream out) {
        try {
            out.writeObject(getSize());
            out.writeObject(getLocation());
            out.writeObject(isIcon);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void load(ObjectInputStream input) {
        try {
            setSize((Dimension) input.readObject());
            setLocation((Point) input.readObject());
            setIcon((boolean) input.readObject());
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }catch (PropertyVetoException ignored){}
    }
    @Override
    public void saveConfiguration() {
        configurator.saveInternalFrame(this);
    }
    @Override
    public void loadConfiguration(JDesktopPane pane) throws InternalFrameLoadException {
        configurator.loadInternalFrame(pane, this);
    }
}
