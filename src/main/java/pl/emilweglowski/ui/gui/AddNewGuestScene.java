package pl.emilweglowski.ui.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.guest.dto.GuestDTO;
import pl.emilweglowski.util.Properties;

public class AddNewGuestScene {

    private Scene mainScene;
    private GuestService guestService = ObjectPool.getGuestService();

    public AddNewGuestScene(Stage addGuestPopup, TableView<GuestDTO> guestsTableView) {

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(15);
        gridPane.setHgap(10);

        Label firstNameLabel = new Label("First name:");
        TextField firstNameField = new TextField();

        firstNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\p{L}*")) {
                firstNameField.setText(oldValue);
            }
        });

        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(firstNameField, 1, 0);

        Label lastNameLabel = new Label("Last name:");
        TextField lastNameField = new TextField();

        lastNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\p{L}*")) {
                lastNameField.setText(oldValue);
            }
        });

        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameField, 1, 1);

        Label ageLabel = new Label("Age:");
        TextField ageField = new TextField();

        ageField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageField.setText(oldValue);
            }
        });

        gridPane.add(ageLabel, 0, 2);
        gridPane.add(ageField, 1, 2);

        Label genderLabel = new Label("Gender:");
        ComboBox<String> genderField = new ComboBox<>();
        genderField.getItems().addAll(Properties.FEMALE, Properties.MALE);
        genderField.setValue(Properties.FEMALE);

        gridPane.add(genderLabel, 0, 3);
        gridPane.add(genderField, 1, 3);

        Button createNewGuestButton = new Button("Create new guest");

        createNewGuestButton.setOnAction(actionEvent -> {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String gender = genderField.getValue();

            boolean isMale = false;
            if(gender.equals(Properties.MALE)){
                isMale = true;
            }

            this.guestService.createNewGuest(firstName, lastName, age, isMale);

            guestsTableView.getItems().clear();
            guestsTableView.getItems().addAll(this.guestService.getGuestsAsDTO());

            addGuestPopup.close();
        });

        gridPane.add(createNewGuestButton, 1, 4);

        this.mainScene = new Scene(gridPane, 640, 640);
        this.mainScene.getStylesheets().add(getClass().getClassLoader()
                .getResource("hotelReservation.css").toExternalForm());
    }

    public Scene getMainScene() {
        return this.mainScene;
    }
}
