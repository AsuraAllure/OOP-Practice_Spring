package gui.GameVisual.Sapper.Visualizers;

import gui.GameVisual.Sapper.Enums.GameState;
import gui.GameVisual.Sapper.Exception.LooseException;
import gui.GameVisual.Sapper.Exception.WinException;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.Models.RectangleSapperModel;
import log.Logger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;


public class RectangleSapperVisualizer extends AbstractSapperVisualizer {
    private RectangleGameField curTable;

    private RectangleSapperModel model;
    private int totalWidthWindow;
    private int totalHeightWindow;
    private int cellWidth;
    private int cellHeight;

    private GameState state;


    private void setWindowSize(int x, int y){
        totalHeightWindow = x;
        totalWidthWindow = y;

        cellWidth = totalWidthWindow / model.getGameTable().getFirstDimension();
        cellHeight = totalHeightWindow / model.getGameTable().getSecondDimension();
    }

    public RectangleSapperVisualizer(RectangleSapperModel model) {
        this.model = model;
        state = GameState.PLAY;
        setWindowSize(270, 270);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x  = e.getX() / cellWidth;
                int y = e.getY() / cellHeight;

                if (state != GameState.PLAY) {
                    repaint();
                    return;
                }

                try {

                    if (SwingUtilities.isLeftMouseButton(e))
                        curTable = RectangleSapperVisualizer.this.model.touch(x, y);
                    else
                        curTable = RectangleSapperVisualizer.this.model.mark(x, y);

                }catch (WinException winException){
                    state = GameState.WIN;
                    Logger.debug("Вы выйграли");
                }catch (LooseException looseException){
                    state = GameState.LOSE;
                    Logger.debug("Вы проиграли");
                }
                repaint();
            }
        });
        curTable = model.getGameTable();
        setDoubleBuffered(true);
    }

    @Override
    public void resetModel(RectangleSapperModel m){
        this.model = m;
        this.state = GameState.PLAY;
        this.curTable = this.model.getGameTable();
        repaint();
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        drawField(g2d);
        for(int i=0; i < model.getGameTable().getFirstDimension(); i++)
            for(int j=0; j < model.getGameTable().getSecondDimension(); j++){
                int posX = i * cellWidth + cellWidth / 2;
                int posY = j * cellHeight + cellHeight / 2;
                switch (curTable.get(i, j)){
                    case BOMB -> drawComponent(g2d, posX, posY);
                    case CLEAR -> drawClearComponent(g2d,posX, posY);
                    case ONE -> drawNum(g2d, posX, posY, "1");
                    case TWO -> drawNum(g2d, posX, posY, "2");
                    case THREE -> drawNum(g2d, posX, posY, "3");
                    case FOUR -> drawNum(g2d, posX, posY, "4");
                    case FIVE -> drawNum(g2d, posX, posY, "5");
                    case SIX -> drawNum(g2d, posX, posY, "6");
                    case SEVEN -> drawNum(g2d, posX, posY, "7");
                    case EIGHT -> drawNum(g2d, posX, posY, "8");
                    case MARK -> drawMark(g2d, posX, posY);
                    }
                }
    }

    public void drawMark(Graphics2D g2d, int posX, int posY){
        g2d.setColor(Color.RED);
        g2d.fillRect(posX - cellWidth / 2, posY - cellHeight / 2, cellWidth, cellHeight);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(posX - cellWidth / 2, posY - cellHeight / 2, cellWidth, cellHeight);
    }
    public void drawField(Graphics2D g2d){
        g2d.setBackground(Color.lightGray);
        for (int i = 1; i < model.getGameTable().getFirstDimension() ; i++){
            g2d.drawLine(0, cellWidth*i, totalWidthWindow, cellWidth*i);
        }
        for (int i = 1; i < model.getGameTable().getSecondDimension() ; i++){
            g2d.drawLine(cellHeight*i, 0 , cellHeight*i, totalHeightWindow);
        }

    }
    public void drawClearComponent(Graphics2D g2d, int x, int y){
        g2d.setColor(Color.lightGray);
        g2d.fillRect(x - cellWidth/2, y - cellHeight/2, 30, 30);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x - cellWidth/2, y - cellHeight/2, 30, 30);
    }
    public void drawComponent(Graphics2D g2d, int x, int y){
        g2d.fillOval(x - cellWidth/4, y - cellHeight/4 , 10, 10);
        g2d.drawOval(x - cellWidth/4, y - cellHeight/4, 10,10);
    }
    public void drawNum(Graphics2D g2d, int x, int y, String value){
        g2d.drawString(value, x- cellWidth/8 , y+cellHeight/8);
    }

}