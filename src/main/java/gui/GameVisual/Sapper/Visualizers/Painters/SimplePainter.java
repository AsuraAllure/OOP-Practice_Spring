package gui.GameVisual.Sapper.Visualizers.Painters;

import gui.GameVisual.Sapper.Models.SapperModel;
import gui.GameVisual.Sapper.Visualizers.SizeSettings;

import java.awt.*;

public class SimplePainter {
    private SizeSettings sizeSetting;

    public SimplePainter(SizeSettings s){
        sizeSetting = s;
    }

    public void drawMark(Graphics2D g2d, int posX, int posY){
        g2d.setColor(Color.RED);
        g2d.fillRect(
                posX - sizeSetting.cellWidth / 2,
                posY - sizeSetting.cellHeight / 2,
                sizeSetting.cellWidth,
                sizeSetting.cellHeight
        );
        g2d.setColor(Color.BLACK);
        g2d.drawRect(
                posX - sizeSetting.cellWidth / 2,
                posY - sizeSetting.cellHeight / 2,
                sizeSetting.cellWidth,
                sizeSetting.cellHeight
        );
    }
    public void drawField(Graphics2D g2d, SapperModel model){
        g2d.setBackground(Color.lightGray);
        for (int i = 1; i < model.getGameTable().getFirstDimension() ; i++){
            g2d.drawLine(
                    0,
                    sizeSetting.cellWidth * i,
                    sizeSetting.width,
                    sizeSetting.cellWidth * i
            );
        }
        for (int i = 1; i < model.getGameTable().getSecondDimension() ; i++){
            g2d.drawLine(
                    sizeSetting.cellHeight * i,
                    0 ,
                    sizeSetting.cellHeight * i,
                    sizeSetting.height
            );
        }

    }
    public void drawClearComponent(Graphics2D g2d, int x, int y){
        g2d.setColor(Color.lightGray);
        g2d.fillRect(
                x - sizeSetting.cellWidth/2,
                y - sizeSetting.cellHeight/2,
                30,
                30
        );
        g2d.setColor(Color.BLACK);
        g2d.drawRect(
                x - sizeSetting.cellWidth/2,
                y - sizeSetting.cellHeight/2,
                30,
                30
        );
    }
    public void drawComponent(Graphics2D g2d, int x, int y){
        g2d.fillOval(
                x - sizeSetting.cellWidth/4,
                y - sizeSetting.cellHeight/4 ,
                10,
                10
        );
        g2d.drawOval(
                x - sizeSetting.cellWidth/4,
                y - sizeSetting.cellHeight/4,
                10,
                10
        );
    }
    public void drawNum(Graphics2D g2d, int x, int y, String value){
        g2d.drawString(
                value,
                x- sizeSetting.cellWidth/8 ,
                y + sizeSetting.cellHeight/8
        );
    }
}
