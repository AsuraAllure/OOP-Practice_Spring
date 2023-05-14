package gui.GameVisual.Sapper;

import gui.GameVisual.Sapper.Enums.Cell;
import gui.GameVisual.Sapper.Enums.GAME_LEVEL;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class CaperModel {

    private int flag;
    private int countBomb;
    private final int firstDimension;
    private final int secondDimension;
    public Cell[][] matrix;
    public Cell[][] gametable;

    public CaperModel(int firstDimension, int secondDimension, GAME_LEVEL level ){
        this.firstDimension = firstDimension;
        this.secondDimension = secondDimension;

        matrix = new Cell[firstDimension][secondDimension];
        gametable = new Cell[firstDimension][secondDimension];

        for(int i = 0; i < firstDimension; i++)
            for(int j = 0; j < secondDimension; j++){
                matrix[i][j] = Cell.FIELD;
                gametable[i][j] = Cell.FIELD;
            }

        int count_bomb = getCountBomb(level);
        fillBomb(count_bomb);
        fillNumbers();
    }
    private void fillNumbers(){
        for(int i = 0; i < firstDimension; i++)
            for(int j = 0; j < secondDimension; j++){
                if (matrix[i][j] == Cell.BOMB){
                       for (int x = i-1; x <= i+1; x++ )
                           for (int y = j-1; y <= j+1; y++)
                               if (check_border(x,y))
                                   upgrade(x,y);
                }
            }
    }
    private void upgrade(int x, int y){
        switch (matrix[x][y]) {
            case FIELD -> matrix[x][y] = Cell.ONE;
            case ONE -> matrix[x][y] = Cell.TWO;
            case TWO -> matrix[x][y] = Cell.THREE;
            case THREE -> matrix[x][y] = Cell.FOUR;
            case FOUR -> matrix[x][y] = Cell.FIVE;
            case FIVE -> matrix[x][y] = Cell.SIX;
            case SIX -> matrix[x][y] = Cell.SEVEN;
            case SEVEN -> matrix[x][y] = Cell.EIGTH;
            default -> {
            }
        }
    }
    private boolean check_border(int x, int y){
        return !(x >= firstDimension || x < 0  || y >= secondDimension || y < 0);
    }
    private void fillBomb(int countBomb){
        int i;
        int j;
        Random rand = new Random();
        while (countBomb != 0){
            do{
                i = rand.nextInt(firstDimension);
                j = rand.nextInt(secondDimension);
            }while(matrix[i][j] == Cell.BOMB);

            matrix[i][j] = Cell.BOMB;
            countBomb -= 1;
        }
    }
    private int getCountBomb(GAME_LEVEL level){
        countBomb = switch (level){
            case EASY -> (int)Math.round(firstDimension * secondDimension * 0.15);
            case MEDIUM ->(int)Math.round(firstDimension * secondDimension * 0.25);
            case HARD -> (int)Math.round(firstDimension * secondDimension * 0.35);
        };
        return countBomb;
    }

    public Cell[][] touch(int i, int j) throws LooseException, WinException {
        switch (matrix[i][j]){
            case BOMB -> endGame(i, j);
            case FIELD -> openAllClear(i,j);
            case CLEAR -> gametable[i][j] = Cell.CLEAR;
            default -> gametable[i][j] = matrix[i][j];
        }

        int countFields = 0;
        if (matrix[i][j] != Cell.BOMB)
            countFields = calcFields();
        else
            throw new LooseException();

        if (countFields == countBomb)
            throw new WinException();


        return gametable;
    }

    private int calcFields(){
        int cF = 0;
        for(int i = 0; i < firstDimension; i++)
            for(int j = 0; j < secondDimension; j++)
                if (gametable[i][j] == Cell.FIELD)
                    cF += 1;
        return cF;
    }
    private void openAllClear(int i, int j){
        Queue<Integer> fIndex = new LinkedList<>();
        Queue<Integer> sIndex = new LinkedList<>();
        fIndex.add(i);
        sIndex.add(j);
        int cur_i;
        int cur_j;
        while(fIndex.size() != 0){
            cur_i = fIndex.element();
            cur_j = sIndex.element();

            matrix[cur_i][cur_j] = Cell.CLEAR;
            gametable[cur_i][cur_j] = Cell.CLEAR;
            fIndex.remove();
            sIndex.remove();

            for (int x = cur_i-1; x <= cur_i+1; x++ )
                for (int y = cur_j-1; y <= cur_j+1; y++)
                    if (check_border(x,y))
                        if (matrix[x][y] == Cell.FIELD) {
                            fIndex.add(x);
                            sIndex.add(y);
                        }
        }
    }
    private void endGame(int i, int j){
        gametable[i][j] = Cell.BOMB;
    }
    public void print_table(){
        for(int i = 0; i < firstDimension; i++){
            for(int j = 0; j < secondDimension; j++) {
                System.out.print(gametable[i][j]);
                System.out.print("  ");
            }
            System.out.println("");}
        System.out.println("");
    }

    public static void main(String[] args) {
        CaperModel cp = new CaperModel(4, 4, GAME_LEVEL.EASY);
        Scanner in = new Scanner(System.in);
        int x;
        int y;
        Cell[][] table;
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
        cp.print_table();
        }while(table[x][y] != Cell.BOMB);
    }
}
