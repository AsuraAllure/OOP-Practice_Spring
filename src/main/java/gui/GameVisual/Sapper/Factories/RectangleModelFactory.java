package gui.GameVisual.Sapper.Factories;

import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.GameField.RectangleToricGameField;
import gui.GameVisual.Sapper.LogicalField.MasterFields.MasterRectangleGameField;
import gui.GameVisual.Sapper.LogicalField.MasterFields.MasterToricGameField;
import gui.GameVisual.Sapper.LogicalField.PlayersFields.PlayerRectangleGameField;
import gui.GameVisual.Sapper.LogicalField.PlayersFields.PlayerToricGameField;
import gui.GameVisual.Sapper.Models.SapperModel;

import java.util.Random;

public class RectangleModelFactory implements ModelFactory{

    public static SapperModel createModel(RectangleGameField field, GAME_LEVEL level, Random r){
        MasterRectangleGameField mas = new MasterRectangleGameField(field.copy(), level, r);
        PlayerRectangleGameField pl = new PlayerRectangleGameField(field.copy());
        return new SapperModel(mas, pl);
    }


}
