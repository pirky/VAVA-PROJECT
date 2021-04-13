package project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import project.model.books.Book;

import java.util.ArrayList;
import java.util.List;

public class tempController {
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
}
