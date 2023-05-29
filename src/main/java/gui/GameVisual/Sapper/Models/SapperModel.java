package gui.GameVisual.Sapper.Models;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.GameField.RectangleToricGameField;
import gui.GameVisual.Sapper.LogicalField.MasterFields.MasterRectangleGameField;

import gui.GameVisual.Sapper.Exception.LooseException;
import gui.GameVisual.Sapper.Exception.WinException;
import gui.GameVisual.Sapper.LogicalField.PlayersFields.PlayerRectangleGameField;


import java.util.Random;
import java.util.Scanner;

public class SapperModel {
    public MasterRectangleGameField masterField;
    public PlayerRectangleGameField gameTable;

    public SapperModel(MasterRectangleGameField mas, PlayerRectangleGameField pl){
        gameTable = pl;
        masterField = mas;
    }
    public PlayerRectangleGameField getGameTable(){
        return gameTable;
    }

    public PlayerRectangleGameField mark(int i, int j) throws WinException, LooseException {
        switch (gameTable.get(i, j)) {
            case FIELD -> gameTable.set(i, j, Cell.MARK);
            case MARK -> gameTable.set(i, j, Cell.FIELD);
            case CLEAR -> pass();
            default -> gameTable.openObviousCell(i, j, masterField);
        }

        if (gameTable.checkWin(masterField.getCountBomb()))
            throw new WinException();
        return gameTable;
    }
    private void pass(){}
    public PlayerRectangleGameField touch(int i, int j) throws LooseException, WinException {

        switch (masterField.get(i, j)){
            // Работает.
            case BOMB -> endGame(i, j);

            case CLEAR -> gameTable.chainReactions(i,j, masterField);
            case MARK -> pass();
            default -> gameTable.set(i, j, masterField.get(i, j));
        }

        if (gameTable.checkWin(masterField.getCountBomb()))
            throw new WinException();

        return gameTable;
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
        MasterRectangleGameField  m = new MasterRectangleGameField(new RectangleToricGameField(9, 9), GAME_LEVEL.EASY, new Random());
        PlayerRectangleGameField p = new PlayerRectangleGameField(new RectangleToricGameField(9, 9));

        SapperModel cp = new SapperModel(m, p);

        cp.debugPrint();

        Scanner in = new Scanner(System.in);
        int x;
        int y;
        PlayerRectangleGameField table;
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

    public MasterRectangleGameField getMasterTable() {
        return masterField;
    }
}
