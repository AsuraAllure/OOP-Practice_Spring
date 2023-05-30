package gui.GameVisual.Sapper.Factories;

import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.Exception.NO_IMPLEMENTS_GET_MODEL;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.Models.SapperModel;

import java.util.Random;

public interface ModelFactory {
    static SapperModel getModel(RectangleGameField field, GAME_LEVEL level, Random r) throws NO_IMPLEMENTS_GET_MODEL {
        throw new NO_IMPLEMENTS_GET_MODEL();
    }
}
