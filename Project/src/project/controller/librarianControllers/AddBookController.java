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
import java.util.Objects;


public class AddBookController {
    @FXML TextField authorName;
    @FXML TextField bookName;
    @FXML TextField bookNote;
    @FXML Button send;
    @FXML ImageView bookImageView;
    private Image bookImage;

    public void addImage(){
        bookImage = null;

        try {
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(imageFilter);
            fileChooser.setTitle("Vyber fotku knihy");
            Image image = new Image(fileChooser.showOpenDialog(Main.mainStage).toURI().toString());
            bookImage = image;
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
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Nastala chyba");
                alert.setContentText("Takato kniha sa uz v databaze nachadza");
                alert.showAndWait();
            }

            if (flag2) {
                book.setCreatedAt(LocalDate.now());
                Main.booksDatabase.addBook(book);
                JOptionPane.showMessageDialog(null, "Kniha bola uspesne pridana");
                bookImageView.setImage(bookImage);
            }

        }


    }


    public void showMenu() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/project/view/librarianViews/LibrarianView.fxml")));
        Scene scene = new Scene(root);
        Main.mainStage.setScene(scene);
        Main.mainStage.show();
    }

}
