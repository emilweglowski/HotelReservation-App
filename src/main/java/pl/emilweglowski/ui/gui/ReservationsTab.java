package pl.emilweglowski.ui.gui;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.domain.reservation.dto.ReservationDTO;

import java.time.LocalDateTime;

public class ReservationsTab {

    private Tab reservationTab;
    private ReservationService reservationService = new ReservationService();

    public ReservationsTab() {

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

        this.reservationTab = new Tab("Reservations", tableView);
        this.reservationTab.setClosable(false);
    }

    public Tab getReservationTab() {
        return reservationTab;
    }
}
