package gui.Delegates.Configurators.Boxes;

import gui.Delegates.Configurators.Exceptions.KeyNotDefineException;
import gui.Delegates.Configurators.Exceptions.ObjNotDefineException;

public interface Boxes {
    public void registerInternalFrame( InternalFrameBoxes boxes, String nameInternalFrame);
    InternalFrameBoxes getInternalBoxes(String key) throws KeyNotDefineException;

}
