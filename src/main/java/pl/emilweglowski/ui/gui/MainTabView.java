package pl.emilweglowski.ui.gui;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import pl.emilweglowski.ui.gui.guests.GuestsTab;
import pl.emilweglowski.ui.gui.reservations.ReservationsTab;
import pl.emilweglowski.ui.gui.rooms.RoomsTab;

public class MainTabView {

    private TabPane mainTabs;

    public MainTabView(Stage primaryStage) {
        this.mainTabs = new TabPane();

        GuestsTab guestsTab = new GuestsTab(primaryStage);
        RoomsTab roomsTab = new RoomsTab(primaryStage);
        ReservationsTab reservationTab = new ReservationsTab(primaryStage);

        this.mainTabs.getTabs().addAll(reservationTab.getReservationTab(),
                guestsTab.getGuestsTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}
