package pl.emilweglowski.ui.gui.guests;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.guest.dto.GuestDTO;

public class GuestsTab {

    private Tab guestsTab;
    private GuestService guestService = ObjectPool.getGuestService();
    private Stage primaryStage;

    public GuestsTab(Stage primaryStage) {

        TableView<GuestDTO> tableView = getGuestDTOTableView();
        this.primaryStage = primaryStage;
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

        TableColumn<GuestDTO, GuestDTO> deleteColumn = new TableColumn<>("");
        deleteColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<>() {

            Button deleteButton = new Button("Delete guest");
            Button editButton = new Button("Edit guest");
            HBox hBox = new HBox(deleteButton, editButton);
            @Override
            protected void updateItem(GuestDTO value, boolean empty) {
                super.updateItem(value, empty);

                if(value==null) {
                    setGraphic(null);
                } else {
                    setGraphic(hBox);
                    deleteButton.setOnAction(actionEvent -> {
                        guestService.removeGuest(value.getId());
                        tableView.getItems().remove(value);
                    });
                    editButton.setOnAction(actionEvent -> {
                        Stage editGuestStage = new Stage();
                        editGuestStage.initModality(Modality.WINDOW_MODAL);
                        editGuestStage.initOwner(primaryStage);
                        editGuestStage.setScene(new EditGuestScene(editGuestStage, tableView, value).getMainScene());
                        editGuestStage.setTitle("Edit guest");
                        editGuestStage.showAndWait();
                    });
                }
            }
        });

        tableView.getColumns().addAll(firstNameColumn, lastNameColumn, ageColumn, genderColumn, deleteColumn);

        tableView.getItems().addAll(guestService.getGuestsAsDTO());
        return tableView;
    }

    public Tab getGuestsTab(){
        return guestsTab;
    }
}
