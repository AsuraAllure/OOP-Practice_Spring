package gui.InternalWindows;

import gui.Extends.Configurators.ConfiguratorInstance.FileConfigurator;
import gui.Extends.Localizer.Localizer;
import gui.GameVisual.RobotGames.GameModel;
import gui.GameVisual.RobotGames.GameVisualizer;

import java.awt.*;

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
