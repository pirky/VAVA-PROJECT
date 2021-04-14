package project.controller.readerControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import project.model.books.Book;
import java.io.IOException;
import java.util.List;

public class InnerTableController  extends ListCell<List<Book>> {
    ObservableList<Book> booksQuartet = FXCollections.observableArrayList();
    @FXML private ListView<Book> listView;
    @FXML private FXMLLoader mLLoader;

    @Override
    protected void updateItem(List<Book> bookList, boolean empty) {
        super.updateItem(bookList, empty);
        super.setPrefWidth(310);
        if (empty || bookList == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/project/view/readerViews/InnerTableView.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            booksQuartet.addAll(bookList);
            listView.setItems(booksQuartet);
            listView.setOrientation(Orientation.HORIZONTAL);
            listView.setCellFactory(ListView -> new CellController());
            setText(null);
            setGraphic(listView);
            listView.setOnMouseReleased(mouseEvent -> showBook());
        }
    }

    public void showBook(){
        Book selectedBook = listView.getSelectionModel().getSelectedItems().get(0);
        System.out.println(selectedBook);
    }
}
