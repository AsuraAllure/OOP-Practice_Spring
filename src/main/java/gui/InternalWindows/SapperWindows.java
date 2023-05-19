package gui.InternalWindows;

import gui.Extends.Configurators.ConfiguratorInstance.FileConfigurator;
import gui.Extends.Localizer.Localizer;
import gui.GameVisual.Sapper.Visualizers.AbstractSapperVisualizer;

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
}
