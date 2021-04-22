package project.controller;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import project.model.databases.UserDatabase;
import java.io.IOException;

public class RegistrationController implements menuInterface{
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField password1;
    @FXML private ToggleGroup group;

    public void registerUser() throws IOException {
        String selectedType = ((RadioButton) group.getSelectedToggle()).getText();
        boolean flag = true;

        if (!UserDatabase.checkIfExists(username.getText())){
            if (username.getText().equals("") || password.getText().equals("")) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Nespravny vstup");
                errorAlert.setContentText("Nezadal si svoje meno alebo heslo");
                errorAlert.showAndWait();
                flag = false;
            }
            if (flag) {
                if (!password.getText().equals(password1.getText()))
                {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Nespravny vstup");
                    errorAlert.setContentText("hesla sa nezhoduju");
                    errorAlert.showAndWait();
                }
                else{
                    UserDatabase.registration(username.getText(), password.getText(), selectedType);
                    showMain();
                }
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

    public void showMain() throws IOException {
        this.changeMainView();
    }
}
