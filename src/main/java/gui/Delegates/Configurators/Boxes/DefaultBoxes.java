package gui.Delegates.Configurators.Boxes;

import gui.Delegates.Configurators.Exceptions.KeyNotDefineException;
import gui.Delegates.Configurators.Exceptions.ObjNotDefineException;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DefaultBoxes implements Serializable, Boxes {
    private Rectangle boundsMainFrame;
    private Point logWindowLocation;
    private Dimension logWindowSize;
    private boolean logWindowIconified;
    private Dimension gameWindowSize;
    private Point gameWindowLocation;
    private boolean gameWindowIconified;
    /*
    private Map<String, InternalFrameBoxes> mapInternalFrame = new HashMap<>();

    public void registerInternalFrame(String nameInternalFrame, InternalFrameBoxes boxes){
*/

    @Override
    public Object getString(String key) throws KeyNotDefineException {
        return switch (key){
            case "mainFrame.bounds"-> boundsMainFrame;
            case "logWindow.location" -> logWindowLocation;
            case "logWindow.size" -> logWindowSize;
            case "logWindow.iconified" -> logWindowIconified;
            case "gameWindow.location" -> gameWindowLocation;
            case "gameWindow.size" -> gameWindowSize;
            case "gameWindow.iconified" -> gameWindowIconified;
            default -> throw new KeyNotDefineException();
        };
    }
    public void setObject(String key, Object obj) throws ObjNotDefineException {
        switch (key){
            case "mainFrame.bounds"-> boundsMainFrame = (Rectangle) obj;
            case "logWindow.location" -> logWindowLocation = (Point) obj;
            case "logWindow.size" -> logWindowSize = (Dimension) obj;
            case "logWindow.iconified" -> logWindowIconified = (boolean) obj;
            case "gameWindow.size" -> gameWindowSize = (Dimension) obj;
            case "gameWindow.location" -> gameWindowLocation = (Point) obj;
            case "gameWindow.iconified" -> gameWindowIconified = (boolean) obj;
            default -> throw new ObjNotDefineException();
        };
    }
}
