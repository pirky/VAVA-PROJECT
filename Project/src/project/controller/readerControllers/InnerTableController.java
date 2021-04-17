package project.controller.readerControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import project.controller.Main;
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

            booksQuartet.clear();
            listView.getItems().clear();
            booksQuartet.addAll(bookList);
            listView.setItems(booksQuartet);
            listView.setOrientation(Orientation.HORIZONTAL);
            listView.setCellFactory(ListView -> new CellController());
            setText(null);
            setGraphic(listView);
            listView.setOnMouseReleased(mouseEvent -> {
                try {
                    showBook();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void showBook() throws IOException {
        Book selectedBook = listView.getSelectionModel().getSelectedItems().get(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/readerViews/BookInfoView.fxml"));
        Parent root = loader.load();
        BookInfoController bookInfoController = loader.getController();
        bookInfoController.setBook(selectedBook);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
