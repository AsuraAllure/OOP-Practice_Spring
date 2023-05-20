package gui.GameVisual.Sapper.GameField;

import gui.GameVisual.Sapper.Enums.Cell;

public class RectangleToricGameField extends RectangleGameField {
    public RectangleToricGameField(int firstDimension, int secondDimension) {
        super(firstDimension, secondDimension);
    }

    @Override
    public Cell get(int x, int y){
        if (!(x >= firstDimension || x < 0  || y >= secondDimension || y < 0))
            return matrix[x][y];
        if (x >= firstDimension)
            x = x - firstDimension;
        if (x < 0)
            x = firstDimension + x;

        if (y >= secondDimension)
            y = y - secondDimension;
        if (y < 0)
            y = secondDimension + y;

        return matrix[x][y];
    }
    @Override
    public void set(int x, int y, Cell c){
        if (!(x >= firstDimension || x < 0  || y >= secondDimension || y < 0))
            matrix[x][y] = c;
        if (x >= firstDimension)
            x = x - firstDimension;
        if (x < 0)
            x = firstDimension + x;

        if (y >= secondDimension)
            y = y - secondDimension;
        if (y < 0)
            y = secondDimension + y;

        matrix[x][y] = c;
    }


}
