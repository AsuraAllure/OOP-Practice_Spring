package gui.GameVisual.Sapper.Factories;

import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.RectangleToricGameField;
import gui.GameVisual.Sapper.LogicalField.MasterFields.MasterToricGameField;
import gui.GameVisual.Sapper.LogicalField.PlayersFields.PlayerToricGameField;
import gui.GameVisual.Sapper.Models.SapperModel;

import java.util.Random;

public class RectangleToricalModelFactory implements ModelFactory{
    public static SapperModel createModel(RectangleToricGameField field, GAME_LEVEL level, Random r){
        MasterToricGameField mas = new MasterToricGameField(field.copy(), level, r);
        PlayerToricGameField pl = new PlayerToricGameField(field.copy());
        return new SapperModel(mas, pl);
    }
}
