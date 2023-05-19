package gui.GameVisual.Sapper.GameField;

import gui.GameVisual.Sapper.Enums.Cell;

public class RectangleGameField implements GameField{
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

    public int getSize(){
        return firstDimension * secondDimension;
    }
    protected boolean checkBorder(int x, int y){
        return !(x >= firstDimension || x < 0  || y >= secondDimension || y < 0);
    }

    protected void upgrade(int x, int y){
        switch (matrix[x][y]) {
            case FIELD, CLEAR -> matrix[x][y] = Cell.ONE;
            case ONE -> matrix[x][y] = Cell.TWO;
            case TWO -> matrix[x][y] = Cell.THREE;
            case THREE -> matrix[x][y] = Cell.FOUR;
            case FOUR -> matrix[x][y] = Cell.FIVE;
            case FIVE -> matrix[x][y] = Cell.SIX;
            case SIX -> matrix[x][y] = Cell.SEVEN;
            case SEVEN -> matrix[x][y] = Cell.EIGTH;
        }
    }

    public Cell get(int i, int j){
        return matrix[i][j];
    }

    public void set(int i, int j, Cell c){
        matrix[i][j] = c;
    }

    public void print(){
        for(int i = 0; i < firstDimension; i++){
            for(int j = 0; j < secondDimension; j++) {
                System.out.print(matrix[i][j]);
                System.out.print("  ");
            }
            System.out.println("");}
        System.out.println("");
    }
}
