package gui.GameVisual;

import gui.GameVisual.Position.GamePositions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class CaperVisualizer extends JPanel implements Observer {
    private GamePositions positions;


    public CaperVisualizer(CaperModel model) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.setTargetPosition(e.getPoint());
                System.out.println(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        int w = getWidth();
        int h = getHeight();
        drawField(g2d, w, h);
        drawComponent(g2d, 10, 10);
        drawNum(g2d, ((w/9)*1)+w/18-3 , ((h/9)*5)+h/18+3, "1");


    }

    public void drawField(Graphics2D g2d, int width, int height){
        for (int i = 0; i < height; i+= height/9){
            for (int j = 0; j < width; j+= width/9){
                g2d.setColor(Color.lightGray);
                g2d.fillRect(i, j, height/9, width/9);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(i, j, height/9, width/9);
            }

        }
    }
    public void drawComponent(Graphics2D g2d, int x, int y){
        g2d.fillOval(x,y, 10, 10);
        g2d.drawOval(x,y,10,10);
    }
    public void drawNum(Graphics2D g2d, int x, int y, String value){

        g2d.drawString(value, x , y);

    }
    @Override
    public void update(Observable o, Object arg) {

    }
}