package project.model.Rooms;

public class LibraryRoom {
    private final String name;
    private final int capacity;

    public LibraryRoom(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public Object clone(){
        return new LibraryRoom(this.name, this.capacity);
    }
}
