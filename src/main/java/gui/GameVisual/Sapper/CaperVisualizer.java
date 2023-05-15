package gui.GameVisual.Sapper;

import gui.GameVisual.Sapper.Enums.Cell;
import log.Logger;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class CaperVisualizer extends JPanel  {
    private CaperModel cpModel;
    private Cell[][] prevTable = {
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD}
    };
    private Cell[][] curTable =  {
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD},
            {Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD,Cell.FIELD}
    };

    public CaperVisualizer(CaperModel model) {
        cpModel = model;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x  = e.getX() / 30;
                int y = e.getY() / 30;

                try {
                    curTable = model.touch(x, y);
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
                switch (curTable[i][j]){
                    case BOMB -> drawComponent(g2d, posX, posY);
                    case CLEAR -> drawclearComponent(g2d,posX, posY);
                    case ONE -> drawNum(g2d, posX, posY, "1");
                    case TWO -> drawNum(g2d, posX, posY, "2");
                    case THREE -> drawNum(g2d, posX, posY, "3");
                    case FOUR -> drawNum(g2d, posX, posY, "4");
                    case FIVE -> drawNum(g2d, posX, posY, "5");
                    case SIX -> drawNum(g2d, posX, posY, "6");
                    case SEVEN -> drawNum(g2d, posX, posY, "7");
                    case EIGTH -> drawNum(g2d, posX, posY, "8");
                    }
                }
        prevTable = curTable;
    }

    public void drawField(Graphics2D g2d, int width, int height){
        g2d.setBackground(Color.lightGray);
        for (int i = 1; i < 9; i++){
            g2d.drawLine(0, 30*i, 270, 30*i);
            g2d.drawLine(30*i, 0 , 30*i, 270);
        }

    }
    public void drawclearComponent(Graphics2D g2d, int x, int y){
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