package gui.InternalWindows;

import gui.Extends.Configurators.Configurator;
import gui.GameVisual.Position.GamePositions;
import gui.GameVisual.Position.RobotPositions;
import log.LogEntry;
import log.LogLevel;
import log.LogWindowSource;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

public class GameLogger extends LogWindow implements Observer {
    private boolean mode;
    private RobotPositions prevPos = new RobotPositions(0,0,0);
    public GameLogger(LogWindowSource logSource,  boolean m) {
        super(logSource, "gameLogger");
        mode = m;
    }
    @Override
    public void update(Observable observable, Object o) {
        GamePositions pos = ((GamePositions) o);
        if (pos.robotPositions.equals(prevPos) || !mode)
            return;

        prevPos = pos.robotPositions;

        String strMessage = "Robot position: "+ pos.robotPositions.getX() + " "
                + pos.robotPositions.getY() + " " + pos.robotPositions.getDirection() +" , Target:"
                + pos.targetPositions.getX() + " " + pos.targetPositions.getY() ;
        m_logSource.append(LogLevel.Debug, strMessage);
    }
    @Override
    public void save(ObjectOutputStream out)  {
        super.save(out);
        File file = new File(System.getProperty("user.home")+ "\\Robot" + "\\GAMELOG.txt");
        try (PrintWriter outLog = new PrintWriter(file)) {
            for(LogEntry entry : m_logSource.all()) {
                outLog.println(entry.getMessage());
            }
        }catch (IOException ignore){}
    }
}
