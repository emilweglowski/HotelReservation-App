package pl.emilweglowski;
import javafx.application.Application;
import javafx.stage.Stage;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.Gender;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.guest.GuestJPARepository;
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.ui.text.TextUI;
import pl.emilweglowski.ui.gui.PrimaryStage;
import pl.emilweglowski.util.SystemUtils;

import java.io.IOException;
import java.util.List;

public class App extends Application {

    private static final TextUI textUI = new TextUI();
    private static final GuestService guestService = ObjectPool.getGuestService();
    private static final RoomService roomService = ObjectPool.getRoomService();
    private static final ReservationService reservationService = ObjectPool.getReservationService();

    public static void main(String[] args) {

        try {
            SystemUtils su = new SystemUtils();
            SystemUtils.createDataDirectory();
            su.createDataBaseConnection();
            System.out.println("Loading data...");
            GuestJPARepository jpaRepository = new GuestJPARepository();
            Guest newGuest = jpaRepository.createNewGuest("Jan", "Kowalski", 53, Gender.MALE);
            List<Guest> allGuests = jpaRepository.getAll();
            for(Guest guest : allGuests) {
                System.out.println(guest.getInfo());
            }
            jpaRepository.edit(newGuest.getId(), "Tony", "Stark", 45, Gender.MALE);
            jpaRepository.remove(newGuest.getId());
            guestService.readAll();
            roomService.readAll();
            reservationService.readAll();
        } catch (IOException e) {
            throw new PersistenceToFileException(SystemUtils.DATA_DIRECTORY.toString(), "create", "directory");
        }
        Application.launch(args);
//        textUI.showSystemInfo();
//        textUI.showMainMenu();
    }

    @Override
    public void start(Stage primaryStage) {
        PrimaryStage primary = new PrimaryStage();
        primary.initialize(primaryStage);
    }

    @Override
    public void stop() {
        System.out.println("Closing application. Saving data.");
        guestService.saveAll();
        roomService.saveAll();
        reservationService.saveAll();
    }
}