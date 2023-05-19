package gui.GameVisual.Sapper.GameField;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;

import java.util.Random;

public class MasterRectangleGameField extends RectangleGameField implements MasterGameField{
    protected int countBomb;
    public MasterRectangleGameField(int firstDimension, int secondDimension, GAME_LEVEL level, Random r){
        super(firstDimension, secondDimension);
        fillBomb(r, level);
        fillNumbersAndClears();
    }
    protected void setCountBomb(GAME_LEVEL level){
        countBomb = switch (level){
            case EASY -> (int)Math.round(super.firstDimension * secondDimension * 0.15);
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
                i = rand.nextInt(firstDimension);
                j = rand.nextInt(secondDimension);
            }while(matrix[i][j] == Cell.BOMB);

            matrix[i][j] = Cell.BOMB;
            count -= 1;
        }
    }
    protected void fillNumbersAndClears(){
        for(int i = 0; i < firstDimension; i++)
            for(int j = 0; j < secondDimension; j++){

                if (matrix[i][j] == Cell.BOMB) {
                    for (int x = i - 1; x <= i + 1; x++)
                        for (int y = j - 1; y <= j + 1; y++)
                            if (checkBorder(x, y))
                                upgrade(x, y);
                }

                if (matrix[i][j] == Cell.FIELD)
                    matrix[i][j] = Cell.CLEAR;
            }
    }

    public int getCountBomb(){
        return countBomb;
    }

    @Override
    public void print(){
        super.print();
        System.out.print("Count Bomb: ");
        System.out.println(countBomb);
        System.out.println();
    }


}
