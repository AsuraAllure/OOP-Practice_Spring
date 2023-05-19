package gui.GameVisual.Sapper.LogicalField;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.RectangleGameField;

import java.util.Random;

public class MasterRectangleGameField implements MasterGameField {
    protected int countBomb;
    protected RectangleGameField field;

    public MasterRectangleGameField(RectangleGameField field, GAME_LEVEL level, Random r){
        this.field = field;
        fillBomb(r, level);
        fillNumbersAndClears();
    }
    protected void setCountBomb(GAME_LEVEL level){
        int firstDimension = field.getFirstDimension();
        int secondDimension = field.getSecondDimension();
        countBomb = switch (level){
            case EASY -> (int)Math.round(firstDimension * secondDimension * 0.15);
            case MEDIUM ->(int)Math.round(firstDimension * secondDimension * 0.25);
            case HARD -> (int)Math.round(firstDimension * secondDimension * 0.35);
        };
    }
    protected void fillBomb(Random rand, GAME_LEVEL level){
        setCountBomb(level);
        int count = countBomb;
        int i;
        int j;
        while (count != 0){
            do{
                i = rand.nextInt(field.getFirstDimension());
                j = rand.nextInt(field.getSecondDimension());
            }while(field.get(i, j) == Cell.BOMB);

            field.set(i, j, Cell.BOMB);
            count -= 1;
        }
    }
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
                }

                if (field.get(i, j) == Cell.FIELD)
                    field.set(i, j,  Cell.CLEAR);
            }
    }

    public int getCountBomb(){
        return countBomb;
    }

    public void print(){
        field.print();
        System.out.print("Count Bomb: ");
        System.out.println(countBomb);
        System.out.println();
    }


    @Override
    public Cell get(int i, int j) {
        return field.get(i, j);
    }

    @Override
    public void set(int i, int j, Cell c) {
        field.set(i, j, c);
    }
}
