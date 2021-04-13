package project.model.users;

import project.model.books.Book;
import java.util.ArrayList;
import java.util.List;

public class Reader extends User{
    private List<Book> rentedBooks;
    private List<Book> reservedBooks;

    public Reader(String userName, String password) {
        super(userName, password);
    }

    public List<Book> getRentedBooks() {
        List<Book> returnList = new ArrayList<>();
        for(Book book: rentedBooks){
            returnList.add((Book) book.clone());
        }
        return returnList;
    }

    public void setRentedBooks(List<Book> rentedBooks) {
        List<Book> list = new ArrayList<>();
        for(Book book: rentedBooks){
            list.add((Book) book.clone());
        }
        this.rentedBooks = list;
    }

    public void addRentedBook(Book book){
        rentedBooks.add((Book) book.clone());
    }

    public List<Book> getReservedBooks() {
        List<Book> returnList = new ArrayList<>();
        for(Book book: reservedBooks){
            returnList.add((Book) book.clone());
        }
        return returnList;
    }

    public void setReservedBooks(List<Book> reservedBooks) {
        List<Book> list = new ArrayList<>();
        for(Book book: reservedBooks){
            list.add((Book) book.clone());
        }
        this.reservedBooks = list;
    }

    public void addReservedBook(Book book){
        reservedBooks.add((Book) book.clone());
    }

    public Object clone(){
        Reader reader = new Reader(this.getUserName(), this.getPassword());
        reader.setRentedBooks(this.rentedBooks);
        reader.setReservedBooks(this.reservedBooks);
        return reader;
    }
}
