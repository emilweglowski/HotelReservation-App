package pl.emilweglowski.ui.gui.reservations;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jdk.jshell.execution.LoaderDelegate;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.domain.reservation.dto.ReservationDTO;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.domain.room.dto.RoomDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddNewReservationScene {

    private Scene mainScene;
    private RoomService roomService = ObjectPool.getRoomService();
    private GuestService guestService = ObjectPool.getGuestService();
    private ReservationService reservationService = ObjectPool.getReservationService();

    public AddNewReservationScene(Stage modalStage, TableView<ReservationDTO> tableView) {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.setHgap(10);

        Label fromDateLabel = new Label("Reservation start date:");
        DatePicker fromDateField = new DatePicker();

        gridPane.add(fromDateLabel, 0,0);
        gridPane.add(fromDateField, 1,0);

        Label toDateLabel = new Label("Reservation end date:");
        DatePicker toDateField = new DatePicker();

        gridPane.add(toDateLabel, 0,1);
        gridPane.add(toDateField, 1,1);

        List<RoomDTO> roomsAsDTO = roomService.getRoomsAsDTO();
        List<RoomSelectionItem> roomSelectionItems = new ArrayList<>();

        roomsAsDTO.forEach(dto -> {
            roomSelectionItems.add(new RoomSelectionItem(dto.getNumber(), (int)dto.getId()));
        });

        List<GuestSelectionItem> guestSelectionItems = new ArrayList<>();

        this.guestService.getGuestsAsDTO().forEach(dto -> {
            guestSelectionItems.add(new GuestSelectionItem(dto.getFirstName(), dto.getLastName(), (int)dto.getId()));
        });

        Label roomLabel = new Label("Room:");
        ComboBox<RoomSelectionItem> roomField = new ComboBox<>();
        roomField.getItems().addAll(roomSelectionItems);

        fromDateField.valueProperty().addListener((ov, oldValue, newValue) -> {
            LocalDate from = newValue;
            LocalDate to = toDateField.getValue();

            if (from!=null && to!=null) {
                List<RoomDTO> availableRoomsAsDTO = this.roomService.getAvailableRoomsAsDTO(from, to);
                roomSelectionItems.clear();
                for (RoomDTO dto : availableRoomsAsDTO) {
                    roomSelectionItems.add(new RoomSelectionItem(dto.getNumber(), (int)dto.getId()));
                }
                roomField.getItems().clear();
                roomField.getItems().addAll(roomSelectionItems);
            }
        });

        toDateField.valueProperty().addListener((ov, oldValue, newValue) -> {
            LocalDate from = fromDateField.getValue();
            LocalDate to = newValue;

            if (from!=null && to!=null) {
                List<RoomDTO> availableRoomsAsDTO = this.roomService.getAvailableRoomsAsDTO(from, to);
                roomSelectionItems.clear();
                for (RoomDTO dto : availableRoomsAsDTO) {
                    roomSelectionItems.add(new RoomSelectionItem(dto.getNumber(), (int)dto.getId()));
                }
                roomField.getItems().clear();
                roomField.getItems().addAll(roomSelectionItems);
            }
        });

        gridPane.add(roomLabel, 0,2);
        gridPane.add(roomField, 1,2);

        Label guestLabel = new Label("Guest:");
        ComboBox<GuestSelectionItem> guestField = new ComboBox<>();
        guestField.getItems().addAll(guestSelectionItems);

        gridPane.add(guestLabel, 0,3);
        gridPane.add(guestField, 1,3);

        Button button = new Button("Create reservation");
        button.setOnAction(actionEvent -> {
            LocalDate from = fromDateField.getValue();
            LocalDate to = toDateField.getValue();
            int guestId = guestField.getValue().getId();
            long roomId = roomField.getValue().getId();

            try {
                this.reservationService.createNewReservation(from, to, roomId, guestId);

                tableView.getItems().clear();
                tableView.getItems().addAll(this.reservationService.getReservationsAsDTO());
                modalStage.close();
            } catch (IllegalArgumentException exception) {
                Label errorLabel = new Label("Invalid reservation dates");
                errorLabel.setTextFill(Color.RED);
                gridPane.add(errorLabel, 0,5);
            }
        });

        gridPane.add(button, 1,4);

        this.mainScene = new Scene(gridPane,640, 480);
        this.mainScene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
    }

    public Scene getMainScene() {
        return this.mainScene;
    }
}
