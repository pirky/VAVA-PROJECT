package project.controller.librarianControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import project.controller.Main;
import project.model.books.Book;
import project.model.books.BookReservation;
import project.model.books.TableBook;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class ReturnBooksController {
    ObservableList<project.model.users.Reader> readers = FXCollections.observableArrayList();
    ObservableList<TableBook> rentedBooks = FXCollections.observableArrayList();
    private Reader reader;
    @FXML
    private ComboBox<Reader> readersBox;
    @FXML
    private TableView<TableBook> tableView;
    @FXML
    private TableColumn<TableBook, String> authorColumn;
    @FXML
    private TableColumn<TableBook, String> titleColumn;
    @FXML
    private TableColumn<TableBook, ImageView> imageColumn;
    @FXML
    private DatePicker datePicker;

    @FXML
    public void initialize(){
        for(User user: Main.userDatabase.getUserDatabase()){
            if(user instanceof Reader){
                readers.add((Reader) user);
            }
        }
        readersBox.setItems(readers);

        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        authorColumn.setCellFactory(param -> {
            TableCell<TableBook, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
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
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageView"));
    }

    public void updateTableView(){
//        tableView.getItems().clear();
//        reader = readersBox.getValue();
//        for(Book book: Main.booksDatabase.getBooks()){
//            for(User user: Main.userDatabase.getUserDatabase()){
//                if(user instanceof Reader){
//                    for(BookReservation bookReservation: ((Reader) user).getReservations()){
//                        if (!bookReservation.isReturned()) {
//                            break;
//                        }
//                    }
//                }
//            }
//            if (isFree){
//                rentedBooks.add(new TableBook(book.getId(), book.getTitle(), book.getAuthor(), book.getNote(), book.getImage()));
//            }
//        }
//        tableView.setItems(rentedBooks);
//        tableView.refresh();
    }

    public void showDates(){
//        datePicker.setDisable(false);
//        Book book = tableView.getSelectionModel().getSelectedItem();
//        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
//            public DateCell call(final DatePicker datePicker) {
//                return new DateCell() {
//                    @Override
//                    public void updateItem(LocalDate dateCurr, boolean empty) {
//                        super.updateItem(dateCurr, empty);
//                        LocalDate now = Main.booksDatabase.getDate();
//                        if(dateCurr.compareTo(now) <= 0){
//                            setDisable(true);
//                            return;
//                        }
//
//                        for(User user: Main.userDatabase.getUserDatabase()){
//                            if (!(user instanceof Reader)){
//                                continue;
//                            }
//                            Reader reader = (Reader) user;
//                            for(BookReservation bookReservation: reader.getReservations()){
//                                if(bookReservation.getBookId() == book.getId()){
//                                    LocalDate dateFrom = bookReservation.getDateFrom();
//                                    LocalDate dateTo = bookReservation.getDateTo();
//                                    if ((dateCurr.isAfter(dateFrom) && dateCurr.isBefore(dateTo)) || dateCurr.equals(dateFrom) || dateCurr.equals(dateTo)) {
//                                        setStyle("-fx-background-color: #ff4444;");
//                                        setDisable(true);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                };
//            }
//        };
//
//        datePicker.setDayCellFactory(dayCellFactory);
//        datePicker.setDisable(false);
    }

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
