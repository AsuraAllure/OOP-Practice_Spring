package gui.InternalWindows;

import gui.Extends.Localizer.Localizer;
import gui.GameVisual.Sapper.CaperModel;
import gui.GameVisual.Sapper.CaperVisualizer;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;


import java.awt.*;


import javax.swing.*;

public class CaperWindows extends JInternalFrame
{
    private final CaperVisualizer m_visualizer;
    private final CaperModel m_model = new CaperModel(9, 9, GAME_LEVEL.EASY);
    public CaperWindows()
    {
        super("Сапёр", false, false, false, true);
        m_visualizer = new CaperVisualizer(m_model);
        setSize(282, 301);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
    }
    public void localization(Localizer localizer){
        setTitle(localizer.getString("nameGameWindows"));
    }
    public CaperModel getModel(){
        return m_model;
    }
}
