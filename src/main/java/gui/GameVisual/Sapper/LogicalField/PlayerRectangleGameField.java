package gui.GameVisual.Sapper.LogicalField;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.LogicalField.MasterFields.MasterRectangleGameField;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerRectangleGameField {
    private RectangleGameField field;
    public PlayerRectangleGameField(RectangleGameField rec){
        field = rec;
    }

    public RectangleGameField getField(){
        return field;
    }

    public Cell get(int i, int j) {
        return field.get(i, j);
    }

    public void set(int i, int j, Cell c){
        field.set(i, j, c);
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

            field.set(cur_i, cur_j, Cell.CLEAR);

            fIndex.remove();
            sIndex.remove();

            for (int x = cur_i-1; x <= cur_i+1; x++ )
                for (int y = cur_j-1; y <= cur_j+1; y++)
                    if (field.checkBorder(x,y))
                        if (pattern.get(x, y) == Cell.CLEAR && field.get(x, y) != Cell.CLEAR) {
                            fIndex.add(x);
                            sIndex.add(y);
                        }else
                            field.set(x, y, pattern.get(x, y));
        }
    }

    public boolean checkWin(int countBomb){
        int countUnseen = 0;

        for (int x =0 ; x < field.getFirstDimension(); x++)
            for (int y= 0; y < field.getSecondDimension(); y++)
                if (field.get(x, y) == Cell.MARK ||  field.get(x,y) == Cell.FIELD)
                    countUnseen += 1;

        return countBomb == countUnseen;
    }
    public void print(){
        field.print();
    }

    public int getCountCell() {
        return field.getFirstDimension()* field.getSecondDimension();
    }

}
