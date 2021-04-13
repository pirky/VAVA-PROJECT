package project.model.databases;

import javafx.scene.image.Image;
import project.model.CustomImage;
import project.model.books.Book;

import java.util.ArrayList;
import java.util.List;

public class BooksDatabase {
    private List<Book> books;

    public BooksDatabase() {
        loadDemo();
    }

    public List<Book> getBooks() {
        List<Book> returnList = new ArrayList<>();
        for(Book book: books){
            returnList.add((Book) book.clone());
        }
        return returnList;
    }

    public void setBooks(List<Book> books) {
        List<Book> list = new ArrayList<>();
        for(Book book: books){
            list.add((Book) book.clone());
        }
        this.books = list;
    }

    public void addBook(Book book){
        books.add(book);
    }

    private void loadDemo(){
        books = new ArrayList<>();
        books.add(new Book("Pekaren", "Julie Caplinova", "Popis knihy pekaren", new CustomImage(new Image("project/images/book1.jpg"))));
        books.add(new Book("Kaviaren", "Julie Caplinova", "Popis knihy kaviaren", new CustomImage(new Image("project/images/book2.jpg"))));
        books.add(new Book("V tieni", "Dominic Dan", "Popis knihy v tieni", new CustomImage(new Image("project/images/book3.jpg"))));
        books.add(new Book("Zaklinac", "Andrej Sapkowski", "Popis knihy zaklinac", new CustomImage(new Image("project/images/book4.jpg"))));

        books.add(new Book("V tieni", "Dominic Dan", "Popis knihy v tieni", new CustomImage(new Image("project/images/book3.jpg"))));
        books.add(new Book("Pekaren", "Julie Caplinova", "Popis knihy pekaren", new CustomImage(new Image("project/images/book1.jpg"))));
        books.add(new Book("Zaklinac", "Andrej Sapkowski", "Popis knihy zaklinac", new CustomImage(new Image("project/images/book4.jpg"))));
        books.add(new Book("Kaviaren", "Julie Caplinova", "Popis knihy kaviaren", new CustomImage(new Image("project/images/book2.jpg"))));

    }

}
