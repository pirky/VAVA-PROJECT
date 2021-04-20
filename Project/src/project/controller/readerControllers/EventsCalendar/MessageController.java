package project.controller.readerControllers.EventsCalendar;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import project.controller.readerControllers.BookReservation.CellController;
import project.model.books.Book;
import project.model.events.Message;

import java.io.IOException;

public class MessageController extends ListCell<Message> {
    @FXML
    private Label label;
    @FXML
    private TextArea textArea;
    @FXML
    private HBox hBox;
    @FXML
    private VBox vBox;
    @FXML
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(Message message, boolean empty) {
        super.updateItem(message, empty);
        super.setPrefWidth(670);
        if (empty || message == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/project/view/readerViews/BookReservation/InnerTableView.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            booksQuartet.clear();
//            listView.getItems().clear();
//            booksQuartet.addAll(bookList);
//            listView.setItems(booksQuartet);
//            listView.setOrientation(Orientation.HORIZONTAL);
//            listView.setCellFactory(ListView -> new CellController());
//            setText(null);
//            setGraphic(listView);
//            listView.setOnMouseReleased(mouseEvent -> {
//                try {
//                    showBook();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
        }
    }

//    @FXML
//    public void initialize(){
//        textArea.setText("serus");
//        textArea.textProperty().addListener((ChangeListener) (observable, oldValue, newValue) -> {
//            Text text = new Text();
//            text.setWrappingWidth(textArea.getWidth());
//            text.setText((String)newValue);
//        });
//    }
}
