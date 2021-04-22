package project.controller.readerControllers.BookReservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import project.controller.Main;
import project.model.books.Book;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookReservationController{
    ObservableList<List<Book>> booksQuartet = FXCollections.observableArrayList();
    ObservableList<String> choices = FXCollections.observableArrayList();
    @FXML private ListView<List<Book>> listView;
    @FXML private ComboBox<String> comboBox;

    @FXML
    public void initialize(){
        listView.setCellFactory(ListView -> new InnerTableController());
        listView.setItems(booksQuartet);
        allBooks();
        choices.add("Všetky knihy");
        choices.add("Nové knihy");
        comboBox.setItems(choices);
        comboBox.getSelectionModel().select(0);
    }

    private void allBooks(){
        listView.getItems().clear();
        booksQuartet.clear();
        booksQuartet.add(new ArrayList<>());
        for (Book book: Main.booksDatabase.getBooks()){
            if(booksQuartet.get(booksQuartet.size() - 1).size() == 4){
                booksQuartet.add(new ArrayList<>());
            }
            booksQuartet.get(booksQuartet.size() - 1).add(book);
        }
    }

    private void newBooks(){
        listView.getItems().clear();
        booksQuartet.clear();
        booksQuartet.add(new ArrayList<>());
        for (Book book: Main.booksDatabase.getBooks()){
            if(Main.booksDatabase.getDate().compareTo(book.getCreatedAt()) >= 0){
                long daysBetween = ChronoUnit.DAYS.between(book.getCreatedAt(), Main.booksDatabase.getDate());
                if (daysBetween > 10){
                    continue;
                }
                if(booksQuartet.get(booksQuartet.size() - 1).size() == 4){
                    booksQuartet.add(new ArrayList<>());
                }
                booksQuartet.get(booksQuartet.size() - 1).add(book);
            }
        }
    }

    public void changeBooks(){
        String option = comboBox.getSelectionModel().getSelectedItem();
        if(option.equals(choices.get(0))){
            allBooks();
        }
        else if(option.equals(choices.get(1))){
            newBooks();
        }
    }

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/view/readerViews/ReaderView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
