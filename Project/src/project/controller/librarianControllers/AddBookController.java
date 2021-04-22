package project.controller.librarianControllers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import project.controller.Main;
import project.model.CustomImage;
import project.model.books.Book;
import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddBookController {
    @FXML TextField authorName;
    @FXML TextField bookName;
    @FXML TextField bookNote;
    @FXML Button send;
    @FXML ImageView bookImageView;
    @FXML Button addImageButton;
    private Image bookImage;
    private String error;
    private String errorMessage1;
    private String errorMessage2;
    private String titleLanguage;
    private String success;

    @FXML
    public void initialize(){
     languageSK();
    }

    public void addImage(){
        bookImage = null;

        try {
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(imageFilter);
            fileChooser.setTitle(titleLanguage);
            Image image = new Image(fileChooser.showOpenDialog(Main.mainStage).toURI().toString());
            bookImage = image;
            bookImageView.setImage(image);
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, titleLanguage);
            LOG.log(Level.SEVERE, "User did not choose a picture");
        }

    }

    public void sendIntoDatabase(){
        boolean flag = true;

        if(bookName.getText().equals("") || authorName.getText().equals("") || bookNote.getText().equals("") || bookImage == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(error);
            alert.setContentText(errorMessage1);
            alert.showAndWait();
            flag = false;
            LOG.log(Level.INFO, "User did not enter all required information");
        }

        if (flag) {
            Book book = new Book(Main.booksDatabase.getBookId(), bookName.getText(), authorName.getText(), bookNote.getText(), new CustomImage(bookImage));

            boolean flag2 = true;

            for(Book i : Main.booksDatabase.getBooks()) {
                if (i.getTitle().equals(bookName.getText())){
                    flag2 = false;
                }
            }

            if (!flag2) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(error);
                alert.setContentText(errorMessage2);
                alert.showAndWait();
                LOG.log(Level.INFO, "User tried to add the same book twice");
            }

            if (flag2) {
                book.setCreatedAt(LocalDate.now());
                Main.booksDatabase.addBook(book);
                JOptionPane.showMessageDialog(null, success);
                bookImageView.setImage(bookImage);
            }
        }
    }

    public void showMenu() throws IOException {
        Locale skLocale = new Locale("sk_SK");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.librarianView", skLocale);
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")), bundle);
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

    private static final Logger LOG = Logger.getLogger(AddBookController.class.getName());

    public void languageEN(){
        Locale enLocale = new Locale("en_US");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.librarianView", enLocale);
        changeSigns(bundle);
    }

    public void languageSK(){
        Locale skLocale = new Locale("sk_SK");
        ResourceBundle bundle = ResourceBundle.getBundle("project/resources.librarianView", skLocale);
        changeSigns(bundle);
    }

    public void changeSigns(ResourceBundle bundle){
        authorName.setPromptText(bundle.getString("authorNameAddBook"));
        bookName.setPromptText(bundle.getString("bookNameAddBook"));
        bookNote.setPromptText(bundle.getString("noteAddBook"));
        addImageButton.setText(bundle.getString("addImageAddBook"));
        send.setText(bundle.getString("addBookAddBook"));
        error = bundle.getString("error");
        errorMessage1 = bundle.getString("errorMessage1");
        errorMessage2 = bundle.getString("errorMessage2");
        titleLanguage = bundle.getString("titleLanguage");
        success = bundle.getString("deletePicture");
    }
}
