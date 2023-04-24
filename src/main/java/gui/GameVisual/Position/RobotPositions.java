package gui.GameVisual.Position;

import java.util.Objects;

public class RobotPositions {
    public int x;
    public int y;
    public double direction;

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
