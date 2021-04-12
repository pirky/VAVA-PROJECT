package project.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

/**
 * Interface with default method to evade duplicate code
 * @author pirky
 */
public interface menuInterface {

    /**
     * change current scene to main scene
     * @throws IOException Exception for reading from file
     */
    default void changeMainView() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/view/MainView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
