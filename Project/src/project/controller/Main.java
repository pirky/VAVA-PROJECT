package project.controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import project.model.databases.BooksDatabase;
import project.model.databases.RoomsDatabase;
import project.model.databases.UserDatabase;
import project.model.users.User;

import java.util.Objects;

public class Main extends Application {
    public static Stage mainStage;
    public static BooksDatabase booksDatabase = new BooksDatabase();
    public static UserDatabase userDatabase = new UserDatabase();
    public static RoomsDatabase roomsDatabase = new RoomsDatabase();
    public static User currUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setTitle("eLib");
        mainStage.getIcons().add(new Image("project/images/logo.jpg"));
        mainStage.setResizable(false);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/MainView.fxml")));
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }








}
