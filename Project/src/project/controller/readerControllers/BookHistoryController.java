package project.controller.readerControllers;

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
import javafx.scene.text.Text;
import project.controller.Main;
import project.model.books.Book;
import project.model.books.BookReservation;
import project.model.books.TableBook;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class BookHistoryController {

        ObservableList<Reader> readers = FXCollections.observableArrayList();
        ObservableList<TableBook> rentedBooks = FXCollections.observableArrayList();
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
        private TableColumn<TableBook, LocalDate> dateFrom;
        @FXML
        private TableColumn<TableBook, LocalDate> dateTo;
        @FXML TextField filterField;

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
            dateFrom.setCellValueFactory(new PropertyValueFactory<>("dateFrom"));
            dateTo.setCellValueFactory(new PropertyValueFactory<>("dateTo"));
            imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageView"));

        }


        public void updateTableView(){
            tableView.getItems().clear();
            Reader reader = readersBox.getValue();
            for(BookReservation bookReservation: reader.getReservations()){
                if(bookReservation.isReturned() == null){
                    continue;
                }
                else{
                    Book temp = Main.booksDatabase.getBooks().get(bookReservation.getBookId());
                    rentedBooks.add(new TableBook(temp.getId(), temp.getTitle(), temp.getAuthor(), temp.getNote(), temp.getImage(), bookReservation.getDateFrom(), bookReservation.getDateTo(), bookReservation.isReturned()));
                }

                tableView.setRowFactory(tv -> new TableRow<TableBook>(){
                    @Override
                    protected void updateItem(TableBook item, boolean empty) {
                        super.updateItem(item,empty);
                        if (empty || item == null) {
                            setStyle("");
                        } else if (item.getReturned()){
                            setStyle("-fx-background-color: #98f8b1");
                        } else {
                            setStyle("-fx-background-color: #ff9494");
                        }
                    }
                });




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
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/readerViews/ReaderView.fxml")));
            Scene scene = new Scene(root);
            Main.mainStage.setScene(scene);
            Main.mainStage.show();
        }
    }