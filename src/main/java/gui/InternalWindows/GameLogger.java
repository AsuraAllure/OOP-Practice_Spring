package gui.InternalWindows;

import gui.Extends.Configurators.Configurator;
import gui.GameVisual.Position.Positions;
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
    public GameLogger(LogWindowSource logSource, Configurator conf, boolean m) {
        super(logSource, conf);
        mode = m;
    }
    @Override
    public void update(Observable observable, Object o) {
        Positions pos = ((Positions) o);
        if (pos.robotPositions.equals(prevPos) || !mode)
            return;

        prevPos = pos.robotPositions;

        String strMessage = "Robot position: "+ pos.robotPositions.x + " "
                + pos.robotPositions.y + " " + pos.robotPositions.direction +" , Target:"
                + pos.targetPositions.x + " " + pos.targetPositions.y ;
        m_logSource.append(LogLevel.Debug, strMessage);
    }
    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }
    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
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
