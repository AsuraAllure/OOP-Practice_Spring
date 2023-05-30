package gui.GameVisual.Sapper.LogicalField.PlayersFields;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.Exception.LooseException;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.GameField.RectangleToricGameField;
import gui.GameVisual.Sapper.LogicalField.MasterFields.MasterRectangleGameField;


import java.util.LinkedList;
import java.util.Queue;

public class PlayerRectangleGameField {
    protected RectangleGameField field;
    public PlayerRectangleGameField(RectangleGameField rec){
        field = rec;
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

            set(cur_i, cur_j, Cell.CLEAR);

            fIndex.remove();
            sIndex.remove();

            for (int x = cur_i-1; x <= cur_i+1; x++ )
                for (int y = cur_j-1; y <= cur_j+1; y++)
                        if (pattern.get(x, y) == Cell.CLEAR && get(x, y) != Cell.CLEAR) {
                            fIndex.add(x);
                            sIndex.add(y);
                        }else
                            set(x, y, pattern.get(x, y));
        }
    }
    public boolean checkWin(int countBomb){
        int countUnseenField = 0;

        for (int x =0 ; x < field.getFirstDimension(); x++)
            for (int y= 0; y < field.getSecondDimension(); y++)
                if (get(x, y) == Cell.FIELD) {
                    countUnseenField += 1;
                }

        return countUnseenField == 0;
    }
    public void print(){
        field.print();
    }
    public void openObviousCell(int i, int j, MasterRectangleGameField pattern) throws LooseException {
        int countMarked = 0;
        for(int x = i - 1; x <=i + 1; x++)
            for(int y = j-1; y <= j+1; y++)
                if (get(x, y) == Cell.MARK) {
                    countMarked += 1;
                }
        int ordinal = get(i, j).ordinal();
        if (ordinal == countMarked)
            for(int x = i - 1; x <=i + 1; x++)
                for(int y = j-1; y <= j+1; y++)
                    if (get(x, y) != Cell.MARK)
                        switch (pattern.get(x, y)){
                            case CLEAR -> chainReactions(x, y, pattern);
                            case BOMB -> throw new LooseException();
                            default -> set(x, y, pattern.get(x, y));
                        }
    }
    public int getCountCell() {
        return field.getFirstDimension()* field.getSecondDimension();
    }
    public int getFirstDimension(){
        return field.getFirstDimension();
    }
    public int getSecondDimension(){
        return field.getSecondDimension();
    }
}
