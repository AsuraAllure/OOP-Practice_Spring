package gui.InternalWindows;

import javax.swing.*;

abstract public class AbstractSerializableInternalFrame extends JInternalFrame implements ISerializableInternalFrame {
    AbstractSerializableInternalFrame(String s, boolean b1, boolean b2, boolean b3, boolean b4){
        super(s,b1,b2,b3,b4);
    }
}
