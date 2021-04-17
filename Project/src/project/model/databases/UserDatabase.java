package project.model.databases;
import project.controller.Main;
import project.model.Rooms.LibraryRoom;
import project.model.Rooms.RoomReservation;
import project.model.books.BookReservation;
import project.model.events.BookDiscussion;
import project.model.events.BookExchange;
import project.model.events.Event;
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

        List<Event> events = new ArrayList<>();
        events.add(new BookDiscussion("Diskusia o knihe Sapiens", "velmi zaujmava diskusia", new RoomReservation(LocalDate.parse("2021-01-01"), LocalDate.parse("2021-01-10"), new LibraryRoom("Miestnost 5", 5)), new Organizer("jano", "balazia"), "Yuval Harari"));
        events.add(new BookDiscussion("Diskusia o knihe Homo Deus", "velmi pestra diskusia", new RoomReservation(LocalDate.parse("2021-02-01"), LocalDate.parse("2021-02-10"), new LibraryRoom("Miestnost 6", 10)), new Organizer("rasto", "bencel"), "Yuval Harari"));
        events.add(new BookDiscussion("Diskusia o knihe Life 3.0", "velmi intelektualna diskusia", new RoomReservation(LocalDate.parse("2021-03-01"), LocalDate.parse("2021-03-10"), new LibraryRoom("Miestnost 7", 15)), new Organizer("peto", "kockin"), "Max Tegmark"));
        events.add(new BookExchange("Januarova vymena knih", "Januarova vymena knih", new RoomReservation(LocalDate.parse("2021-01-15"), LocalDate.parse("2021-01-20"), new LibraryRoom("Miestnost 8", 20)), new Organizer("fero", "vangel")));
        events.add(new BookExchange("Februarova vymena knih", "Februarova vymena knih", new RoomReservation(LocalDate.parse("2021-02-15"), LocalDate.parse("2021-02-20"), new LibraryRoom("Miestnost 9", 25)), new Organizer("pele", "smevko")));
        Organizer organizer = new Organizer("organizer", "heslo");
        organizer.setEvents(events);
        userDatabase.add(organizer);


        Reader reader = new Reader("reader", "heslo");
        reader.setReservations(reservations);
        userDatabase.add(reader);



    }
}
