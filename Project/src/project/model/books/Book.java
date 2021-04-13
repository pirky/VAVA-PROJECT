package project.model.books;

import project.controller.Main;
import project.model.CustomImage;
import java.io.Serializable;
import java.time.LocalDate;


public class Book implements Serializable {
    private String title;
    private String author;
    private String note;
    private CustomImage image;
    private LocalDate createdAt;

    public Book(String name, String author, String note, CustomImage image) {
        this.title = name;
        this.author = author;
        this.note = note;
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public CustomImage getImage() {
        return image;
    }

    public void setImage(CustomImage image) {
        this.image = image;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Object clone(){
        Book book = new Book(this.title, this.author, this.note, this.image);
        book.setCreatedAt(this.createdAt);
        return book;
    }
}
