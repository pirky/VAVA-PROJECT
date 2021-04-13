package project.model.databases;
import project.controller.Main;
import project.model.users.Librarian;
import project.model.users.Organizer;
import project.model.users.Reader;
import project.model.users.User;
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
        return userDatabase;
    }

    public void setUserDatabase(List<User> userDatabase) {
        this.userDatabase = userDatabase;
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
        userDatabase.add(new Librarian("Martin", "Pirkovsky"));
        userDatabase.add(new Organizer("Pazo", "Pazicky"));
        userDatabase.add(new Reader("Peter", "Plevko"));
    }
}
