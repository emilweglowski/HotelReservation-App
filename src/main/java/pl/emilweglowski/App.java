package pl.emilweglowski;
import javafx.application.Application;
import javafx.stage.Stage;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.ui.text.TextUI;
import pl.emilweglowski.ui.gui.PrimaryStage;
import pl.emilweglowski.util.Properties;

import java.io.IOException;

public class App extends Application {

    private static final TextUI textUI = new TextUI();
    private static final GuestService guestService = ObjectPool.getGuestService();
    private static final RoomService roomService = ObjectPool.getRoomService();
    private static final ReservationService reservationService = ObjectPool.getReservationService();

    public static void main(String[] args) {

        try {
            Properties.createDataDirectory();
            System.out.println("Loading data...");
            guestService.readAll();
            roomService.readAll();
            reservationService.readAll();
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