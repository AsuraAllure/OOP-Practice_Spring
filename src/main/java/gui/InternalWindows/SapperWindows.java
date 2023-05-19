package gui.InternalWindows;

import gui.Extends.Localizer.Localizer;
import gui.GameVisual.Sapper.GameField.MasterRectangleGameField;
import gui.GameVisual.Sapper.GameField.PlayerRectangleGameField;
import gui.GameVisual.Sapper.Models.RectangleSapperModel;
import gui.GameVisual.Sapper.Models.SapperModel;
import gui.GameVisual.Sapper.Visualizers.RectangleSapperVisualizer;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.Visualizers.SapperVisualizer;


import java.awt.*;
import java.util.Random;


import javax.swing.*;

public class SapperWindows extends JInternalFrame
{
    private final SapperVisualizer m_visualizer;
    private final SapperModel m_model = new RectangleSapperModel(new MasterRectangleGameField(9,9,GAME_LEVEL.EASY, new Random()), new PlayerRectangleGameField(9, 9));
    public SapperWindows()
    {
        super("Сапёр", false, false, false, true);
        m_visualizer = new RectangleSapperVisualizer((RectangleSapperModel) m_model);
        setSize(282, 301);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add((RectangleSapperVisualizer) m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
    }
    public void localization(Localizer localizer){
        setTitle(localizer.getString("nameGameWindows"));
    }
    public SapperModel getModel(){
        return m_model;
    }
}
