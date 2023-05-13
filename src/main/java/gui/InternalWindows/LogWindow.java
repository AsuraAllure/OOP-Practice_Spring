package gui.InternalWindows;

import java.awt.*;

import javax.swing.*;
import gui.Extends.Configurators.ConfiguratorInstance.FileConfigurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

public class LogWindow extends FileConfigurator implements LogChangeListener
{
  protected LogWindowSource m_logSource;
  private TextArea m_logContent;

  public LogWindow(LogWindowSource logSource, String filename){

    super(filename, "Протокол работы", true, true, true, true);
    m_logSource = logSource;
    m_logSource.registerListener(this);
    m_logContent = new TextArea("");
    m_logContent.setSize(200, 500);

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
  public void saveConfiguration() {
    super.saveInternalFrame();
  }

  @Override
  public void loadConfiguration(JDesktopPane pane) throws InternalFrameLoadException {
    super.loadInternalFrame(pane);
  }
}
