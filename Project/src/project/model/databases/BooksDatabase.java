package project.model.databases;

import javafx.scene.image.Image;
import project.model.CustomImage;
import project.model.books.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BooksDatabase {
    private List<Book> books;
    private LocalDate date;

    public BooksDatabase() {
        this.date = LocalDate.now();
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private void loadDemo(){
        books = new ArrayList<>();
        Book book = new Book("Pekaren", "Julie Caplinova", "Popis knihy pekaren", new CustomImage(new Image("project/images/book1.jpg")));
        book.setCreatedAt(date);
        books.add(book);

        book = new Book("Kaviaren", "Julie Caplinova", "Popis knihy kaviaren", new CustomImage(new Image("project/images/book2.jpg")));
        book.setCreatedAt(date);
        books.add(book);

        book = new Book("V tieni", "Dominic Dan", "Popis knihy v tieni", new CustomImage(new Image("project/images/book3.jpg")));
        book.setCreatedAt(date);
        books.add(book);

        book = new Book("Zaklinac", "Andrej Sapkowski", "Popis knihy zaklinac", new CustomImage(new Image("project/images/book4.jpg")));
        book.setCreatedAt(date);
        books.add(book);

        book = new Book("V tieni", "Dominic Dan", "Popis knihy v tieni", new CustomImage(new Image("project/images/book3.jpg")));
        book.setCreatedAt(date);
        books.add(book);

        book = new Book("Pekaren", "Julie Caplinova", "Popis knihy pekaren", new CustomImage(new Image("project/images/book1.jpg")));
        book.setCreatedAt(date);
        books.add(book);

        book = new Book("Zaklinac", "Andrej Sapkowski", "Popis knihy zaklinac", new CustomImage(new Image("project/images/book4.jpg")));
        book.setCreatedAt(date);
        books.add(book);

        book = new Book("Kaviaren", "Julie Caplinova", "Popis knihy kaviaren", new CustomImage(new Image("project/images/book2.jpg")));
        book.setCreatedAt(date);
        books.add(book);
    }

}
