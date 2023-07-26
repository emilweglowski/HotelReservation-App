package pl.emilweglowski;
import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.ui.text.TextUI;
import pl.emilweglowski.util.Properties;

import java.io.IOException;

public class App {

    private static final TextUI textUI = new TextUI();

    public static void main(String[] args) {

        try {
            Properties.createDataDirectory();
        } catch (IOException e) {
            throw new PersistenceToFileException(Properties.DATA_DIRECTORY.toString(), "create", "directory");
        }
        textUI.showSystemInfo();
        textUI.showMainMenu();
    }
}