package project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import project.controller.Main;
import project.model.databases.UserDatabase;
import project.model.users.User;

import java.io.IOException;

public class RegistrationController {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private ToggleGroup group;

    public void registerUser() throws IOException {
        String selectedType = ((RadioButton) group.getSelectedToggle()).getText();
        boolean flag = true;
        int number = 0;
        if (selectedType.equals("knihovník")){
            number = 1;
        }
        if (selectedType.equals("organizátor")){
            number = 2;
        }
        if (selectedType.equals("čitateľ")){
            number = 3;
        }
        if (UserDatabase.checkIfExists(username.getText()) == false){
            if (username.getText().equals("") || password.getText().equals("")) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Nespravny vstup");
                errorAlert.setContentText("Nezadal si svoje meno alebo heslo");
                errorAlert.showAndWait();
                flag = false;
            }
            if (flag == true) {
                UserDatabase.registration(username.getText(), password.getText(), number);
                Parent root = FXMLLoader.load(Main.class.getResource("/project/view/MainView.fxml"));
                Scene scene = new Scene(root);
                Main.mainStage.setScene(scene);
                Main.mainStage.show();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Nastala chyba");
            alert.setContentText("Uzivatel s takymto menom uz existuje");
            alert.showAndWait();
        }

    }


}
