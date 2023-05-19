package gui.GameVisual.Sapper.Visualizers;

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


public class RectangleSapperVisualizer extends JPanel implements SapperVisualizer {
    private RectangleGameField curTable = new RectangleGameField(9, 9);

    public RectangleSapperVisualizer(RectangleSapperModel model) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x  = e.getX() / 30;
                int y = e.getY() / 30;

                try {
                    if (SwingUtilities.isLeftMouseButton(e))
                        curTable = model.touch(x, y);
                    else
                        curTable = model.mark(x, y);
                    repaint();
                }catch (WinException winException){
                    repaint();
                    Logger.debug("Вы выйграли");
                }catch (LooseException looseException){
                    repaint();
                    Logger.debug("Вы проиграли");
                }

            }
        });
        setDoubleBuffered(true);
    }


    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        drawField(g2d, 270, 270);
        for(int i=0; i < 9; i++)
            for(int j=0; j < 9; j++){
                int posX = i * 30 + 12;
                int posY = j * 30 + 15;
                switch (curTable.get(i, j)){
                    case BOMB -> drawComponent(g2d, posX-2, posY-5);
                    case CLEAR -> drawClearComponent(g2d,posX, posY);
                    case ONE -> drawNum(g2d, posX, posY, "1");
                    case TWO -> drawNum(g2d, posX, posY, "2");
                    case THREE -> drawNum(g2d, posX, posY, "3");
                    case FOUR -> drawNum(g2d, posX, posY, "4");
                    case FIVE -> drawNum(g2d, posX, posY, "5");
                    case SIX -> drawNum(g2d, posX, posY, "6");
                    case SEVEN -> drawNum(g2d, posX, posY, "7");
                    case EIGTH -> drawNum(g2d, posX, posY, "8");
                    case MARK -> drawMark(g2d, posX, posY);
                    }
                }
    }

    public void drawMark(Graphics2D g2d, int posX, int posY){
        g2d.setColor(Color.RED);
        g2d.fillRect(posX-12, posY-15, 30, 30);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(posX-12, posY-15, 30, 30);
    }
    public void drawField(Graphics2D g2d, int width, int height){
        g2d.setBackground(Color.lightGray);
        for (int i = 1; i < 9; i++){
            g2d.drawLine(0, 30*i, 270, 30*i);
            g2d.drawLine(30*i, 0 , 30*i, 270);
        }

    }
    public void drawClearComponent(Graphics2D g2d, int x, int y){
        g2d.setColor(Color.lightGray);
        g2d.fillRect(x-12,y-15, 30, 30);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(x-12, y-15, 30, 30);
    }
    public void drawComponent(Graphics2D g2d, int x, int y){
        g2d.fillOval(x,y, 10, 10);
        g2d.drawOval(x,y,10,10);
    }
    public void drawNum(Graphics2D g2d, int x, int y, String value){
        g2d.drawString(value, x , y);
    }

}