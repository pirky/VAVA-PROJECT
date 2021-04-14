package project.controller.readerControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import project.controller.Main;
import project.controller.menuInterface;

import java.io.IOException;
import java.util.Objects;

public class ReaderController implements menuInterface {

    public void bookReservation() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/readerViews/BookReservationView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
        Main.mainStage.setMinWidth(1300);
        Main.mainStage.setMinHeight(700);
    }

    public void showMain() throws IOException {
        this.changeMainView();
    }
}
