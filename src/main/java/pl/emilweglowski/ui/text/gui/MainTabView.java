package pl.emilweglowski.ui.text.gui;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainTabView {

    private TabPane mainTabs;

    public MainTabView() {
        this.mainTabs = new TabPane();

        Tab reservationTab = new Tab("Reservations", new Label("Reservations service"));
        Tab guestTab = new Tab("Guests", new Label("Guests service"));
        Tab roomTab = new Tab("Rooms", new Label("Rooms service"));

        reservationTab.setClosable(false);
        guestTab.setClosable(false);
        roomTab.setClosable(false);

        this.mainTabs.getTabs().addAll(reservationTab, guestTab, roomTab);
    }

    TabPane getMainTabs() {
        return mainTabs;
    }
}
