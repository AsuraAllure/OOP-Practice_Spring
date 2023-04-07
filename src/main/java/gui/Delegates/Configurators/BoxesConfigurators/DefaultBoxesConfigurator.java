package gui.Delegates.Configurators.BoxesConfigurators;

import gui.Delegates.Configurators.Boxes.Boxes;
import gui.Delegates.Configurators.Boxes.DefaultBoxes;
import gui.Delegates.Configurators.Boxes.InternalFrameBoxes;
import gui.Delegates.Configurators.Boxes.MainFrameBox;
import gui.Delegates.Configurators.Exceptions.ObjNotDefineException;

import java.awt.*;
public class DefaultBoxesConfigurator implements BoxesConfigurator {
    public DefaultBoxesConfigurator(){

    }
    @Override
    public Boxes getBoxes(){
        Boxes container = new DefaultBoxes();

        InternalFrameBoxes logBox = new InternalFrameBoxes(new Point(10,10),new Dimension(400, 400),false);
        InternalFrameBoxes gameBox = new InternalFrameBoxes(new Point (10,100),new Dimension(300, 800),false);
        container.registerInternalFrame(logBox, "logFrame");
        container.registerInternalFrame(gameBox, "gameFrame");

        return container;
    }
    public void save(Boxes boxes) {}
}
