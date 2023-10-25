package pl.emilweglowski.ui.gui.rooms;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
    private Stage primaryStage;

    public RoomsTab(Stage primaryStage) {

        TableView<RoomDTO> tableView = getRoomDTOTableView();
        this.primaryStage = primaryStage;
        Button button = new Button("Create new room");
        button.setOnAction(actionEvent -> {
            Stage addRoomStage = new Stage();
            addRoomStage.initModality(Modality.WINDOW_MODAL);
            addRoomStage.initOwner(this.primaryStage);
            addRoomStage.setScene(new AddNewRoomScene(addRoomStage, tableView).getMainScene());
            addRoomStage.setTitle("Create new room");
            addRoomStage.showAndWait();
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

        TableColumn<RoomDTO, RoomDTO> deleteColumn = new TableColumn<>("");
        deleteColumn.setCellValueFactory(value -> new ReadOnlyObjectWrapper<>(value.getValue()));

        deleteColumn.setCellFactory(param -> new TableCell<>() {

            Button deleteButton = new Button("Delete room");
            Button editButton = new Button("Edit room");
            HBox hBox = new HBox(deleteButton, editButton);
            @Override
            protected void updateItem(RoomDTO value, boolean empty) {
                super.updateItem(value, empty);

                if(value==null) {
                    setGraphic(null);
                } else {
                    setGraphic(hBox);
                    deleteButton.setOnAction(actionEvent -> {
                        roomService.removeRoom(value.getId());
                        tableView.getItems().remove(value);
                    });
                    editButton.setOnAction(actionEvent -> {
                        Stage editRoomStage = new Stage();
                        editRoomStage.initModality(Modality.WINDOW_MODAL);
                        editRoomStage.initOwner(primaryStage);
                        editRoomStage.setScene(new EditRoomScene(editRoomStage, tableView, value).getMainScene());
                        editRoomStage.setTitle("Edit room");
                        editRoomStage.showAndWait();
                    });
                }
            }
        });

        tableView.getColumns().addAll(numberColumn, roomSizeColumn, bedsCountColumn, bedsColumn, deleteColumn);

        List<RoomDTO> allAsDTO = roomService.getRoomsAsDTO();

        tableView.getItems().addAll(allAsDTO);
        return tableView;
    }

    public Tab getRoomTab() {
        return roomTab;
    }
}
