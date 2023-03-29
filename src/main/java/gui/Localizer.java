package gui;

import javax.swing.UIManager;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Localizer {
    private Locale locale;
    private ResourceBundle resourceBundle;

    public Localizer(Locale l) {
        locale = l;
    }

    public void changeLocale(Locale newLocale) {
        locale = newLocale;
    }

    public String getString(String fieldName) {

        try {
            return resourceBundle.getString(fieldName);
        } catch (MissingResourceException m_e) {
            return "unknown";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void localize() {
        resourceBundle = ResourceBundle.getBundle("localData", locale);
        localizeButtonText();
    }

    private void localizeButtonText() {
        UIManager.put("OptionPane.yesButtonText", resourceBundle.getString("yesButtonText"));
        UIManager.put("OptionPane.noButtonText", resourceBundle.getString("noButtonText"));
        UIManager.put("OptionPane.cancelButtonText", resourceBundle.getString("cancelButtonText"));
        UIManager.put("OptionPane.okButtonText", resourceBundle.getString("okButtonText"));
    }

}
