package project.model.books;

import project.model.CustomImage;

import java.io.Serializable;

public class SellableBook extends Book implements Serializable {
    private double price;
    private boolean sold;

    public SellableBook(String name, String author, String note, CustomImage image, double price) {
        super(name, author, note, image);
        this.price = price;
        this.sold = false;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Object clone(){
        SellableBook sellableBook = new SellableBook(this.getName(), this.getAuthor(), this.getNote(), this.getImage(), this.price);
        sellableBook.setSold(this.sold);
        return sellableBook;
    }
}
