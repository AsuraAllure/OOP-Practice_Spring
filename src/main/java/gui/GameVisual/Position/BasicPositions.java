package gui.GameVisual.Position;

public abstract class BasicPositions {
    protected volatile double x;
    protected volatile double y;
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public void setX(double x){
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
}
