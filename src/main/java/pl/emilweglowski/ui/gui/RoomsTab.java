package pl.emilweglowski.ui.gui;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.domain.room.dto.RoomDTO;

import java.util.List;

public class RoomsTab {

    private Tab roomTab;
    private RoomService roomService = ObjectPool.getRoomService();

    public RoomsTab(Stage primaryStage) {

        TableView<RoomDTO> tableView = getRoomDTOTableView();

        Button button = new Button("Create new");
        button.setOnAction(actionEvent -> {
            Stage addRoomPopup = new Stage();
            addRoomPopup.initModality(Modality.WINDOW_MODAL);
            addRoomPopup.setScene(new addNewRoomScene(addRoomPopup, tableView).getMainScene());
            addRoomPopup.initOwner(primaryStage);
            addRoomPopup.setTitle("Create new room");
            addRoomPopup.showAndWait();

        });

        VBox layout = new VBox(button, tableView);

        this.roomTab = new Tab("Rooms", layout);
        this.roomTab.setClosable(false);
    }

    private TableView<RoomDTO> getRoomDTOTableView() {
        TableView<RoomDTO> tableView = new TableView<>();

        TableColumn<RoomDTO, Integer> numberColumn = new TableColumn<>("Room number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));

        TableColumn<RoomDTO, String> bedsColumn = new TableColumn<>("Bed types");
        bedsColumn.setCellValueFactory(new PropertyValueFactory<>("beds"));

        TableColumn<RoomDTO, Integer> bedsCountColumn = new TableColumn<>("Beds number");
        bedsCountColumn.setCellValueFactory(new PropertyValueFactory<>("bedsCount"));

        TableColumn<RoomDTO, Integer> roomSizeColumn = new TableColumn<>("Room size");
        roomSizeColumn.setCellValueFactory(new PropertyValueFactory<>("roomSize"));

        tableView.getColumns().addAll(numberColumn, roomSizeColumn, bedsCountColumn, bedsColumn);

        List<RoomDTO> allAsDTO = roomService.getRoomsAsDTO();

        tableView.getItems().addAll(allAsDTO);
        return tableView;
    }

    public Tab getRoomTab() {
        return roomTab;
    }
}
