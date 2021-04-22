package project.controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import project.model.databases.UserDatabase;
import project.model.users.Librarian;
import project.model.users.Organizer;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private DatePicker datePicker;
    @FXML private Text sign;
    @FXML private Button logInBtn;
    @FXML private Button registerBtn;
    private String error;
    private String error_msg;

    @FXML
    public void initialize(){
        datePicker.setValue(Main.booksDatabase.getDate());
        datePicker.setEditable(false);
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
        sign.setText(bundle.getString("sign"));
        logInBtn.setText(bundle.getString("logInBtn"));
        registerBtn.setText(bundle.getString("registerBtn"));
        error = bundle.getString("error");
        error_msg = bundle.getString("error_msg");
    }

    public void logIn() throws IOException {
        User user;
        user = UserDatabase.login(username.getText(), password.getText());
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(error);
            alert.setHeaderText(error);
            alert.setContentText(error_msg);
            alert.showAndWait();
        }
        else{
            Parent root = null;
            Locale locale;
            if (Main.currLanguage.equals("SK")) locale = new Locale("sk_SK");
            else locale = new Locale("en_US");

            if (user instanceof Librarian){
                ResourceBundle bundle = ResourceBundle.getBundle("project/resources.librarianView", locale);
                root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")), bundle);
            }
            else if (user instanceof Organizer){
                ResourceBundle bundle = ResourceBundle.getBundle("project/resources.organizerView", locale);
                root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/OrganizerView.fxml")), bundle);
            }
            else if (user instanceof Reader){
                ResourceBundle bundle = ResourceBundle.getBundle("project/resources.readerView", locale);
                root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/readerViews/ReaderView.fxml")), bundle);
            }
            Scene scene = new Scene(Objects.requireNonNull(root));
            Main.mainStage.setScene(scene);
            Main.mainStage.show();
        }
    }

    public void changeToRegisterView() throws IOException {
        Locale locale;
        if (Main.currLanguage.equals("SK")) locale = new Locale("sk_SK");
        else locale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.mainView", locale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/RegistrationView.fxml")), bundle);
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
        Locale locale;
        if (Main.currLanguage.equals("SK")) locale = new Locale("sk_SK");
        else locale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.librarianView", locale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")), bundle);
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
        Locale locale;
        if (Main.currLanguage.equals("SK")) locale = new Locale("sk_SK");
        else locale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.readerView", locale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/readerViews/ReaderView.fxml")), bundle);
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
        Locale locale;
        if (Main.currLanguage.equals("SK")) locale = new Locale("sk_SK");
        else locale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.organizerView", locale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/OrganizerView.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}