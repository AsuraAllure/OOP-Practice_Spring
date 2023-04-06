package gui.Delegates.Configurators.Boxes;

import gui.Delegates.Configurators.Exceptions.KeyNotDefineException;
import gui.Delegates.Configurators.Exceptions.ObjNotDefineException;

public interface Boxes {
    Object getString(String key) throws KeyNotDefineException;
    void setObject(String key, Object obj) throws ObjNotDefineException;
}
