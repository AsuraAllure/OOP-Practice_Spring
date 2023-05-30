package gui.GameVisual.Sapper.LogicalField.MasterFields;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import log.Logger;

import java.util.Random;

public class MasterToricGameField extends MasterRectangleGameField{

    protected int difX = 0;
    protected int difY = 0;

    public MasterToricGameField(RectangleGameField field, GAME_LEVEL level, Random r) {
        super(field, level, r);
    }

    public void setDif(int x, int y){
        difX = x;
        difY = y;
    }

    @Override
    public Cell get(int i, int j) {
        //Logger.debug("GET MASTER(with offset): "+ Integer.toString(i+difX)+ "  " + Integer.toString(j + difY));
        return field.get(i - difX, j - difY);
    }
    @Override
    public void set(int i, int j, Cell c){
        super.set(i - difX, j - difY, c);
    }


}
