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
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.domain.reservation.dto.ReservationDTO;

import java.time.LocalDateTime;

public class ReservationsTab {

    private Tab reservationTab;
    private ReservationService reservationService = ObjectPool.getReservationService();

    public ReservationsTab(Stage primaryStage) {

        TableView<ReservationDTO> tableView = getReservationDTOTableView();

        Button button = new Button("Create new reservation");
        button.setOnAction(actionEvent -> {
            Stage addReservationPopup = new Stage();
            addReservationPopup.initModality(Modality.WINDOW_MODAL);
            addReservationPopup.initOwner(primaryStage);
            addReservationPopup.setScene(new AddNewReservationScene(addReservationPopup, tableView).getMainScene());
            addReservationPopup.setTitle("Create reservation");
            addReservationPopup.showAndWait();
        });

        VBox layout = new VBox(button, tableView);

        this.reservationTab = new Tab("Reservations", layout);
        this.reservationTab.setClosable(false);
    }

    private TableView<ReservationDTO> getReservationDTOTableView() {
        TableView<ReservationDTO> tableView = new TableView<>();

        TableColumn<ReservationDTO, LocalDateTime> fromColumn = new TableColumn<>("From");
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));

        TableColumn<ReservationDTO, LocalDateTime> toColumn = new TableColumn<>("To");
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));

        TableColumn<ReservationDTO, Integer> roomNumberColumn = new TableColumn<>("Room number");
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        TableColumn<ReservationDTO, String> guestNameColumn = new TableColumn<>("Guest name");
        guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));

        tableView.getColumns().addAll(fromColumn, toColumn, roomNumberColumn, guestNameColumn);

        tableView.getItems().addAll(reservationService.getReservationsAsDTO());
        return tableView;
    }

    public Tab getReservationTab() {
        return reservationTab;
    }
}
