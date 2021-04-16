package project.controller.librarianControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import project.controller.Main;
import project.controller.menuInterface;

import java.io.IOException;
import java.util.Objects;

public class LibrarianController implements menuInterface {

    @FXML
    public void initialize(){

    }

    public void giveBook() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/GiveBooksView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    public void returnBook() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/ReturnBooksView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    public void showMain() throws IOException {
        this.changeMainView();
    }

    public void addNewBook() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/AddBookView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }



}
