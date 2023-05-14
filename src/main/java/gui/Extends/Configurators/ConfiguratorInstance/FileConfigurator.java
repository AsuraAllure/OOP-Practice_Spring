package gui.Extends.Configurators.ConfiguratorInstance;

import gui.Extends.Configurators.Configurator;
import gui.Extends.Configurators.Exceptions.InternalFrameLoadException;
import gui.InternalWindows.AbstractSerializableInternalFrame;

import javax.swing.JDesktopPane;
import java.io.*;

public abstract class FileConfigurator extends AbstractSerializableInternalFrame implements Configurator {
    private final String saveDirectory = System.getProperty("user.home")+ "\\Robot";
    private final String filename ;
    public FileConfigurator(String filename, String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable){
        super(title, resizable, closable, maximizable, iconifiable);
        this.filename = filename;
    }
    public void loadInternalFrame(JDesktopPane pane)
            throws InternalFrameLoadException {
        pane.add(this);
        this.setVisible(true);
        File saveFile = new File(saveDirectory +"\\" + filename +".txt");
        if (!saveFile.exists())
            try {
                saveFile.createNewFile();
            }catch (IOException e){ throw new InternalFrameLoadException();}

        try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(saveFile))) {
            this.load(input);
        } catch (IOException e){

            throw new InternalFrameLoadException();

        }
        this.setVisible(true);
    }

    public void saveInternalFrame() {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveDirectory +"\\"+filename+".txt"))){
            this.save(out);
        } catch (IOException ignored) {}
    }

}
