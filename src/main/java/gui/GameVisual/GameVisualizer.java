package gui.GameVisual;

import gui.GameVisual.Position.GamePositions;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel implements Observer
{
  private GamePositions positions;
  public GameVisualizer(GameModel model)
  {
    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        model.setTargetPosition(e.getPoint());
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
    drawRobot(
              g2d,
              (int) positions.robotPositions.getX(),
              (int) positions.robotPositions.getY(),
              positions.robotPositions.getDirection()
    );
    drawTarget(g2d, (int) positions.targetPositions.getX(), (int) positions.targetPositions.getY());
  }

  private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
  {
    g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
  }

  private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
  {
    g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
  }

  private void drawRobot(Graphics2D g, int x, int y, double direction)
  {
    if (x < 0)
      x = 10;
    if (y < 0)
      y = 10;
    if (x > getWidth())
      x = getWidth() - 10;
    if (y > getHeight())
      y = getHeight() - 10;


    AffineTransform t = AffineTransform.getRotateInstance(direction, x, y);
    g.setTransform(t);
    g.setColor(Color.MAGENTA);
    fillOval(g, x, y, 30, 10);
    g.setColor(Color.BLACK);
    drawOval(g, x, y, 30, 10);
    g.setColor(Color.WHITE);
    fillOval(g, x + 10, y, 5, 5);
    g.setColor(Color.BLACK);
    drawOval(g, x + 10, y, 5, 5);
  }

  private void drawTarget(Graphics2D g, int x, int y)
  {
    AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
    g.setTransform(t);
    g.setColor(Color.GREEN);
    fillOval(g, x, y, 5, 5);
    g.setColor(Color.BLACK);
    drawOval(g, x, y, 5, 5);
  }

  @Override
  public void update(Observable observable, Object o) {
    positions = (GamePositions) o;
    EventQueue.invokeLater(this::repaint);
  }
}
