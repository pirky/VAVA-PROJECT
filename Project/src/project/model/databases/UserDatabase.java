package project.model.databases;

import project.controller.Main;
import project.model.Rooms.RoomReservation;
import project.model.books.BookReservation;
import project.model.events.BookDiscussion;
import project.model.events.BookExchange;
import project.model.events.Event;
import project.model.users.Librarian;
import project.model.users.Organizer;
import project.model.users.Reader;
import project.model.users.User;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private List<User> users;

    public UserDatabase() throws IOException, ClassNotFoundException {
//        loadDemo();
        deserialize();
    }

    public static User login(String name, String password){
        for( User i: Main.userDatabase.getUsers()){
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
        for(User i: Main.userDatabase.getUsers()){
            if(i.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }

    public List<User> getUsers() {
        List<User> returnList = new ArrayList<>();
        for(User user: users){
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

    public void setUsers(List<User> users) {
        List<User> list = new ArrayList<>();
        for(User user: users){
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
        this.users = list;
    }

    public void removeUser(User user){
        users.removeIf(temp -> user.getUserName().equals(temp.getUserName()));
    }

    public void addUser(Librarian librarian){
        this.users.add(librarian);
    }

    public void addUser(Organizer organizer){
        this.users.add(organizer);
    }

    public void addUser(Reader reader){
        this.users.add(reader);
    }

    private void loadDemo(){
        users = new ArrayList<>();
        List<BookReservation> reservations = new ArrayList<>();
        reservations.add(new BookReservation(0, LocalDate.parse("2021-04-13"), LocalDate.parse("2021-04-21")));
        reservations.add(new BookReservation(1, LocalDate.parse("2021-05-03"), LocalDate.parse("2021-05-11")));
        reservations.add(new BookReservation(2, LocalDate.parse("2021-04-14"), LocalDate.parse("2021-04-20")));
        reservations.add(new BookReservation(3, LocalDate.parse("2021-04-15"), LocalDate.parse("2021-04-30")));

        users.add(new Librarian("librarian", "heslo"));

        Organizer organizer = new Organizer("organizer", "heslo");
        List<Event> events = new ArrayList<>();
        events.add(new BookDiscussion("Diskusia o knihe Sapiens", "velmi zaujmava diskusia", new RoomReservation(LocalDate.parse("2021-04-20"), LocalDate.parse("2021-04-21"), 0), organizer, "Yuval Harari"));
        events.add(new BookDiscussion("Diskusia o knihe Homo Deus", "velmi pestra diskusia", new RoomReservation(LocalDate.parse("2021-04-28"), LocalDate.parse("2021-04-30"), 0), organizer, "Yuval Harari"));
        events.add(new BookDiscussion("Diskusia o knihe Life 3.0", "velmi intelektualna diskusia", new RoomReservation(LocalDate.parse("2021-04-17"), LocalDate.parse("2021-04-18"), 1), organizer, "Max Tegmark"));
        events.add(new BookExchange("Aprilova vymena knih", "popis Aprilova vymena knih", new RoomReservation(LocalDate.parse("2021-04-20"), LocalDate.parse("2021-04-25"), 1), organizer));
        events.add(new BookExchange("Majova vymena knih", "popis Majova vymena knih", new RoomReservation(LocalDate.parse("2021-05-20"), LocalDate.parse("2021-05-25"), 1), organizer));

        organizer.setEvents(events);
        users.add(organizer);

        Reader reader = new Reader("reader", "heslo");
        reader.setReservations(reservations);
        users.add(reader);
    }

    public void serialize() throws IOException {
        File database = new File("src/project/model/databases/Users.txt");
        FileOutputStream fos = new FileOutputStream(database);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(users);
        oos.close();
        fos.close();
    }

    public void deserialize() throws IOException, ClassNotFoundException {
        File database = new File("src/project/model/databases/Users.txt");
        FileInputStream fis = new FileInputStream(database);
        ObjectInputStream ois = new ObjectInputStream(fis);
        users = (List<User>) ois.readObject();
        ois.close();
        fis.close();
    }
}
