package gui.InternalWindows;

import gui.Extends.Configurators.ConfiguratorInstance.FileConfigurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import gui.Extends.Localizer.Localizer;
import gui.GameVisual.CaperModel;
import gui.GameVisual.CaperVisualizer;
import gui.GameVisual.GameVisualizer;

import java.awt.*;

import javax.swing.*;

public class CaperWindow extends FileConfigurator
{
    private final CaperVisualizer m_visualizer;
    private final CaperModel m_model = new CaperModel();
    public CaperWindow()
    {
        super("gameFrame", "Игровое поле", false, false, false, false);


        m_visualizer = new CaperVisualizer(m_model);
        m_model.addObserver(m_visualizer);

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

    @Override
    public void saveConfiguration() {
        super.saveInternalFrame();
    }
    @Override
    public void loadConfiguration(JDesktopPane pane) throws InternalFrameLoadException {
        super.loadInternalFrame(pane);
    }
}
