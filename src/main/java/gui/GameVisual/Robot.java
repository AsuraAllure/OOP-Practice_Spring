package gui.GameVisual;

import gui.GameVisual.Position.RobotPositions;

public class Robot {
    private final double maxVelocity = 0.1;
    private final double maxAngularVelocity = 0.001;

    public RobotPositions robotPositions;
    public double getMaxVelocity(){
        return maxVelocity;
    }
    public double getMaxAngularVelocity(){
        return maxAngularVelocity;
    }
    public Robot(RobotPositions rp){
        robotPositions = rp;
    };
}
