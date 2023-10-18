package pl.emilweglowski.ui.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.guest.dto.GuestDTO;

public class GuestsTab {

    private Tab guestsTab;
    private GuestService guestService = ObjectPool.getGuestService();

    public GuestsTab(Stage primaryStage) {

        TableView<GuestDTO> tableView = getGuestDTOTableView();

        Button button = new Button("Add new guest");
        button.setOnAction(actionEvent -> {
            Stage addGuestPopup = new Stage();
            addGuestPopup.initModality(Modality.WINDOW_MODAL);
            addGuestPopup.initOwner(primaryStage);
            addGuestPopup.setScene(new AddNewGuestScene(addGuestPopup, tableView).getMainScene());
            addGuestPopup.setTitle("Add new guest");
            addGuestPopup.showAndWait();
        });

        VBox layout = new VBox(button, tableView);

        this.guestsTab = new Tab("Guests", layout);
        this.guestsTab.setClosable(false);
    }

    private TableView<GuestDTO> getGuestDTOTableView() {
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
        return tableView;
    }

    public Tab getGuestsTab(){
        return guestsTab;
    }
}
