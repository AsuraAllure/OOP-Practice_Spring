package gui.InternalWindows;

import gui.Extends.Configurators.Configurator;
import gui.Extends.Configurators.ConfiguratorInstance.FileConfigurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import gui.Extends.Localizer.Localizer;
import gui.GameVisual.GameModel;
import gui.GameVisual.GameVisualizer;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

public class GameWindow extends FileConfigurator
{
  private final GameVisualizer m_visualizer;
  private final GameModel m_model = new GameModel();
  public GameWindow()
  {
    super("gameFrame", "Игровое поле", true, true, true, true);


    m_visualizer = new GameVisualizer(m_model);
    m_model.addObserver(m_visualizer);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(m_visualizer, BorderLayout.CENTER);
    getContentPane().add(panel);
  }
  public void localization(Localizer localizer){
    setTitle(localizer.getString("nameGameWindows"));
  }
  public GameModel getModel(){
    return m_model;
  }

}
