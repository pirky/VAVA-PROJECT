package project.controller.organizerControllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import java.util.*;

public class EditRoomsController implements Initializable {
    ObservableList<ImageView> imageViews = FXCollections.observableArrayList();
    ObservableList<LibraryRoom> allRooms = FXCollections.observableArrayList();
    @FXML ComboBox<LibraryRoom> roomComboBox;
    @FXML ListView<ImageView> roomPictures;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allRooms.addAll(Main.roomsDatabase.getRooms());
        roomComboBox.setItems(allRooms);
    }


    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/organizerViews/OrganizerView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }


    public void comboClick(){
        LibraryRoom libraryRoom = roomComboBox.getValue();
        if (libraryRoom == null){
            return;
        }
        for(LibraryRoom roomFor: Main.roomsDatabase.getRooms()) {
            if (roomFor.getName().equals(libraryRoom.getName()))
            {
                libraryRoom = roomFor;
            }
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


    public void addImage() {

        try {
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(imageFilter);
            fileChooser.setTitle("Vyber fotku izby");
            Image image = new Image(fileChooser.showOpenDialog(Main.mainStage).toURI().toString());
            LibraryRoom libraryRoom = roomComboBox.getValue();

            for (LibraryRoom roomFor : Main.roomsDatabase.getRooms()) {
                List<CustomImage> imagesReal = roomFor.getRealImages();
                if (libraryRoom.getName().equals(roomFor.getName())) {
                    imagesReal.add(new CustomImage(image));
                }

                Main.roomsDatabase.removeRoom(roomFor);
                Main.roomsDatabase.addRoom(roomFor);
            }

            comboClick();
        } catch (Exception e) {
            if (roomComboBox.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nastala chyba");
                alert.setContentText("nevybral si miestnost");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nastala chyba");
                alert.setContentText("nevybral si obrazok");
                alert.showAndWait();
            }
        }
    }


    public void deletePhoto(){
        try {

            Image imageToDel = roomPictures.getSelectionModel().getSelectedItem().getImage();
            List<LibraryRoom> rooms = Main.roomsDatabase.getRooms();

            for(LibraryRoom roomFor: rooms){
                List<CustomImage> imagesReal = roomFor.getRealImages();

                for(CustomImage imageFor: imagesReal){
                    if (imageFor.getImage().equals(imageToDel)) {
                        imagesReal.remove(imageFor);
                        Main.roomsDatabase.removeRoom(roomFor);
                        Main.roomsDatabase.addRoom(roomFor);

                        List<LibraryRoom> roomsSort;
                        roomsSort = Main.roomsDatabase.getRooms();

                        roomsSort.sort(Comparator.comparing(LibraryRoom::getName));
                        Main.roomsDatabase.setRooms(roomsSort);

                        String string = roomComboBox.getSelectionModel().getSelectedItem().getName();
                        allRooms.clear();
                        allRooms.addAll(Main.roomsDatabase.getRooms());
                        roomComboBox.setItems(allRooms);
                        roomComboBox.getSelectionModel().select(Integer.parseInt(string.substring(string.length() - 1))-1);

                        roomPictures.getItems().clear();
                        for(CustomImage image: roomFor.getImages()){
                            ImageView imageView = new ImageView(image.getImage());
                            imageView.setPreserveRatio(true);
                            imageView.setFitWidth(493);
                            imageViews.add(imageView);
                        }
                        roomPictures.setItems(imageViews);
                        roomPictures.refresh();
                        return;
                    }
                }
            }

        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Nastala chyba");
            alert.setContentText("Vyber ktory obrazok chces zmazat");
            alert.showAndWait();
        }
    }
}