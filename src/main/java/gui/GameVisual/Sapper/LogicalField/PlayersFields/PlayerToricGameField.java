package gui.GameVisual.Sapper.LogicalField.PlayersFields;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import log.Logger;

public class PlayerToricGameField extends PlayerRectangleGameField{

    protected int difX = 0;
    protected int difY = 0;

    protected int prevX = -1;
    protected int prevY = -1;
    public PlayerToricGameField(RectangleGameField rec) {
        super(rec);
    }
    public int getDifX(){
        return difX;
    }
    public int getDifY(){
        return difY;
    }
    @Override
    public Cell get(int i, int j) {
        return field.get(i - difX, j - difY);
    }
    @Override
    public void set(int i, int j, Cell c){
        //Logger.debug("SET PLAYER: " + Integer.toString(i - difX) + " " + Integer.toString(j - difY));
        super.set(i - difX, j - difY, c);
    }
    public void swipe(int i, int j) {
        //Logger.debug("DIF: " +Integer.toString(difX) + "  " + Integer.toString(difY));

        if (i != prevX) {
            difX += i - prevX;
            prevX = i;
        }
        if (j != prevY){
            difY += j - prevY;
            prevY = j;
        }
    }

    public void pressed(int i, int j){
        if (prevX != -1)
            return;
        prevX = i;
        prevY = j;
    }
    public void release() {
        prevX = -1;
        prevY = -1;
    }
}
