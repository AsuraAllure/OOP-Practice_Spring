package gui.GameVisual.Position;

import java.util.Objects;

public class RobotPositions extends BasicPositions {
    protected volatile double direction;
    public double getDirection(){
        return direction;
    }
    public void setDirection(double d){
        direction = d;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RobotPositions){
            RobotPositions rb = (RobotPositions) obj;
            return (x == rb.x) && (y == rb.y) && (direction == rb.direction);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, direction);
    }
    public RobotPositions(int pos_x, int pos_y, double direction){
        x = pos_x;
        y = pos_y;
        this.direction = direction;
    }
}
