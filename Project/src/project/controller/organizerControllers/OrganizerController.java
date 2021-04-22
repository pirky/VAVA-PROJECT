package project.controller.organizerControllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import project.controller.Main;
import project.controller.menuInterface;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class OrganizerController implements menuInterface {
    @FXML private Button button1;
    @FXML private Button button2;

    @FXML
    public void initialize(){
        languageSK();
    }

    public void addEvent() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/AddEventView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    public void showMenu() throws IOException {
        this.changeMainView();
    }

    public void editEvent() throws IOException {
        Locale skLocale = new Locale("sk_SK");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.organizerView", skLocale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/EditEventView.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    public void languageEN(){
        Locale enLocale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.organizerView", enLocale);
        changeSigns(bundle);
    }

    public void languageSK(){
        Locale skLocale = new Locale("sk_SK");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.organizerView", skLocale);
        changeSigns(bundle);
    }

    public void changeSigns(ResourceBundle bundle){
        button1.setText(bundle.getString("button1"));
        button2.setText(bundle.getString("button2"));
    }
}
