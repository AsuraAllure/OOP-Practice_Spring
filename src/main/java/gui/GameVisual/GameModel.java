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
    private final TargetPositions targetPositions = new TargetPositions(150, 100);

    private final Robot robot = new Robot(new RobotPositions(100, 100, 0));
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
                                        (int) Math.round(robot.robotPositions.getX()),
                                        (int) Math.round(robot.robotPositions.getY()),
                                        robot.robotPositions.getDirection()
                                ),
                                new TargetPositions(
                                        (int) Math.round(targetPositions.getX()),
                                        (int) Math.round(targetPositions.getY())
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


        if (angleToTarget - robot.robotPositions.getDirection() > Math.PI){
            angularVelocity = -robot.getMaxAngularVelocity();
        }

        if (angleToTarget - robot.robotPositions.getDirection() < -Math.PI){
            angularVelocity = robot.getMaxAngularVelocity();
        }

        if (angleToTarget - robot.robotPositions.getDirection() < Math.PI && angleToTarget - robot.robotPositions.getDirection() >=0)
        {
            angularVelocity = robot.getMaxAngularVelocity();
        }

        if (angleToTarget - robot.robotPositions.getDirection() < 0 && angleToTarget - robot.robotPositions.getDirection() >=-Math.PI){
            angularVelocity = -robot.getMaxAngularVelocity();
        }

        return angularVelocity;
    }

    protected void onModelUpdateEvent()
    {
        double distance = Mathematic.distance(targetPositions.getX(), targetPositions.getY(),
                robot.robotPositions.getX(),robot.robotPositions.getY());
        if (distance < 0.5)
        {
            return;
        }
        double velocity = robot.getMaxVelocity();
        double angleToTarget = Mathematic.angleTo(robot.robotPositions.getX(), robot.robotPositions.getY(), targetPositions.getX(), targetPositions.getY());
        double angularVelocity = calculateAngularVelocity(angleToTarget);

        if (unreachable()) {
            angularVelocity = 0;
        }
        moveRobot(velocity, angularVelocity, 10);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = Mathematic.applyLimits(velocity, 0, robot.getMaxVelocity());
        angularVelocity = Mathematic.applyLimits(angularVelocity, -robot.getMaxAngularVelocity(), robot.getMaxAngularVelocity());


        double newX = robot.robotPositions.getX() + (velocity / angularVelocity) *
                (Math.sin(robot.robotPositions.getDirection()  + angularVelocity * duration) - Math.sin(robot.robotPositions.getDirection()));


        if (!Double.isFinite(newX))
        {
            newX = robot.robotPositions.getX() + velocity * duration * Math.cos(robot.robotPositions.getDirection());
        }

        double newY = robot.robotPositions.getY() - velocity / angularVelocity *
                (Math.cos(robot.robotPositions.getDirection()  + angularVelocity * duration) - Math.cos(robot.robotPositions.getDirection()));


        if (!Double.isFinite(newY))
        {
            newY = robot.robotPositions.getY() + velocity * duration * Math.sin(robot.robotPositions.getDirection());
        }


        robot.robotPositions.setX( newX);
        robot.robotPositions.setY(newY);
        double newDirection = Mathematic.asNormalizedRadians(robot.robotPositions.getDirection() + angularVelocity * duration);

        robot.robotPositions.setDirection( newDirection);
    }
    private boolean unreachable(){
        double dx = targetPositions.getX() - robot.robotPositions.getX();
        double dy = targetPositions.getY() - robot.robotPositions.getY();

        double new_dx = Math.cos(robot.robotPositions.getDirection())*dx + Math.sin(robot.robotPositions.getDirection())*dy;
        double new_dy = Math.cos(robot.robotPositions.getDirection())*dy - Math.sin(robot.robotPositions.getDirection())*dx;

        double y_center = robot.getMaxVelocity() / robot.getMaxAngularVelocity();
        double dist1 = (Math.sqrt(Math.pow((new_dx),2)+Math.pow(new_dy-y_center,2)));
        double dist2 = (Math.sqrt(Math.pow((new_dx),2)+Math.pow(new_dy+y_center,2)));

        return !(dist1 > robot.getMaxVelocity() / robot.getMaxAngularVelocity())
                || !(dist2 > robot.getMaxVelocity() / robot.getMaxAngularVelocity());
    }
}
