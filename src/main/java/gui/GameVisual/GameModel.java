package gui.GameVisual;

import gui.Extends.Mathematic.Mathematic;
import gui.GameVisual.Position.GamePositions;
import gui.GameVisual.Position.RobotPositions;
import gui.GameVisual.Position.TargetPositions;

import java.awt.*;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

public class GameModel extends Observable {
    private final Timer m_timer = initTimer();

    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;
    private final TargetPositions targetPositions = new TargetPositions(150, 100);
    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;
    public GameModel(){
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                setChanged();
                notifyObservers(
                        new GamePositions(
                                new RobotPositions(
                                        (int) Math.round(m_robotPositionX),
                                        (int) Math.round(m_robotPositionY),
                                        m_robotDirection
                                ),
                                new TargetPositions(
                                        targetPositions.getX(),
                                        targetPositions.getY()
                                )
                        )
                );
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
    }

    protected void setTargetPosition(Point p)
    {
        targetPositions.setX(p.x);
        targetPositions.setY(p.y);
    }


    private double calculateAngularVelocity(double angleToTarget){

        double angularVelocity = 0;

        if (angleToTarget - m_robotDirection > Math.PI){
            angularVelocity = -maxAngularVelocity;
        }

        if (angleToTarget - m_robotDirection < -Math.PI){
            angularVelocity = maxAngularVelocity;
        }

        if (angleToTarget - m_robotDirection < Math.PI && angleToTarget - m_robotDirection >=0)
        {
            angularVelocity = maxAngularVelocity;
        }

        if (angleToTarget - m_robotDirection < 0 && angleToTarget - m_robotDirection >=-Math.PI){
            angularVelocity = -maxAngularVelocity;
        }

        return angularVelocity;
    }

    protected void onModelUpdateEvent()
    {
        double distance = Mathematic.distance(targetPositions.getX(), targetPositions.getY(),
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = Mathematic.angleTo(m_robotPositionX, m_robotPositionY, targetPositions.getX(), targetPositions.getY());
        double angularVelocity = calculateAngularVelocity(angleToTarget);

        if (unreachable()) {
            angularVelocity = 0;
        }
        moveRobot(velocity, angularVelocity, 10);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = Mathematic.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = Mathematic.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);


        double newX = m_robotPositionX + (velocity / angularVelocity) *
                (Math.sin(m_robotDirection  + angularVelocity * duration) - Math.sin(m_robotDirection));


        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }

        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) - Math.cos(m_robotDirection));


        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }


        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = Mathematic.asNormalizedRadians(m_robotDirection + angularVelocity * duration);

        m_robotDirection = newDirection;
    }
    private boolean unreachable(){
        double dx = targetPositions.getX() - m_robotPositionX;
        double dy = targetPositions.getY() - m_robotPositionY;

        double new_dx = Math.cos(m_robotDirection)*dx + Math.sin(m_robotDirection)*dy;
        double new_dy = Math.cos(m_robotDirection)*dy - Math.sin(m_robotDirection)*dx;

        double y_center = maxVelocity / maxAngularVelocity;
        double dist1 = (Math.sqrt(Math.pow((new_dx),2)+Math.pow(new_dy-y_center,2)));
        double dist2 = (Math.sqrt(Math.pow((new_dx),2)+Math.pow(new_dy+y_center,2)));

        return !(dist1 > maxVelocity / maxAngularVelocity) || !(dist2 > maxVelocity / maxAngularVelocity);
    }

}
