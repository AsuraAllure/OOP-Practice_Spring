package gui.GameVisual.Sapper.Models;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.LogicalField.MasterRectangleGameField;
import gui.GameVisual.Sapper.LogicalField.MasterToricRectangleGameField;
import gui.GameVisual.Sapper.LogicalField.PlayerRectangleGameField;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.Exception.LooseException;
import gui.GameVisual.Sapper.Exception.WinException;

import java.util.Random;
import java.util.Scanner;

public class RectangleSapperModel implements SapperModel{
    public MasterRectangleGameField masterField;
    public PlayerRectangleGameField  gameTable;

    public RectangleSapperModel(MasterRectangleGameField mas, PlayerRectangleGameField pl){
        gameTable = pl;
        masterField = mas;
    }
    public RectangleGameField getGameTable(){
        return gameTable.getField();
    }

    public RectangleGameField mark(int i, int j) throws WinException{
        switch (gameTable.get(i, j)) {
            case FIELD -> gameTable.set(i, j, Cell.MARK);
            case MARK -> gameTable.set(i, j, Cell.FIELD);
        }

        if (gameTable.checkWin(masterField.getCountBomb()))
            throw new WinException();

        return gameTable.getField();
    }
    public RectangleGameField touch(int i, int j) throws LooseException, WinException {
        switch (masterField.get(i, j)){
            case BOMB -> endGame(i, j);
            case CLEAR -> gameTable.chainReactions(i,j, masterField);
            default -> gameTable.set(i, j, masterField.get(i, j));
        }

        if (gameTable.checkWin(masterField.getCountBomb()))
            throw new WinException();


        return gameTable.getField();
    }

    private void endGame(int i, int j) throws LooseException{
        gameTable.set(i, j, Cell.BOMB);
        throw new LooseException();
    }

    public void debugPrint(){
        masterField.print();
        gameTable.print();
    }

    public static void main(String[] args) {
        MasterRectangleGameField  m = new MasterToricRectangleGameField(new RectangleGameField(4, 4), GAME_LEVEL.EASY, new Random());
        PlayerRectangleGameField p = new PlayerRectangleGameField(new RectangleGameField(4, 4));

        RectangleSapperModel cp = new RectangleSapperModel(m, p);

        cp.debugPrint();

        Scanner in = new Scanner(System.in);
        int x;
        int y;
        RectangleGameField table;
        do{
        x = in.nextInt();
        y = in.nextInt();
        try {
            table = cp.touch(x, y);
        }catch (WinException e){
            System.out.println("win");
            return;
        }catch (LooseException e){
            System.out.println("loose");
            return;
        }
        cp.debugPrint();
        }while(table.get(x, y) != Cell.BOMB);
    }
}
