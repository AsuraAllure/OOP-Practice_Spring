package gui.Delegates.Configurators.BoxesConfigurators;

import gui.Delegates.Configurators.Boxes.Boxes;
import gui.Delegates.Configurators.Exceptions.BoxesNotCompleteException;

public interface BoxesConfigurator {
    Boxes getBoxes() throws BoxesNotCompleteException;
    void save(Boxes container);
}
