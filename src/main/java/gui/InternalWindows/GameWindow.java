package gui.InternalWindows;

import gui.Extends.Configurators.Configurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import gui.GameVisual.GameModel;
import gui.GameVisual.GameVisualizer;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

public class GameWindow extends AbstractSerializableInternalFrame
{
  private final Configurator configurator;
  private final GameVisualizer m_visualizer;
  private final GameModel m_model = new GameModel();
  public GameWindow(Configurator conf)
  {
    super("Игровое поле", true, true, true, true);
    configurator = conf;

    m_visualizer = new GameVisualizer(m_model);
    m_model.addObserver(m_visualizer);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(m_visualizer, BorderLayout.CENTER);
    getContentPane().add(panel);
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
