package gui.GameVisual.Sapper.Visualizers;

import gui.GameVisual.Sapper.Models.RectangleSapperModel;

import javax.swing.*;

abstract public class AbstractSapperVisualizer extends JPanel {
    abstract public void resetModel(RectangleSapperModel m);
}
