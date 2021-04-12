package project.model.databases;
import project.model.users.Librarian;
import project.model.users.Organizer;
import project.model.users.Reader;
import project.model.users.User;
import java.util.ArrayList;

public class UserDatabase {

    public static ArrayList<User> UserDatabase = new ArrayList<>();


    public static User login(String name, String password){
        for( User i: UserDatabase ){
            if((i.getUserName().equals(name)) && (i.getPassword().equals(password))){
                return i;
            }
        }
        return null;
    }

    public static void registration(String username, String password, int type)
    {

        switch(type)
        {
            case 1:
                UserDatabase.add(new Librarian(username,password));
                break;
            case 2:
                UserDatabase.add(new Organizer(username,password));
                break;
            case 3:
                UserDatabase.add(new Reader(username,password));
                break;
        }

    }

    public static boolean checkIfExists(String username){
        for( User i: UserDatabase ){
            if(i.getUserName().equals(username)){
                return true;
            }
        }
        return false;
    }


}
