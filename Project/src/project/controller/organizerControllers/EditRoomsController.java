package project.controller.organizerControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import project.controller.Main;
import project.model.CustomImage;
import project.model.Rooms.LibraryRoom;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditRoomsController implements Initializable {

    @FXML ComboBox<LibraryRoom> roomComboBox;
    @FXML ListView<ImageView> roomPictures;

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/OrganizerView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    ObservableList<ImageView> imageViews = FXCollections.observableArrayList();
    ObservableList<LibraryRoom> allRooms = FXCollections.observableArrayList();



    public void comboClick(){
        LibraryRoom libraryRoom = roomComboBox.getValue();
        if (libraryRoom == null){
            return;
        }
        roomPictures.getItems().clear();
        for(CustomImage image: libraryRoom.getImages()){
            ImageView imageView = new ImageView(image.getImage());
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(493);
            imageViews.add(imageView);
        }
        roomPictures.setItems(imageViews);
        roomPictures.refresh();
    }


    public void addImage(){
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Vyber fotku izby");
        Image image = new Image(fileChooser.showOpenDialog(Main.mainStage).toURI().toString());
        LibraryRoom libraryRoom = (LibraryRoom) roomComboBox.getValue();
        libraryRoom.addImage(new CustomImage(image));
        comboClick();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allRooms.addAll(Main.roomsDatabase.getRooms());
        roomComboBox.setItems(allRooms);
    }

}


