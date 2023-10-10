package pl.emilweglowski.ui.gui;

import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class MainTabView {

    private TabPane mainTabs;

    public MainTabView(Stage primaryStage) {
        this.mainTabs = new TabPane();

        GuestsTab guestsTab = new GuestsTab();
        RoomsTab roomsTab = new RoomsTab(primaryStage);
        ReservationsTab reservationTab = new ReservationsTab();

        this.mainTabs.getTabs().addAll(reservationTab.getReservationTab(),
                guestsTab.getGuestsTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}
