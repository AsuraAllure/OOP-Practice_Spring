package gui.Delegates.Configurators.Boxes;

import gui.Delegates.Configurators.Exceptions.KeyNotDefineException;

public interface Boxes {
    void registerInternalFrame( InternalFrameBoxes boxes, String nameInternalFrame);
    InternalFrameBoxes getInternalBoxes(String key) throws KeyNotDefineException;

}
