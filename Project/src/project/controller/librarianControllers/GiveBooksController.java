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

public class GiveBooksController {
    ObservableList<Reader> readers = FXCollections.observableArrayList();
    ObservableList<BookReservation> bookReservations = FXCollections.observableArrayList();
    ObservableList<TableBook> freeBooks = FXCollections.observableArrayList();
    private Reader reader;
    @FXML
    private ComboBox<Reader> readersBox;
    @FXML
    private ListView<BookReservation> listView;
    @FXML
    private Label infoLabel;
    @FXML
    private Button giveBtn;
    @FXML
    private Button addBtn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<TableBook> tableView;
    @FXML
    private TableColumn<TableBook, String> authorColumn;
    @FXML
    private TableColumn<TableBook, String> titleColumn;
    @FXML
    private TableColumn<TableBook, ImageView> imageColumn;

    @FXML
    public void initialize(){
        for(User user: Main.userDatabase.getUserDatabase()){
            if(user instanceof Reader){
                readers.add((Reader) user);
            }
        }
        readersBox.setItems(readers);
        infoLabel.setVisible(false);
        giveBtn.setDisable(true);
        addBtn.setDisable(true);
        datePicker.setDisable(true);

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

    public void showReservations(){
        reader = readersBox.getValue();
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
        giveBtn.setDisable(false);
        updateTableView();
    }

    private void updateTableView(){
        tableView.getItems().clear();
        for(Book book: Main.booksDatabase.getBooks()){
            boolean isFree = true;
            for(User user: Main.userDatabase.getUserDatabase()){
                if(user instanceof Reader){
                    for(BookReservation bookReservation: ((Reader) user).getReservations()){
                        if (book.getId() == bookReservation.getBookId() &&
                                ((Main.booksDatabase.getDate().compareTo(bookReservation.getDateFrom()) >= 0 &&
                                        Main.booksDatabase.getDate().compareTo(bookReservation.getDateTo()) <= 0) ||
                                        (bookReservation.isReturned() != null && !bookReservation.isReturned()))) {
                            isFree = false;
                            break;
                        }
                    }
                }
            }
            if (isFree){
                freeBooks.add(new TableBook(book.getId(), book.getTitle(), book.getAuthor(), book.getNote(), book.getImage()));
            }
        }
        tableView.setItems(freeBooks);
        tableView.refresh();
    }

    public void giveBooks(){
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
        Main.userDatabase.removeUser(reader);
        Main.userDatabase.addUser(reader);
        deleteFields();
    }

    public void showDates(){
        datePicker.setDisable(false);
        Book book = tableView.getSelectionModel().getSelectedItem();
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate dateCurr, boolean empty) {
                        super.updateItem(dateCurr, empty);
                        LocalDate now = Main.booksDatabase.getDate();
                        if(dateCurr.compareTo(now) <= 0){
                            setDisable(true);
                            return;
                        }

                        for(User user: Main.userDatabase.getUserDatabase()){
                            if (!(user instanceof Reader)){
                                continue;
                            }
                            Reader reader = (Reader) user;
                            for(BookReservation bookReservation: reader.getReservations()){
                                if(bookReservation.getBookId() == book.getId()){
                                    LocalDate dateFrom = bookReservation.getDateFrom();
                                    LocalDate dateTo = bookReservation.getDateTo();
                                    if ((dateCurr.isAfter(dateFrom) && dateCurr.isBefore(dateTo)) || dateCurr.equals(dateFrom) || dateCurr.equals(dateTo)) {
                                        setStyle("-fx-background-color: #ff4444;");
                                        setDisable(true);
                                    }
                                }
                            }
                        }
                    }
                };
            }
        };

        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.setDisable(false);
    }

    public void enableAddBtn(){
        addBtn.setDisable(false);
    }

    public void addBook(){
        LocalDate dateTo = datePicker.getValue();
        Book book = tableView.getSelectionModel().getSelectedItem();
        bookReservations.add(new BookReservation(book.getId(), Main.booksDatabase.getDate(), dateTo));
        datePicker.setDisable(true);
        datePicker.setValue(null);
        freeBooks.removeIf(temp -> temp.getId() == book.getId());
        tableView.refresh();
        listView.refresh();
    }

    private void deleteFields(){
        listView.getItems().clear();
        listView.refresh();
        readersBox.getSelectionModel().clearSelection();
        giveBtn.setDisable(true);
        addBtn.setDisable(true);
        datePicker.setDisable(true);
        datePicker.setValue(null);
        tableView.getItems().clear();
        tableView.refresh();
    }

    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
