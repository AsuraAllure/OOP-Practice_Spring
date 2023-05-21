package gui.GameVisual.Position;

public class GamePositions {
    public RobotPositions robotPositions;
    public TargetPositions targetPositions;
    public GamePositions(RobotPositions rp, TargetPositions tp){
        robotPositions = rp;
        targetPositions = tp;
    }
}
