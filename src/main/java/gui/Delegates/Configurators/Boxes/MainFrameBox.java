package gui.Delegates.Configurators.Boxes;

import java.awt.*;
import java.io.Serializable;

public class MainFrameBox implements Serializable {
    public Rectangle boundsMainFrame;
    public MainFrameBox(Rectangle rect){
        boundsMainFrame = rect;
    }
}
