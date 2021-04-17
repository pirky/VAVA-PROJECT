package project.model.databases;

import javafx.scene.image.Image;
import project.model.CustomImage;
import project.model.Rooms.LibraryRoom;
import java.util.ArrayList;
import java.util.List;

public class RoomsDatabase {
    private List<LibraryRoom> rooms;

    public RoomsDatabase() {
        loadDemo();
    }

    public List<LibraryRoom> getRooms() {
        List<LibraryRoom> returnList = new ArrayList<>();
        for(LibraryRoom room: rooms){
            returnList.add((LibraryRoom) room.clone());
        }
        return returnList;
    }

    public void setRooms(List<LibraryRoom> rooms) {
        List<LibraryRoom> list = new ArrayList<>();
        for(LibraryRoom room: rooms){
            list.add((LibraryRoom) room.clone());
        }
        this.rooms = list;
    }

    public void addRoom(LibraryRoom room){
        rooms.add(room);
    }

    public void removeRoom(LibraryRoom room){
        rooms.removeIf(temp -> temp.getId() == room.getId());
    }

    private void loadDemo(){
        rooms = new ArrayList<>();
        LibraryRoom room = new LibraryRoom(0, "Miestnost 1", 15);
        room.addImage(new CustomImage(new Image("project/images/rooms/room1_1.jpg")));
        room.addImage(new CustomImage(new Image("project/images/rooms/room1_2.jpg")));
        rooms.add(room);

        room = new LibraryRoom(1, "Miestnost 2", 100);
        room.addImage(new CustomImage(new Image("project/images/rooms/room2_1.jpg")));
        room.addImage(new CustomImage(new Image("project/images/rooms/room2_2.jpg")));
        rooms.add(room);
    }
}
