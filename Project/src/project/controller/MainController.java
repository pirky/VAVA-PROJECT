package project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import project.model.databases.UserDatabase;
import project.model.users.Librarian;
import project.model.users.Organizer;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
import java.util.Objects;

public class MainController {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private DatePicker datePicker;
    @FXML private Label sign;
    @FXML private Button engBtn;
    @FXML private Button skBtn;

    @FXML
    public void initialize(){
        datePicker.setValue(Main.booksDatabase.getDate());
        datePicker.setEditable(false);
    }

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
            Parent root = null;
            if (user instanceof Librarian){
                root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")));
            }
            else if (user instanceof Organizer){
                root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/OrganizerView.fxml")));
            }
            else if (user instanceof Reader){
                root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/readerViews/ReaderView.fxml")));
            }
            Scene scene = new Scene(Objects.requireNonNull(root));
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

    public void changeDate(){
        Main.booksDatabase.setDate(datePicker.getValue());
        datePicker.setValue(Main.booksDatabase.getDate());
    }

    public void knihovnik() throws IOException {
        for(User user: Main.userDatabase.getUserDatabase()){
            if(user.getUserName().equals("librarian")){
                Main.currUser = user;
                break;
            }
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    public void citatel() throws IOException {
        for(User user: Main.userDatabase.getUserDatabase()){
            if(user.getUserName().equals("reader")){
                Main.currUser = user;
                break;
            }
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/readerViews/ReaderView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    public void organizator() throws IOException {
        for(User user: Main.userDatabase.getUserDatabase()){
            if(user.getUserName().equals("organizer")){
                Main.currUser = user;
                break;
            }
        }
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/OrganizerView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

}
