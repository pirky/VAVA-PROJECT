package project.controller.readerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import project.controller.Main;
import project.model.books.Book;
import project.model.books.BookReservation;
import project.model.users.Reader;
import project.model.users.User;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;

public class BookInfoController{
    private Book book;
    @FXML
    private DatePicker datePickerFrom;
    @FXML
    private DatePicker datePickerTo;
    @FXML
    private Label authorLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private TextArea noteArea;
    @FXML
    private ImageView imageView;
    @FXML
    private Button btn;

    public void updateDateFrom(LocalDate dateSelected){

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            LocalDate dateBlock = null;
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate dateCurr, boolean empty) {
                        super.updateItem(dateCurr, empty);
                        LocalDate now = Main.booksDatabase.getDate();
                        if(dateCurr.compareTo(now) < 0){
                            setDisable(dateCurr.compareTo(now) < 0);
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
                                        dateBlock = dateCurr;
                                    }
                                    if (dateSelected != null && (dateCurr.isBefore(dateSelected) || dateCurr.equals(dateSelected))){
                                        setDisable(true);
                                    }
                                    if (dateSelected != null && dateBlock != null && dateSelected.isBefore(dateBlock) && dateCurr.isAfter(dateBlock)){
                                        setDisable(true);
                                    }
                                }
                                else{
                                    if (dateSelected!= null && (dateCurr.isBefore(dateSelected) || dateCurr.equals(dateSelected))){
                                        setDisable(true);
                                    }
                                }
                            }
                        }
                    }
                };
            }
        };
        if (dateSelected == null){
            datePickerFrom.setDayCellFactory(dayCellFactory);
            datePickerTo.setDisable(true);
        }
        else {
            datePickerTo.setDayCellFactory(dayCellFactory);
            datePickerTo.setDisable(false);
        }
    }

    public void updateDateTo(){
        LocalDate date = datePickerFrom.getValue();
        updateDateFrom(date);
    }

    public void enableBtn(){
        btn.setDisable(false);
    }

    public void reserveBook() throws IOException {
        LocalDate dateFrom = datePickerFrom.getValue();
        LocalDate dateTo = datePickerTo.getValue();
        Main.userDatabase.removeUser(Main.currUser);
        Reader reader = (Reader) Main.currUser;
        reader.addReservation(new BookReservation(book.getId(), dateFrom, dateTo));
        Main.userDatabase.addUser(reader);
        Main.currUser = reader;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("project/images/logo.jpg"));
        alert.setTitle("Úspešná Rezervácia");
        alert.setHeaderText("Kniha bola úspešne rezervovaná");
        ImageView imageView = new ImageView(book.getImage().getImage());
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(200);
        alert.setGraphic(imageView);
        alert.setContentText(null);
        alert.showAndWait();

        showBooks();
    }

    public void setBook(Book book) {
        this.book = (Book) book.clone();
        imageView.setImage(book.getImage().getImage());
        authorLabel.setText(book.getAuthor());
        titleLabel.setText(book.getTitle());
        noteArea.setText(book.getNote());
        datePickerTo.setDisable(true);
        btn.setDisable(true);
        updateDateFrom(null);
    }

    public void showBooks() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/project/view/readerViews/BookReservationView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }
}
