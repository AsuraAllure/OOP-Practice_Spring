package gui.Delegates.Configurators.Boxes;

import gui.Delegates.Configurators.Exceptions.KeyNotDefineException;
import java.io.Serializable;
import java.util.HashMap;


public class DefaultBoxes implements Serializable, Boxes {
    private final HashMap<String, InternalFrameBoxes> mapInternalFrame = new HashMap<>();
    public void registerInternalFrame( InternalFrameBoxes boxes, String nameInternalFrame){
        mapInternalFrame.put(nameInternalFrame, boxes);
    }
    @Override
    public InternalFrameBoxes getInternalBoxes(String key) throws KeyNotDefineException {
        InternalFrameBoxes boxRes = mapInternalFrame.get(key);
        if (boxRes == null)
            throw new KeyNotDefineException();
        return boxRes;
    }
}
