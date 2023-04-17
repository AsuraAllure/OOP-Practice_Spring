package gui.Extends.Configurators.ConfiguratorInstance;

import gui.Extends.Configurators.Configurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import gui.InternalWindows.AbstractSerializableInternalFrame;

import javax.swing.JDesktopPane;
import java.io.*;

public class FileConfigurator implements Configurator {
    private final String saveDirectory = System.getProperty("user.home")+ "\\Robot";
    private final String filename ;
    public FileConfigurator(String filename){
        this.filename = filename;
    }
    @Override
    public void loadInternalFrame(JDesktopPane pane, AbstractSerializableInternalFrame frame)
            throws InternalFrameLoadException {
        pane.add(frame);
        frame.setVisible(true);
        File saveFile = new File(saveDirectory +"\\" + filename +".txt");
        if (!saveFile.exists())
            try {
                saveFile.createNewFile();
            }catch (IOException e){ throw new InternalFrameLoadException();}

        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(saveFile))) {
            frame.load(input);
        } catch (IOException e){
            throw new InternalFrameLoadException();
        }
        frame.setVisible(true);
    }

    @Override
    public void saveInternalFrame(AbstractSerializableInternalFrame frame) {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveDirectory +"\\"+filename+".txt"))){
            frame.save(out);
        } catch (IOException ignored) {}
    }
}
