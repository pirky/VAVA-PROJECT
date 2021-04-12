package project.model.books;

import project.model.CustomImage;
import java.io.Serializable;


public class Book implements Serializable {
    private String name;
    private String author;
    private String note;
    private CustomImage image;

    public Book(String name, String author, String note, CustomImage image) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Object clone(){
        return new Book(this.name, this.author, this.note, this.image);
    }
}
