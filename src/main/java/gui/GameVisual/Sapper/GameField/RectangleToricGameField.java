package gui.GameVisual.Sapper.GameField;

import gui.GameVisual.Sapper.Enums.Cell;

public class RectangleToricGameField extends RectangleGameField {
    public RectangleToricGameField(int firstDimension, int secondDimension) {
        super(firstDimension, secondDimension);
    }

    @Override
    public RectangleGameField copy(){
        return new RectangleToricGameField(firstDimension, secondDimension);
    }
    @Override
    public Cell get(int x, int y){
        if (!(x >= firstDimension || x < 0  || y >= secondDimension || y < 0))
            return matrix[x][y];
        while (x >= firstDimension)
            x = x - firstDimension;

        while (x < 0)
            x += firstDimension;

        while (y >= secondDimension)
            y = y - secondDimension;

        while (y < 0)
            y += secondDimension;

        return matrix[x][y];
    }
    @Override
    public void set(int x, int y, Cell c){
        if (!(x >= firstDimension || x < 0  || y >= secondDimension || y < 0))
            matrix[x][y] = c;

        while (x >= firstDimension)
            x = x - firstDimension;
        while (x < 0)
            x += firstDimension;
        while (y >= secondDimension)
            y = y - secondDimension;
        while (y < 0)
            y += secondDimension;

        matrix[x][y] = c;
    }


}
