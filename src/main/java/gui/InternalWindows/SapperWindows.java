package gui.InternalWindows;

import gui.Extends.Configurators.ConfiguratorInstance.FileConfigurator;
import gui.Extends.Localizer.Localizer;
import gui.GameVisual.Sapper.Visualizers.AbstractSapperVisualizer;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import java.awt.*;

public class SapperWindows extends FileConfigurator
{
    AbstractSapperVisualizer visualizer;
    public SapperWindows(AbstractSapperVisualizer m_visualizer)
    {
        super("SapperFrame", "Сапёр", false, true, false, true);
        visualizer = m_visualizer;

        setSize(282, 301);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
    }
    public void localization(Localizer localizer){
        setTitle(localizer.getString("nameGameWindows"));
    }
    @Override
    protected void save(ObjectOutputStream out) {
        try {
            out.writeObject(getSize());
            out.writeObject(getLocation());
            out.writeObject(isIcon);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void load(ObjectInputStream input) {
        try {
            setSize((Dimension) input.readObject());
            setLocation((Point) input.readObject());
            setIcon((boolean) input.readObject());
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }catch (PropertyVetoException ignored){}
    }
}
