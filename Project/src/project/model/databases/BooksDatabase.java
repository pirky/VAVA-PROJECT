package project.model.databases;

import javafx.scene.image.Image;
import project.model.CustomImage;
import project.model.books.Book;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BooksDatabase {
    private List<Book> books;
    private LocalDate date;

    public BooksDatabase() throws IOException, ClassNotFoundException {
//        loadDemo();
        deserialize();
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
        books.add((Book) book.clone());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getBookId(){
        return getBooks().size();
    }

    private void loadDemo(){
        this.date = LocalDate.now();
        books = new ArrayList<>();
        Book book = new Book(0, "Pekaren", "Julie Caplinova", "Popis knihy pekaren", new CustomImage(new Image("project/images/books/book0.jpg")));
        book.setCreatedAt(LocalDate.parse("2021-01-03"));
        books.add(book);

        book = new Book(1, "Kaviaren", "Julie Caplinova", "Popis knihy kaviaren", new CustomImage(new Image("project/images/books/book1.jpg")));
        book.setCreatedAt(LocalDate.parse("2021-03-03"));
        books.add(book);

        book = new Book(2, "V tieni", "Dominic Dan", "Popis knihy v tieni", new CustomImage(new Image("project/images/books/book2.jpg")));
        book.setCreatedAt(LocalDate.parse("2021-02-21"));
        books.add(book);

        book = new Book(3, "Zaklinac", "Andrej Sapkowski", "Popis knihy zaklinac", new CustomImage(new Image("project/images/books/book3.jpg")));
        book.setCreatedAt(LocalDate.parse("2020-02-21"));
        books.add(book);

        book = new Book(4, "Obklopený idiotmi", "Thomas Erikson", "Popis knihy v Obklopený idiotmi", new CustomImage(new Image("project/images/books/book4.jpg")));
        book.setCreatedAt(LocalDate.parse("2021-04-14"));
        books.add(book);

        book = new Book(5, "Môj macík", "Mária Rázusová-Martáková", "Popis knihy Môj macík", new CustomImage(new Image("project/images/books/book5.jpg")));
        book.setCreatedAt(LocalDate.parse("2021-04-11"));
        books.add(book);

        book = new Book(6, "Sochár smrti", "Chris Carter", "Popis knihy Sochár smrti", new CustomImage(new Image("project/images/books/book6.jpg")));
        book.setCreatedAt(LocalDate.parse("2018-10-14"));
        books.add(book);

        book = new Book(7, "Zdravé črevo a trávenie", "Ladislav Kužela, Zuzana Čižmáriková", "Popis knihy Zdravé črevo a trávenie", new CustomImage(new Image("project/images/books/book7.jpg")));
        book.setCreatedAt(LocalDate.parse("2007-11-11"));
        books.add(book);
    }

    public void serialize() throws IOException {
        File database = new File("src/project/model/databases/Books.txt");
        FileOutputStream fos = new FileOutputStream(database);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(date);
        oos.writeObject(books);
        oos.close();
        fos.close();
    }

    public void deserialize() throws IOException, ClassNotFoundException {
        File database = new File("src/project/model/databases/Books.txt");
        FileInputStream fis = new FileInputStream(database);
        ObjectInputStream ois = new ObjectInputStream(fis);
        date = (LocalDate) ois.readObject();
        books = (List<Book>) ois.readObject();
        ois.close();
        fis.close();
    }
}
