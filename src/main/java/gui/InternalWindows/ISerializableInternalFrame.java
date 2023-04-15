package gui.InternalWindows;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface ISerializableInternalFrame {
     void save(ObjectOutputStream out);
     void load(ObjectInputStream input);
}
