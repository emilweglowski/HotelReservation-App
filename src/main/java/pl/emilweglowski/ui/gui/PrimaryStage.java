package pl.emilweglowski.ui.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.emilweglowski.util.SystemUtils;

public class PrimaryStage {

    public void initialize(Stage primaryStage) {
        String hotelName = SystemUtils.HOTEL_NAME;
        String systemVersion = SystemUtils.SYSTEM_VERSION;

        MainTabView mainTabView = new MainTabView(primaryStage);

        Scene scene = new Scene(mainTabView.getMainTabs(), 940,580);
        scene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
        String title = String.format("%s reservation system (version: %s)", hotelName, systemVersion);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
