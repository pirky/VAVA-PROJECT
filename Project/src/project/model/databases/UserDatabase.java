package project.model.databases;
import javafx.scene.image.Image;
import project.controller.Main;
import project.model.CustomImage;
import project.model.books.Book;
import project.model.books.BookReservation;
import project.model.books.SellableBook;
import project.model.users.Librarian;
import project.model.users.Organizer;
import project.model.users.Reader;
import project.model.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class UserDatabase {
    private List<User> userDatabase;

    public UserDatabase() {
        loadDemo();
    }

    public static User login(String name, String password){
        for( User i: Main.userDatabase.getUserDatabase()){
            if((i.getUserName().equals(name)) && (i.getPassword().equals(password))){
                Main.currUser = i;
                return i;
            }
        }
        return null;
    }

    public static void registration(String username, String password, String type) {
        switch(type) {
            case "knihovník":
                Librarian librarian = new Librarian(username,password);
                Main.userDatabase.addUser(librarian);
                break;
            case "organizátor":
                Organizer organizer = new Organizer(username,password);
                Main.userDatabase.addUser(organizer);
                break;
            case "čitateľ":
                Reader reader = new Reader(username,password);
                Main.userDatabase.addUser(reader);
                break;
        }
    }

    public static boolean checkIfExists(String username){
        for(User i: Main.userDatabase.getUserDatabase()){
            if(i.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

    public List<User> getUserDatabase() {
        List<User> returnList = new ArrayList<>();
        for(User user: userDatabase){
            if(user instanceof Reader){
                returnList.add((Reader) user.clone());
            }
            else if(user instanceof Librarian){
                returnList.add((Librarian) user.clone());
            }
            else if(user instanceof Organizer){
                returnList.add((Organizer) user.clone());
            }
        }
        return returnList;
    }

    public void setUserDatabase(List<User> userDatabase) {
        List<User> list = new ArrayList<>();
        for(User user: userDatabase){
            if(user instanceof Reader){
                list.add((Reader) user.clone());
            }
            else if(user instanceof Librarian){
                list.add((Librarian) user.clone());
            }
            else if(user instanceof Organizer){
                list.add((Organizer) user.clone());
            }
        }
        this.userDatabase = list;
    }

    public void removeUser(User user){
        userDatabase.removeIf(temp -> user.getUserName().equals(temp.getUserName()));
    }

    public void addUser(Librarian librarian){
        this.userDatabase.add(librarian);
    }

    public void addUser(Organizer organizer){
        this.userDatabase.add(organizer);
    }

    public void addUser(Reader reader){
        this.userDatabase.add(reader);
    }

    private void loadDemo(){
        userDatabase = new ArrayList<>();
        List<BookReservation> reservations = new ArrayList<>();
        reservations.add(new BookReservation(0, LocalDate.parse("2021-04-13"), LocalDate.parse("2021-04-21")));
        reservations.add(new BookReservation(1, LocalDate.parse("2021-05-03"), LocalDate.parse("2021-05-11")));
        reservations.add(new BookReservation(2, LocalDate.parse("2021-04-14"), LocalDate.parse("2021-04-20")));
        reservations.add(new BookReservation(3, LocalDate.parse("2021-04-15"), LocalDate.parse("2021-04-30")));

        userDatabase.add(new Librarian("librarian", "heslo"));
        userDatabase.add(new Organizer("organizer", "heslo"));
        Reader reader = new Reader("reader", "heslo");
        reader.setReservations(reservations);
        userDatabase.add(reader);

    }
}
