package pl.emilweglowski.ui.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.emilweglowski.util.Properties;

public class PrimaryStage {

    public void initialize(Stage primaryStage) {
        String hotelName = Properties.HOTEL_NAME;
        int systemVersion = Properties.SYSTEM_VERSION;

        MainTabView mainTabView = new MainTabView(primaryStage);

        Scene scene = new Scene(mainTabView.getMainTabs(), 940,580);
        scene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
        String title = String.format("%s reservation system (version: %d)", hotelName, systemVersion);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
