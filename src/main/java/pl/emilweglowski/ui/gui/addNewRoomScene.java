package pl.emilweglowski.ui.gui;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.domain.room.dto.RoomDTO;

import java.util.ArrayList;
import java.util.List;

public class addNewRoomScene {

    private final Scene mainScene;
    private final List<ComboBox<String>> comboBoxes = new ArrayList<>();
    private final RoomService roomService = ObjectPool.getRoomService();

    public addNewRoomScene(Stage addRoomPopup, TableView<RoomDTO> tableView){
        Label roomNumberLabel = new Label("Room number:");
        TextField roomNumberField = new TextField();
        HBox roomNumberRow = new HBox(roomNumberLabel, roomNumberField);

        Label bedTypeLabel = new Label("Bed types:");
        Button addNewBedButton = new Button("Add new bed");

        HBox bedTypeRow = new HBox(bedTypeLabel, addNewBedButton);

        VBox bedsVerticalLayout = new VBox(bedTypeRow, createComboBox());

        addNewBedButton.setOnAction(actionEvent -> {
            bedsVerticalLayout.getChildren().add(createComboBox());
        });

        Button addNewRoomButton = new Button("Add new room");
        addNewRoomButton.setOnAction(actionEvent -> {
            int newRoomNumber = Integer.parseInt(roomNumberField.getText());
            List<String> bedTypes = new ArrayList<>();
            this.comboBoxes.forEach(comboBox -> {
                bedTypes.add(comboBox.getValue());
            });
            this.roomService.createNewRoom(newRoomNumber, bedTypes);

            tableView.getItems().clear();

            List<RoomDTO> allAsDTO = roomService.getRoomsAsDTO();
            tableView.getItems().addAll(allAsDTO);

            addRoomPopup.close();
        });

        VBox mainFormLayout = new VBox(roomNumberRow, bedsVerticalLayout, addNewRoomButton);

        this.mainScene = new Scene(mainFormLayout,640, 480);
    }

    private ComboBox<String> createComboBox() {
        ComboBox<String> bedTypeField = new ComboBox<>();
        bedTypeField.getItems().addAll("Single", "Double", "King size");
        bedTypeField.setValue("Single");
        this.comboBoxes.add(bedTypeField);
        return bedTypeField;
    }

    public Scene getMainScene() {
        return mainScene;
    }
}
