package pl.emilweglowski.ui.gui;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainTabView {

    private TabPane mainTabs;

    public MainTabView() {
        this.mainTabs = new TabPane();

        GuestsTab guestsTab = new GuestsTab();
        RoomsTab roomsTab = new RoomsTab();
        ReservationsTab reservationTab = new ReservationsTab();

        this.mainTabs.getTabs().addAll(reservationTab.getReservationTab(),
                guestsTab.getGuestsTab(), roomsTab.getRoomTab());
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}
