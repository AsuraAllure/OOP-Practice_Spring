package gui.InternalWindows;

import gui.GameVisual.Position.GamePositions;
import gui.GameVisual.Position.RobotPositions;

import log.LogLevel;
import log.LogWindowSource;

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

}
