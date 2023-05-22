package gui.InternalWindows;

import gui.Extends.Configurators.ConfiguratorInstance.FileConfigurator;
import gui.Extends.Localizer.Localizer;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.Models.RectangleSapperModel;
import gui.GameVisual.Sapper.Visualizers.AbstractSapperVisualizer;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;
import java.awt.*;

public class SapperWindows extends FileConfigurator
{
    private GAME_LEVEL level;
    private RectangleGameField field;
    private AbstractSapperVisualizer visualizer;
    public SapperWindows(AbstractSapperVisualizer m_visualizer)
    {
        super("SapperFrame", "Сапёр", false, false, false, true);
        visualizer = m_visualizer;

        setSize(282, 301);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    public void setLevel(GAME_LEVEL l){
        level = l;
    }
    public GAME_LEVEL getLevel(){
        return level;
    }
    public void setField(RectangleGameField f){
        this.field = f;
    }
    public RectangleGameField getField(){
        return this.field.copy();
    }
    public void resetModel(RectangleSapperModel m){
        visualizer.resetModel(m);
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
