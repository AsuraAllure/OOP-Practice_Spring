package gui.Delegates.Configurators.BoxesConfigurators;

import gui.Delegates.Configurators.Boxes.Boxes;
import gui.Delegates.Configurators.Boxes.DefaultBoxes;
import gui.Delegates.Configurators.Exceptions.ObjNotDefineException;

import java.awt.*;
public class DefaultBoxesConfigurator implements BoxesConfigurator {
    public DefaultBoxesConfigurator(){
    }
    @Override
    public Boxes getBoxes(){
        Boxes container = new DefaultBoxes();
        try{
            int inset = 50;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            container.setObject("mainFrame.bounds", new Rectangle(
                    inset,
                    inset,
                    screenSize.width  - inset*2,
                    screenSize.height - inset*2
            ));

            container.setObject("logWindow.location", new Point(10,10));
            container.setObject("logWindow.size", new Dimension(400, 400));
            container.setObject("logWindow.iconified", false);
            container.setObject("gameWindow.size", new Dimension(300, 800));
            container.setObject("gameWindow.location", new Point (10,10));
            container.setObject("gameWindow.iconified", false);
        } catch (ObjNotDefineException e) {
            throw new RuntimeException(e);
        }
        return container;
    }
    public void save(Boxes boxes) {}
}
