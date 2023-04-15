package gui.InternalWindows;

import gui.GameVisualizer;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

public class GameWindow extends AbstractSerializableInternalFrame
{
  private final GameVisualizer m_visualizer;
  public GameWindow()
  {
    super("Игровое поле", true, true, true, true);
    m_visualizer = new GameVisualizer();
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
}
