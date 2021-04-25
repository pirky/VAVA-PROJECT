package project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.model.databases.UserDatabase;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationController implements menuInterface{
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField password1;
    @FXML private ToggleGroup group;
    @FXML private RadioButton librarianRB;
    @FXML private RadioButton organizerRB;
    @FXML private RadioButton readerRB;
    @FXML private Button registerBtn;
    private String error;
    private String error_msg;
    private String wrong_input;
    private String existing_user;
    private String not_matching;

    @FXML
    public void initialize(){
        if (Main.currLanguage.equals("SK")) languageSK();
        else languageEN();
    }

    public void languageEN(){
        Main.currLanguage = "US";
        Locale enLocale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.mainView", enLocale);
        changeSigns(bundle);
    }

    public void languageSK(){
        Main.currLanguage = "SK";
        Locale skLocale = new Locale("sk_SK");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.mainView", skLocale);
        changeSigns(bundle);
    }

    public void changeSigns(ResourceBundle bundle){
        username.setPromptText(bundle.getString("username"));
        password.setPromptText(bundle.getString("password"));
        password1.setPromptText(bundle.getString("confirm_password"));
        registerBtn.setText(bundle.getString("registerBtn"));
        librarianRB.setText(bundle.getString("librarianRB"));
        organizerRB.setText(bundle.getString("organizerRB"));
        readerRB.setText(bundle.getString("readerRB"));
        error = bundle.getString("error");
        error_msg = bundle.getString("error_msg");
        wrong_input = bundle.getString("wrong_input");
        existing_user = bundle.getString("existing_user");
        not_matching = bundle.getString("not_matching");
    }

    public void registerUser() throws IOException {
        String selectedType = ((RadioButton) group.getSelectedToggle()).getText();
        boolean flag = true;

        if (!UserDatabase.checkIfExists(username.getText())){
            if (username.getText().equals("") || password.getText().equals("")) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                Stage stage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("project/images/other/logo.jpg"));
                errorAlert.setHeaderText(wrong_input);
                errorAlert.setContentText(error_msg);
                errorAlert.showAndWait();
                flag = false;
            }
            if (flag) {
                if (!password.getText().equals(password1.getText())) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    Stage stage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("project/images/other/logo.jpg"));
                    errorAlert.setHeaderText(wrong_input);
                    errorAlert.setContentText(not_matching);
                    errorAlert.showAndWait();
                }
                else{
                    switch (selectedType) {
                        case "librarian":
                            selectedType = "knihovník";
                            break;
                        case "reader":
                            selectedType = "čítateľ";
                            break;
                        case "organizer":
                            selectedType = "organizátor";
                            break;
                    }
                    UserDatabase.registration(username.getText(), password.getText(), selectedType);
                    showMain();
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("project/images/other/logo.jpg"));
            alert.setTitle(error);
            alert.setHeaderText(error);
            alert.setContentText(existing_user);
            alert.showAndWait();
        }
    }

    public void showMain() throws IOException {
        this.changeMainView();
    }
}
