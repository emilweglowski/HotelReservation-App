package pl.emilweglowski.ui.gui.guests;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.guest.dto.GuestDTO;
import pl.emilweglowski.util.SystemUtils;

public class EditGuestScene {
    private Scene mainScene;
    private GuestService guestService = ObjectPool.getGuestService();

    public EditGuestScene(Stage editGuestStage, TableView<GuestDTO> tableView, GuestDTO guest) {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(10);

        Label firstNameLabel = new Label("First name:");
        TextField firstNameField = new TextField();
        firstNameField.setText(guest.getFirstName());

        firstNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\p{L}*")) {
                firstNameField.setText(oldValue);
            }
        });

        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(firstNameField, 1, 0);

        Label lastNameLabel = new Label("Last name:");
        TextField lastNameField = new TextField();
        lastNameField.setText(guest.getLastName());

        lastNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\p{L}*")) {
                lastNameField.setText(oldValue);
            }
        });

        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);

        Label ageLabel = new Label("Age:");
        TextField ageField = new TextField();
        ageField.setText(String.valueOf(guest.getAge()));

        ageField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageField.setText(oldValue);
            }
        });

        gridPane.add(ageLabel, 0, 2);
        gridPane.add(ageField, 1, 2);

        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll(SystemUtils.FEMALE, SystemUtils.MALE);
        genderField.setValue(guest.getGender());

        gridPane.add(genderLabel, 0, 3);
        gridPane.add(genderField, 1, 3);

        Button editGuestButton = new Button("Edit guest");

        editGuestButton.setOnAction(actionEvent -> {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getValue();

            boolean isMale = false;
            if(gender.equals(SystemUtils.MALE)){
                isMale = true;
            }

            this.guestService.editGuest(guest.getId(), firstName, lastName, age, isMale);

            tableView.getItems().clear();
            tableView.getItems().addAll(this.guestService.getGuestsAsDTO());

            editGuestStage.close();
        });

        gridPane.add(editGuestButton, 1, 4);

        this.mainScene = new Scene(gridPane, 640, 640);
        this.mainScene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
    }

    public Scene getMainScene() {
        return this.mainScene;
    }
}
