package project.controller.readerControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import project.controller.Main;
import project.model.books.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookReservationController{
    ObservableList<List<Book>> booksQuartet = FXCollections.observableArrayList();
    @FXML
    public ListView<List<Book>> listView;

    @FXML
    public void initialize(){
        booksQuartet.add(new ArrayList<>());
        for (Book book: Main.booksDatabase.getBooks()){
            if(booksQuartet.get(booksQuartet.size() - 1).size() == 4){
                booksQuartet.add(new ArrayList<>());
            }
            booksQuartet.get(booksQuartet.size() - 1).add(book);
        }
        listView.setItems(booksQuartet);
        listView.setCellFactory(ListView -> new InnerTableController());
    }

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/view/readerViews/ReaderView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
//        Main.mainStage.setWidth(600);
//        Main.mainStage.setHeight(400);
//        Main.mainStage.setMinHeight(0);
//        Main.mainStage.setMinWidth(0);
    }
}
