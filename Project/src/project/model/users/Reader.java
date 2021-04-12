package project.model.users;

public class Reader extends User{

    public Reader(String userName, String password) {
        super(userName, password);
    }

    public Object clone(){
        return new Reader(this.getUserName(), this.getPassword());
    }
}
