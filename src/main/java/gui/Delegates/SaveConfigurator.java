package gui.Delegates;


import gui.Delegates.Configurators.Boxes.Boxes;
import gui.Delegates.Configurators.BoxesConfigurators.BoxesConfigurator;
import gui.Delegates.Configurators.BoxesConfigurators.DefaultBoxesConfigurator;
import gui.Delegates.Configurators.BoxesConfigurators.FileBoxesConfigurator;
import gui.Delegates.Configurators.Exceptions.BoxesNotCompleteException;
import gui.Delegates.Configurators.Exceptions.KeyNotDefineException;
import gui.Delegates.Configurators.Exceptions.ObjNotDefineException;
import gui.MainApplicationFrame;
import gui.Windows.GameWindow;
import gui.Windows.LogWindow;
import log.Logger;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
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
    public void loadMainWindow(MainApplicationFrame mainFrame){
        try{
        mainFrame.setBounds((Rectangle)container.getString("mainFrame.bounds"));
        }catch (KeyNotDefineException e){
            container = new DefaultBoxesConfigurator().getBoxes();
            try{
            mainFrame.setBounds((Rectangle)container.getString("mainFrame.bounds"));
            }
            catch (KeyNotDefineException e1){
                throw new RuntimeException(e1);
            }
        }
    }
    public LogWindow loadLogWindow(JDesktopPane pane) {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        pane.add(logWindow);
        logWindow.setVisible(true);
        try {
            logWindow.setLocation((Point) container.getString("logWindow.location"));
            logWindow.setSize((Dimension) container.getString("logWindow.size"));
            if ((boolean) container.getString("logWindow.iconified"))
            {

                    try{
                        logWindow.setIcon(true);
                    }catch (PropertyVetoException ignored){}

            }
        }catch (KeyNotDefineException e){
            container = new DefaultBoxesConfigurator().getBoxes();
            try{
                logWindow.setLocation((Point) container.getString("logWindow.location"));
                logWindow.setSize((Dimension) container.getString("logWindow.size"));
            } catch (KeyNotDefineException e1) { throw new RuntimeException(e1);}
        }
        Logger.debug("Protocol is working.");
        return logWindow;
    }
    public GameWindow loadGameWindow(JDesktopPane pane){
       GameWindow gameWindow = new GameWindow();
       pane.add(gameWindow);
       gameWindow.setVisible(true);

       try {
           gameWindow.setSize((Dimension) container.getString("gameWindow.size"));
           gameWindow.setLocation((Point) container.getString("gameWindow.location"));
           if ((boolean) container.getString("gameWindow.iconified"))
           {
               try{
               gameWindow.setIcon(true);}catch (PropertyVetoException ignored){}

           }
       }catch (KeyNotDefineException e){
            container = new DefaultBoxesConfigurator().getBoxes();
            try{
                gameWindow.setSize((Dimension) container.getString("gameWindow.size"));
                gameWindow.setLocation((Point) container.getString("gameWindow.location"));
            }
            catch (KeyNotDefineException e1){ throw new RuntimeException(e1);}
       }
       return gameWindow;
    }

    public void saveMainWindow(MainApplicationFrame mainFrame) {
        try{
        container.setObject("mainFrame.bounds", mainFrame.getBounds());
        } catch (ObjNotDefineException ignored){}
    }
    public void saveLogWindow(LogWindow logWindow) {
        try{
            container.setObject("logWindow.size", logWindow.getSize());
            container.setObject("logWindow.location", logWindow.getLocation());
            container.setObject("logWindow.iconified", logWindow.isIcon());
        }catch (ObjNotDefineException ignored){}
    }
    public void saveGameWindow(GameWindow gameWindow) {
        try{
            container.setObject("gameWindow.size", gameWindow.getSize());
            container.setObject("gameWindow.location", gameWindow.getLocation());
            container.setObject("gameWindow.iconified", gameWindow.isIcon());
        }catch (ObjNotDefineException ignored){}
    }
    public void save(){
        configurator.save(container);
    }
}
