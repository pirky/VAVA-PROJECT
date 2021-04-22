package project.controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

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
        Locale locale;
        if (Main.currLanguage.equals("SK")) locale = new Locale("sk_SK");
        else locale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.mainView", locale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/view/MainView.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
