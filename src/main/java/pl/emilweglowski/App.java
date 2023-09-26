package pl.emilweglowski;
import javafx.application.Application;
import javafx.stage.Stage;
import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.ui.text.TextUI;
import pl.emilweglowski.ui.text.gui.PrimaryStage;
import pl.emilweglowski.util.Properties;

import java.io.IOException;

public class App extends Application {

    private static final TextUI textUI = new TextUI();

    public static void main(String[] args) {

        try {
            Properties.createDataDirectory();
        } catch (IOException e) {
            throw new PersistenceToFileException(Properties.DATA_DIRECTORY.toString(), "create", "directory");
        }
        Application.launch(args);
//        textUI.showSystemInfo();
//        textUI.showMainMenu();
    }

    public void start(Stage primaryStage) {
        PrimaryStage primary = new PrimaryStage();
        primary.initialize(primaryStage);
    }
}