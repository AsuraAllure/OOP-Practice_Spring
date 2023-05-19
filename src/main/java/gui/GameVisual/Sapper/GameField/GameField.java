package gui.GameVisual.Sapper.GameField;

import gui.GameVisual.Sapper.Enums.Cell;

public interface GameField {
    Cell get(int i, int j);
    void set(int i, int j, Cell c);
}
