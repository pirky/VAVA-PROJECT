package project.model.books;

import project.model.CustomImage;

import java.io.Serializable;

public class SellableBook extends Book implements Serializable {
    private double price;
    private final String userName;

    public SellableBook(int id, String name, String author, String note, CustomImage image, double price, String userName) {
        super(id, name, author, note, image);
        this.price = price;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Object clone(){
        return new SellableBook(this.getId(), this.getTitle(), this.getAuthor(), this.getNote(), this.getImage(), this.price, this.userName);
    }


}
