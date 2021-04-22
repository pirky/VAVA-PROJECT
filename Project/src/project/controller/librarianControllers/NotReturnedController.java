package project.controller.librarianControllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import project.controller.Main;
import project.model.books.Book;
import project.model.books.BookReservation;
import project.model.books.TableBook;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotReturnedController {
    ObservableList<TableBook> rentedBooks = FXCollections.observableArrayList();
    private final List<Reader> readers = new ArrayList<>();
    @FXML private TableView<TableBook> tableView;
    @FXML private TableColumn<TableBook, String> authorColumn;
    @FXML private TableColumn<TableBook, String> titleColumn;
    @FXML private TableColumn<TableBook, ImageView> imageColumn;
    @FXML TextField filterField;

    @FXML
    public void initialize(){
        for(User user: Main.userDatabase.getUserDatabase()){
            if(user instanceof Reader){
                readers.add((Reader) user);
            }
        }

        tableView.setRowFactory(tv -> new TableRow<TableBook>() {
            @Override
            protected void updateItem(TableBook item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null){
                    setStyle("");
                    return;
                }
                if(Main.booksDatabase.getDate().compareTo(item.getDateTo()) > 0){
                    setStyle("-fx-background-color: #ff9494;");
                }
                else setStyle("");
            }
        });

        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setCellFactory(param -> {
            TableCell<TableBook, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setTextFill(Color.RED);
            cell.setText("item");
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(param -> {
            TableCell<TableBook, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setTextFill(Color.RED);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        updateTableView();
    }

    public void updateTableView(){
        tableView.getItems().clear();
        for(Reader reader : readers){
            for(BookReservation bookReservation: reader.getReservations()){
                if(bookReservation.isReturned() == null || bookReservation.isReturned()){
                    continue;
                }

                if(bookReservation.getDateTo().compareTo(Main.booksDatabase.getDate()) < 0){
                    Book temp = Main.booksDatabase.getBooks().get(bookReservation.getBookId());
                    rentedBooks.add(new TableBook(temp.getId(), temp.getTitle(), temp.getAuthor(), temp.getNote(), temp.getImage(), bookReservation.getDateFrom(), bookReservation.getDateTo(), bookReservation.isReturned()));
                }
            }
        }

        FilteredList<TableBook> filteredData = new FilteredList<>(rentedBooks, b-> true);
        filterField.textProperty().addListener((observable, oldValue, newValue)-> filteredData.setPredicate(TableBook -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();
            if (TableBook.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return TableBook.getTitle().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<TableBook> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);

    }

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
