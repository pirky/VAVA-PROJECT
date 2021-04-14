package project.controller.librarianControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import project.controller.Main;
import project.model.books.BookReservation;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
import java.util.Objects;

public class GiveBooksController {
    ObservableList<Reader> readers = FXCollections.observableArrayList();
    ObservableList<BookReservation> bookReservations = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Reader> readersBox;
    @FXML
    private ListView<BookReservation> listView;
    @FXML
    private Label infoLabel;

    @FXML
    public void initialize(){
        for(User user: Main.userDatabase.getUserDatabase()){
            if(user instanceof Reader){
                readers.add((Reader) user);
            }
        }
        readersBox.setItems(readers);
        infoLabel.setVisible(false);
    }

    public void showReservations(){
        Reader reader = readersBox.getValue();
        if (reader == null){
            return;
        }
        bookReservations.clear();
        for(BookReservation bookReservation: reader.getReservations()){
            if (bookReservation.isReturned() == null && (bookReservation.getDateFrom().equals(Main.booksDatabase.getDate()) ||
                    (bookReservation.getDateFrom().isBefore(Main.booksDatabase.getDate()) &&
                            bookReservation.getDateTo().isAfter(Main.booksDatabase.getDate())))){
                bookReservations.add(bookReservation);
            }
        }
        listView.setItems(bookReservations);
        listView.refresh();
    }

    public void giveBooks(){
        Reader reader = readersBox.getValue();
        if(reader == null){
            infoLabel.setVisible(true);
            infoLabel.setText("Vyber čítateľa");
            return;
        }
        else {
            infoLabel.setVisible(false);
        }

        for(BookReservation bookReservation: bookReservations){
            bookReservation.setReturned(false);
            reader.removeReservation(bookReservation);
            reader.addReservation(bookReservation);
        }
        deleteFields();
    }

    private void deleteFields(){
        listView.getItems().clear();
        listView.refresh();
        readersBox.getSelectionModel().clearSelection();
    }

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
