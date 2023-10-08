package pl.emilweglowski.ui.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.guest.dto.GuestDTO;

public class GuestsTab {

    private Tab guestsTab;
    private GuestService guestService = ObjectPool.getGuestService();

    public GuestsTab() {

        TableView<GuestDTO> tableView = new TableView<>();

        TableColumn<GuestDTO, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<GuestDTO, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<GuestDTO, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<GuestDTO, String> genderColumn = new TableColumn<>("Gender");
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, ageColumn, genderColumn);

        tableView.getItems().addAll(guestService.getGuestsAsDTO());

        this.guestsTab = new Tab("Guests", tableView);
        this.guestsTab.setClosable(false);
    }

    public Tab getGuestsTab(){
        return guestsTab;
    }
}
