package gui.GameVisual;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CaperModel {
    private int firstDimension;
    private int secondDimension;
    private enum Cell {BOMB, MARK, FIELD ,CLEAR, ONE, TWO, THREE, FOUR,FIVE, SIX, SEVEN, EIGTH}
    public Cell[][] matrix;
    public Cell[][] gametable;

    public CaperModel(int firstDimension, int secondDimension,GAME_LEVEL level ){
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
        return switch (level){
            case EASY -> (int)Math.round(firstDimension * secondDimension * 0.15);
            case MEDIUM ->(int)Math.round(firstDimension * secondDimension * 0.25);
            case HARD -> (int)Math.round(firstDimension * secondDimension * 0.35);
        };
    }

    public void touch(int i, int j){
        switch (matrix[i][j]){
            case BOMB -> endGame(i, j);
            case FIELD -> openAllClear(i,j);
            case CLEAR -> gametable[i][j] = Cell.CLEAR;
            default -> gametable[i][j] = matrix[i][j];
        }

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
        System.out.println("GAME OVER");
    }
    public void print_table(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                System.out.print(matrix[i][j]);
                System.out.print("  ");
            }
            System.out.println("");}

        System.out.println("Game");
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++) {
                System.out.print(gametable[i][j]);
                System.out.print("  ");
            }

            System.out.println("");}
    }

    public static void main(String[] args){
        CaperModel cp = new CaperModel(9,9, GAME_LEVEL.HARD);
        cp.print_table();
        cp.touch(0,0);
        System.out.println("asd");
        cp.print_table();


    }
    /*
    protected void onModelUpdateEvent()
    {
        double distance = Mathematic.distance(targetPositions.getX(), targetPositions.getY(),
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = Mathematic.angleTo(m_robotPositionX, m_robotPositionY, targetPositions.getX(), targetPositions.getY());
        double angularVelocity = calculateAngularVelocity(angleToTarget);

        if (unreachable()) {
            angularVelocity = 0;
        }
        moveRobot(velocity, angularVelocity, 10);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = Mathematic.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = Mathematic.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);


        double newX = m_robotPositionX + (velocity / angularVelocity) *
                (Math.sin(m_robotDirection  + angularVelocity * duration) - Math.sin(m_robotDirection));


        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }

        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) - Math.cos(m_robotDirection));


        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }


        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = Mathematic.asNormalizedRadians(m_robotDirection + angularVelocity * duration);

        m_robotDirection = newDirection;
    }
    private boolean unreachable(){
        double dx = targetPositions.getX() - m_robotPositionX;
        double dy = targetPositions.getY() - m_robotPositionY;

        double new_dx = Math.cos(m_robotDirection)*dx + Math.sin(m_robotDirection)*dy;
        double new_dy = Math.cos(m_robotDirection)*dy - Math.sin(m_robotDirection)*dx;

        double y_center = maxVelocity / maxAngularVelocity;
        double dist1 = (Math.sqrt(Math.pow((new_dx),2)+Math.pow(new_dy-y_center,2)));
        double dist2 = (Math.sqrt(Math.pow((new_dx),2)+Math.pow(new_dy+y_center,2)));

        return !(dist1 > maxVelocity / maxAngularVelocity) || !(dist2 > maxVelocity / maxAngularVelocity);
    }
*/
}
