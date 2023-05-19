package gui.GameVisual.Sapper.GameField;

import gui.GameVisual.Sapper.Enums.Cell;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerRectangleGameField extends RectangleGameField implements PlayerGameField {

    private int countOpened;
    public PlayerRectangleGameField(int firstDimension, int secondDimension){
        super(firstDimension, secondDimension);
    }

    @Override
    public void set(int i, int j, Cell c){
        if (matrix[i][j] != c)
            countOpened +=1;
        super.set(i, j, c);
    }
    public void chainReactions(int startI, int startJ, MasterRectangleGameField pattern){
        Queue<Integer> fIndex = new LinkedList<>();
        Queue<Integer> sIndex = new LinkedList<>();
        fIndex.add(startI);
        sIndex.add(startJ);
        int cur_i;
        int cur_j;
        while(fIndex.size() != 0){
            cur_i = fIndex.element();
            cur_j = sIndex.element();

            set(cur_i, cur_j, Cell.CLEAR);

            fIndex.remove();
            sIndex.remove();

            for (int x = cur_i-1; x <= cur_i+1; x++ )
                for (int y = cur_j-1; y <= cur_j+1; y++)
                    if (checkBorder(x,y))
                        if (pattern.get(x, y) == Cell.CLEAR && matrix[x][y] != Cell.CLEAR) {
                            fIndex.add(x);
                            sIndex.add(y);
                        }else
                            set(x, y, pattern.get(x, y));

        }
    }

    public int getCountOpened(){
        return countOpened;
    }

}
