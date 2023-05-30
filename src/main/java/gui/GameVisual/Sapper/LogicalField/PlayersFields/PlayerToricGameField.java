package gui.GameVisual.Sapper.LogicalField.PlayersFields;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import log.Logger;

public class PlayerToricGameField extends PlayerRectangleGameField{

    protected int difX = 0;
    protected int difY = 0;

    public PlayerToricGameField(RectangleGameField rec) {
        super(rec);
    }

    public void setDif(int x, int y){
        difX = x;
        difY = y;
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
}
