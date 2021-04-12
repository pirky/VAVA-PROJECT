package project.model.users;

public class Organizer extends User{


    public Organizer(String userName, String password) {
        super(userName, password);
    }

    public Object clone(){
        return new Organizer(this.getUserName(), this.getPassword());
    }
}
