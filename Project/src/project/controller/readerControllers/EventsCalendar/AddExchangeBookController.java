package project.controller.readerControllers.EventsCalendar;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import project.controller.Main;
import project.model.CustomImage;
import project.model.books.Book;
import project.model.books.BookReservation;
import project.model.books.SellableBook;
import project.model.books.TableBook;
import project.model.events.BookExchange;
import project.model.events.Event;
import project.model.users.Reader;
import project.model.users.User;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddExchangeBookController implements Initializable {
    private YearMonth yearMonth;
    private Event event;
    @FXML
    TextField authorName;
    @FXML TextField bookName;
    @FXML TextField bookNote;
    @FXML
    Button send;
    @FXML
    ImageView bookImageView;
    @FXML TextField price;
    private Image bookImage;
    @FXML
    private TableView<TableBook> tableView;
    @FXML
    private TableColumn<TableBook, String> authorColumn;
    @FXML
    private TableColumn<TableBook, String> titleColumn;
    @FXML
    private TableColumn<TableBook, ImageView> imageColumn;
    ObservableList<TableBook> tableBooks = FXCollections.observableArrayList();
    @FXML
    private TableColumn<TableBook, Double> priceCol;


    public void setEvent(Event event) {

        this.event = event;
        tableView.setRowFactory(tv -> new TableRow<TableBook>() {
            @Override
            protected void updateItem(TableBook item, boolean empty) {
                super.updateItem(item, empty);
                if(item == null){
                    setStyle("");
                    return;
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
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageView"));


        for(SellableBook sellableBook: ((BookExchange) event).getBooks())
        {

            tableView.getItems().clear();

            TableBook tableBook = new TableBook(sellableBook.getId(), sellableBook.getTitle(), sellableBook.getAuthor(), sellableBook.getNote(), sellableBook.getImage(), null, null ,false);
            tableBook.setPrice(sellableBook.getPrice());
            tableBooks.add(tableBook);
        }
        tableView.setItems(tableBooks);
        tableView.refresh();
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public void showEvent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/project/view/readerViews/EventsCalendar/EventEnrollView.fxml"));
        Parent root = loader.load();
        Main.mainStage.setResizable(false);
        EventEnrollController eventEnrollController = loader.getController();
        eventEnrollController.setYearMonth(yearMonth);
        eventEnrollController.setEvent(event);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    public void addImage(){
        bookImage = null;
        try {
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(imageFilter);
            fileChooser.setTitle("Vyber fotku knihy");
            Image image = new Image(fileChooser.showOpenDialog(Main.mainStage).toURI().toString());
            bookImage = image;
            bookImageView.setImage(image);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Vyber obrazok");
        }

    }

    public void sendIntoDatabase(){
        boolean flag = true;

        if(bookName.getText().equals("") || authorName.getText().equals("") || bookNote.getText().equals("") || bookImage == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Nastala chyba");
            alert.setContentText("nezadal si vsetky potrebne udaje");
            alert.showAndWait();
            flag = false;
        }

        try {
            Integer.parseInt(price.getText());
        }
        catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Nastala chyba");
            alert.setContentText("nezadal si cislo");
            alert.showAndWait();
            flag = false;
        }


        if (flag) {

            SellableBook sellableBook = new SellableBook(Main.booksDatabase.getBookId(), bookName.getText(), authorName.getText(), bookNote.getText(), new CustomImage(bookImage), Integer.parseInt(price.getText()), Main.currUser.getUserName());

            boolean flag2 = true;

            if (event instanceof BookExchange){
                for(Book i : ((BookExchange) event).getBooks()) {
                    if (i.getTitle().equals(bookName.getText())){
                        flag2 = false;
                    }
                }
            }

            if (!flag2) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nastala chyba");
                alert.setContentText("Takato kniha sa uz v databaze nachadza");
                alert.showAndWait();
            }

            if (flag2) {
                sellableBook.setCreatedAt(LocalDate.now());

                if (event instanceof BookExchange){

                    ((BookExchange) event).addBook(sellableBook);
                }
                JOptionPane.showMessageDialog(null, "Kniha bola uspesne pridana");
                bookImageView.setImage(bookImage);
            }
        }

        if (event instanceof BookExchange){

            for(SellableBook sellableBook: ((BookExchange) event).getBooks())
            {

                tableView.getItems().clear();

                TableBook tableBook = new TableBook(sellableBook.getId(), sellableBook.getTitle(), sellableBook.getAuthor(), sellableBook.getNote(), sellableBook.getImage(), null, null ,false);
                tableBook.setPrice(sellableBook.getPrice());
                tableBooks.add(tableBook);
            }
            tableView.setItems(tableBooks);
            tableView.refresh();
        }



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }




    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }


}
