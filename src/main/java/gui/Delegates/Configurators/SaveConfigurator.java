package gui.Delegates.Configurators;


import gui.Delegates.Configurator;
import gui.Delegates.Configurators.Boxes.Boxes;
import gui.Delegates.Configurators.Boxes.InternalFrameBoxes;
import gui.Delegates.Configurators.BoxesConfigurators.BoxesConfigurator;
import gui.Delegates.Configurators.BoxesConfigurators.DefaultBoxesConfigurator;
import gui.Delegates.Configurators.BoxesConfigurators.FileBoxesConfigurator;
import gui.Delegates.Configurators.Exceptions.BoxesNotCompleteException;
import gui.Delegates.Configurators.Exceptions.KeyNotDefineException;

import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import java.beans.PropertyVetoException;

public class SaveConfigurator implements Configurator {
    private BoxesConfigurator configurator;
    private Boxes container;
    public SaveConfigurator(){
        configurator = chooseConfigurator();
        try {
            container = configurator.getBoxes();
        } catch (BoxesNotCompleteException e){
            configurator = new DefaultBoxesConfigurator();
            try {container = configurator.getBoxes();} catch (BoxesNotCompleteException ignored){
                throw new RuntimeException();
            }
        }
    }
    private BoxesConfigurator chooseConfigurator(){
        return new FileBoxesConfigurator();
    }
    public void loadInternalFrame(JDesktopPane pane, JInternalFrame frame, String key) {
        pane.add(frame);
        frame.setVisible(true);
        try {
            InternalFrameBoxes box = container.getInternalBoxes(key);
            frame.setLocation(box.frameLocation);
            frame.setSize(box.frameSize);
            try{
                frame.setIcon(box.frameIconified);
            } catch (PropertyVetoException ignored){}

        } catch (KeyNotDefineException e){
            container = new DefaultBoxesConfigurator().getBoxes();
            try{
            InternalFrameBoxes box = container.getInternalBoxes(key);
            frame.setLocation(box.frameLocation);
            frame.setSize(box.frameSize);
            } catch (KeyNotDefineException ignored){}
        }
    }

    public void saveInternalFrame(JInternalFrame frame, String key) {
        InternalFrameBoxes box = new InternalFrameBoxes(frame.getLocation(), frame.getSize(), frame.isIcon());
        container.registerInternalFrame(box, key);
    }

    public void save(){
        configurator.save(container);
    }
}
