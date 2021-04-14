package project.controller.readerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import project.controller.Main;

import java.io.IOException;
import java.util.Objects;

public class BookInfoController{
    @FXML private DatePicker datePicker;

    @FXML
    public void initialize(){

    }

    public void showBooks() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/view/readerViews/BookReservationView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
