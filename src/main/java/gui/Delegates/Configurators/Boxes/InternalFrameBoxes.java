package gui.Delegates.Configurators.Boxes;

import java.awt.*;
import java.io.Serializable;

public class InternalFrameBoxes implements Serializable {
    public Point frameLocation;
    public Dimension frameSize;
    public Boolean frameIconified;
    public InternalFrameBoxes(Point loc, Dimension size, Boolean iconified){
        frameIconified = iconified;
        frameLocation  = loc;
        frameSize = size;
    }
}
