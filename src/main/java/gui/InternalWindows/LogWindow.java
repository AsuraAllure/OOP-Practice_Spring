package gui.InternalWindows;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.*;

import gui.Extends.Configurators.Configurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends AbstractSerializableInternalFrame implements LogChangeListener
{
  private LogWindowSource m_logSource;
  private TextArea m_logContent;

  private final Configurator configurator;
  public LogWindow(LogWindowSource logSource, Configurator conf)
  {
    super("Протокол работы", true, true, true, true);
    m_logSource = logSource;
    m_logSource.registerListener(this);
    m_logContent = new TextArea("");
    m_logContent.setSize(200, 500);

    configurator = conf;

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(m_logContent, BorderLayout.CENTER);
    getContentPane().add(panel);
    pack();
    updateLogContent();
  }

  private void updateLogContent()
  {
    StringBuilder content = new StringBuilder();
    for (LogEntry entry : m_logSource.all())
    {
      content.append(entry.getMessage()).append("\n");
    }
    m_logContent.setText(content.toString());
    m_logContent.invalidate();
  }

  @Override
  public void onLogChanged()
  {
    EventQueue.invokeLater(this::updateLogContent);
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
