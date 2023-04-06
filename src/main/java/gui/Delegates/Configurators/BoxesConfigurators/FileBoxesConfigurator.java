package gui.Delegates.Configurators.BoxesConfigurators;

import gui.Delegates.Configurators.Boxes.Boxes;
import gui.Delegates.Configurators.Boxes.DefaultBoxes;
import gui.Delegates.Configurators.Exceptions.BoxesNotCompleteException;

import java.io.*;

public class FileBoxesConfigurator implements BoxesConfigurator{
    public FileBoxesConfigurator(){}

    private File saveFile = new File(System.getProperty("user.home")+"\\Robot\\saveFile.txt");
    @Override
    public Boxes getBoxes() throws BoxesNotCompleteException {
        Boxes container;
        File directory = new File(System.getProperty("user.home")+"\\Robot");
        if (!directory.exists()) {
            if (!directory.mkdir())
                throw new BoxesNotCompleteException();
        }

        if (saveFile.exists()){
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(System.getProperty("user.home") + "\\Robot\\saveFile.txt"))){
                container = (DefaultBoxes) inputStream.readObject();
            }catch (IOException | ClassNotFoundException exception){
                throw new BoxesNotCompleteException();
            }
        } else {
            try{
            if (!saveFile.createNewFile())
                throw new IOException();
            }catch (IOException e) { throw new BoxesNotCompleteException();}
            container = new DefaultBoxesConfigurator().getBoxes();
        }
        return container;
    }
    @Override
    public void save(Boxes container) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(saveFile))){
            try {
                outputStream.writeObject(container);
            } catch (Exception e){}
        }catch (IOException ignore) {}
    }
}


