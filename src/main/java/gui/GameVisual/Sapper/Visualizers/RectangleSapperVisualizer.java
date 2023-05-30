package gui.GameVisual.Sapper.Visualizers;

import gui.GameVisual.Sapper.Enums.GAME_LEVEL;
import gui.GameVisual.Sapper.Enums.GameState;
import gui.GameVisual.Sapper.Exception.LooseException;
import gui.GameVisual.Sapper.Exception.WinException;
import gui.GameVisual.Sapper.Factories.RectangleModelFactory;
import gui.GameVisual.Sapper.GameField.RectangleGameField;
import gui.GameVisual.Sapper.LogicalField.MasterFields.MasterToricGameField;
import gui.GameVisual.Sapper.LogicalField.PlayersFields.PlayerRectangleGameField;
import gui.GameVisual.Sapper.LogicalField.PlayersFields.PlayerToricGameField;
import gui.GameVisual.Sapper.Models.SapperModel;
import gui.GameVisual.Sapper.Visualizers.Painters.SimplePainter;
import log.Logger;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

public class RectangleSapperVisualizer extends AbstractSapperVisualizer {
    private PlayerRectangleGameField curTable;
    private SapperModel model;
    private SizeSettings sizeSetting;
    private SimplePainter painter;
    private GameState state;
    private final Controllers controllers;

    private void setWindowSize(int x, int y){
        sizeSetting = new SizeSettings(
                x,
                y,
                x / model.getGameTable().getFirstDimension(),
                y / model.getGameTable().getSecondDimension()
        );
    }

    public RectangleSapperVisualizer() {
        this.model = RectangleModelFactory.createModel(
                new RectangleGameField(9,9),
                GAME_LEVEL.EASY,
                new Random()

        );
        state = GameState.PLAY;
        setWindowSize(270, 270);
        painter = new SimplePainter(sizeSetting);
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
        painter.drawField(g2d, model);
        for(int i=0; i < model.getGameTable().getFirstDimension(); i++)
            for(int j=0; j < model.getGameTable().getSecondDimension(); j++){
                int posX = i * sizeSetting.cellWidth + sizeSetting.cellWidth / 2;
                int posY = j * sizeSetting.cellHeight + sizeSetting.cellHeight / 2;
                switch (curTable.get(i, j)){
                    case BOMB -> painter.drawComponent(g2d, posX, posY);
                    case CLEAR -> painter.drawClearComponent(g2d,posX, posY);
                    case ONE -> painter.drawNum(g2d, posX, posY, "1");
                    case TWO -> painter.drawNum(g2d, posX, posY, "2");
                    case THREE -> painter.drawNum(g2d, posX, posY, "3");
                    case FOUR -> painter.drawNum(g2d, posX, posY, "4");
                    case FIVE -> painter.drawNum(g2d, posX, posY, "5");
                    case SIX -> painter.drawNum(g2d, posX, posY, "6");
                    case SEVEN -> painter.drawNum(g2d, posX, posY, "7");
                    case EIGHT -> painter.drawNum(g2d, posX, posY, "8");
                    case MARK -> painter.drawMark(g2d, posX, posY);
                }
            }
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

                            if (pointX > sizeSetting.width)
                                pointX = sizeSetting.width - 1 ;

                            if (pointX < 0)
                                pointX = 0;
                            if (pointY > sizeSetting.height)
                                pointY = sizeSetting.height - 1;
                            if (pointY < 0)
                                pointY = 0;

                            int x  = pointX / sizeSetting.cellWidth;
                            int y = pointY / sizeSetting.cellHeight;


                            model.pressed(x, y);
                            model.swipe(x, y);
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
                    model.release();
                    repaint();
                    pressedTask.cancel();
                }
                @Override
                public void mouseClicked(MouseEvent e) {

                    int x  = e.getX() / sizeSetting.cellWidth ;
                    int y = e.getY() / sizeSetting.cellHeight ;

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

                    int x  = e.getX() / sizeSetting.cellWidth;
                    int y = e.getY() / sizeSetting.cellHeight;

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