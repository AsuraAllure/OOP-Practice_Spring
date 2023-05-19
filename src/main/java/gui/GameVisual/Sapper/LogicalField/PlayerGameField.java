package gui.GameVisual.Sapper.LogicalField;

import gui.GameVisual.Sapper.GameField.GameField;

public interface PlayerGameField extends GameField {
    void chainReactions(int startI, int startJ, MasterRectangleGameField pattern);

    int getCountCell();
}
