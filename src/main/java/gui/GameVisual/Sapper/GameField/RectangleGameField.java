package gui.GameVisual.Sapper.GameField;

import gui.GameVisual.Sapper.Enums.Cell;

public class RectangleGameField {
    protected final int firstDimension;
    protected final int secondDimension;
    protected final Cell[][] matrix;
    public RectangleGameField(int firstDimension, int secondDimension){
        this.firstDimension = firstDimension;
        this.secondDimension = secondDimension;

        matrix = new Cell[firstDimension][secondDimension];
        for(int i = 0; i < this.firstDimension; i++)
            for(int j = 0; j < this.secondDimension; j++){
                matrix[i][j] = Cell.FIELD;
            }
    }

    public RectangleGameField copy(){
        return new RectangleGameField(firstDimension, secondDimension);
    }

    public int getFirstDimension(){
        return firstDimension;
    }
    public int getSecondDimension(){
        return secondDimension;
    }
    public void upgrade(int x, int y){
        switch (get(x, y)) {
            case FIELD, CLEAR -> set(x, y, Cell.ONE);
            case ONE -> set(x, y, Cell.TWO);
            case TWO -> set(x, y, Cell.THREE);
            case THREE -> set(x, y, Cell.FOUR);
            case FOUR -> set(x, y, Cell.FIVE);
            case FIVE -> set(x, y, Cell.SIX);
            case SIX -> set(x, y, Cell.SEVEN);
            case SEVEN -> set(x, y, Cell.EIGHT);
        }
    }
    public Cell get(int x, int y){
        if (!(x >= firstDimension || x < 0  || y >= secondDimension || y < 0))
            return matrix[x][y];
        return Cell.NULL;
    }

    public void set(int x, int y, Cell c){
        if (!(x >= firstDimension || x < 0  || y >= secondDimension || y < 0))
            matrix[x][y] = c;
    }

    public void print(){
        for(int i = 0; i < firstDimension; i++){
            for(int j = 0; j < secondDimension; j++) {
                System.out.print(get(i, j));
                System.out.print("  ");
            }
            System.out.println("");}
        System.out.println("");
    }
}
