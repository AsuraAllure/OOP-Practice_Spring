package gui.GameVisual.Sapper.Models;

import gui.GameVisual.Sapper.GameField.GameField;
import gui.GameVisual.Sapper.Exception.LooseException;
import gui.GameVisual.Sapper.Exception.WinException;

public interface SapperModel {
    GameField touch(int rowIndex, int columnIndex) throws LooseException, WinException;
    GameField mark(int rowIndex, int columnIndex);
}
