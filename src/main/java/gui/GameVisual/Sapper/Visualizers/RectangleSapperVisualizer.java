package gui.GameVisual.Sapper.Visualizers;

import gui.GameVisual.Sapper.Enums.GameState;
import gui.GameVisual.Sapper.Exception.LooseException;
import gui.GameVisual.Sapper.Exception.WinException;
import gui.GameVisual.Sapper.LogicalField.MasterFields.MasterToricGameField;
import gui.GameVisual.Sapper.LogicalField.PlayersFields.PlayerRectangleGameField;
import gui.GameVisual.Sapper.LogicalField.PlayersFields.PlayerToricGameField;
import gui.GameVisual.Sapper.Models.SapperModel;
import log.Logger;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;


public class RectangleSapperVisualizer extends AbstractSapperVisualizer {
    private PlayerRectangleGameField curTable;
    private SapperModel model;
    private int totalWidthWindow;
    private int totalHeightWindow;
    private int cellWidth;
    private int cellHeight;
    private GameState state;
    private final Controllers controllers;

    private void setWindowSize(int x, int y){
        totalHeightWindow = x;
        totalWidthWindow = y;

        cellWidth = totalWidthWindow / model.getGameTable().getFirstDimension();
        cellHeight = totalHeightWindow / model.getGameTable().getSecondDimension();
    }

    public RectangleSapperVisualizer(SapperModel model) {
        this.model = model;
        state = GameState.PLAY;
        setWindowSize(270, 270);
        controllers = new Controllers();
        addMouseListener(controllers.getSimpleMouseListeners());
        curTable = model.getGameTable();
        setDoubleBuffered(true);
    }

    @Override
    public void resetModel(SapperModel m){
        this.model = m;
        this.state = GameState.PLAY;
        this.curTable = this.model.getGameTable();

        if (curTable instanceof PlayerToricGameField){
            removeMouseListener(controllers.getCurrentMouseListeners());
            addMouseListener(controllers.getToricMouseListener());
        }
        else {
            removeMouseListener(controllers.getCurrentMouseListeners());
            addMouseListener(controllers.getSimpleMouseListeners());
        }

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

    private class Controllers{
        private final java.util.Timer m_timer = initTimer();
        private TimerTask pressedTask;
        private static java.util.Timer initTimer()
        {
            java.util.Timer timer = new Timer("events generator", true);
            return timer;
        }
        private MouseListener curListener = null;
        private MouseListener toricMouseListener = null;
        private MouseListener simpleMouseListener = null;

        public MouseListener getCurrentMouseListeners(){
            if (curListener == null)
                return new MouseAdapter(){};
            return curListener;
        }
        public MouseListener getToricMouseListener( ){
            if (toricMouseListener != null) {
                curListener = toricMouseListener;
                return toricMouseListener;
            }
            toricMouseListener = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e){
                    super.mousePressed(e);
                    if (RectangleSapperVisualizer.this.state != GameState.PLAY) {
                        repaint();
                        return;
                    }
                    pressedTask = new TimerTask() {
                        @Override
                        public void run() {
                            Point p = MouseInfo.getPointerInfo().getLocation();
                            SwingUtilities.convertPointFromScreen(p, RectangleSapperVisualizer.this);

                            int pointX = (int) p.getX();
                            int pointY = (int) p.getY();

                            if (pointX > totalWidthWindow)
                                pointX = totalWidthWindow - 1 ;

                            if (pointX < 0)
                                pointX = 0;
                            if (pointY > totalHeightWindow)
                                pointY = totalHeightWindow - 1;
                            if (pointY < 0)
                                pointY = 0;

                            int x  = pointX / cellWidth;
                            int y = pointY / cellHeight;

                            PlayerToricGameField pl = ((PlayerToricGameField) model.getGameTable());

                            pl.pressed(x, y);
                            pl.swipe(x, y);
                            repaint();
                        }
                    };
                    m_timer.schedule(pressedTask, 0, 50);
                }

                @Override
                public void mouseReleased(MouseEvent e){
                    super.mouseReleased(e);
                    if (RectangleSapperVisualizer.this.state != GameState.PLAY) {
                        repaint();
                        return;
                    }
                    ((PlayerToricGameField) model.getGameTable()).release();
                    repaint();
                    pressedTask.cancel();
                }
                @Override
                public void mouseClicked(MouseEvent e) {

                    int x  = e.getX() / cellWidth ;
                    int y = e.getY() / cellHeight ;

                    //Logger.debug("Clicked: "+Integer.toString(x) + "  " + Integer.toString(y));

                    if (state != GameState.PLAY) {
                        repaint();
                        return;
                    }

                    int d1 =((PlayerToricGameField) RectangleSapperVisualizer.this.model.getGameTable()).getDifX();
                    int d2 =((PlayerToricGameField) RectangleSapperVisualizer.this.model.getGameTable()).getDifY();

                    ((MasterToricGameField) RectangleSapperVisualizer.this.model.getMasterTable()).setDif(d1, d2);

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
            };
            curListener = toricMouseListener;
            return toricMouseListener;
        }
        public MouseListener getSimpleMouseListeners(){
            if (simpleMouseListener != null) {
                curListener = simpleMouseListener;
                return simpleMouseListener;
            }
            simpleMouseListener = new MouseAdapter() {
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
            };
            curListener = simpleMouseListener;
            return simpleMouseListener;
        }
    }
}