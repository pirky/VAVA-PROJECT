package project.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import project.controller.Main;
import project.model.databases.UserDatabase;
import project.model.users.User;

import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML private TextField username;
    @FXML private TextField password;

    public void logIn() throws IOException {
        User user;
        user = UserDatabase.login(username.getText(), password.getText());
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Nastala chyba");
            alert.setContentText("Zadal si zle meno alebo heslo");
            alert.showAndWait();
        }
        else{
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/LoggedIn.fxml")));
            Scene scene = new Scene(root);
            Main.mainStage.setScene(scene);
            Main.mainStage.show();
        }
    }


    public void changeToRegisterView() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/RegistrationView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    public void temp() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/tempView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

}
