package gui.GameVisual.Sapper.LogicalField;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.RectangleGameField;

import java.util.Random;

public class MasterToricRectangleGameField extends MasterRectangleGameField{

    public MasterToricRectangleGameField(RectangleGameField field, GAME_LEVEL level, Random r) {
        super(field, level, r);
    }

    @Override
    protected void fillNumbersAndClears(){
        int firstDimension = field.getFirstDimension();
        int secondDimension = field.getSecondDimension();

        for(int i = 0; i < firstDimension; i++)
            for(int j = 0; j < secondDimension; j++){

                if (field.get(i, j) ==  Cell.BOMB) {
                    for (int x = i - 1; x <= i + 1; x++)
                        for (int y = j - 1; y <= j + 1; y++)
                            if (field.checkBorder(x, y))
                                field.upgrade(x, y);
                            else{
                                int new_x = x;
                                int new_y = y;
                                if (x>=firstDimension)
                                    new_x = x - firstDimension;
                                if (x < 0)
                                    new_x = firstDimension + x;
                                if (y >= secondDimension)
                                    new_y = y - secondDimension;
                                if (y < 0)
                                    new_y = secondDimension + y;

                                field.upgrade(new_x, new_y);
                            }
                }

                if (field.get(i, j) == Cell.FIELD)
                    field.set(i, j,  Cell.CLEAR);
            }
    }
}
