package project.model.events;

import project.model.Rooms.RoomReservation;
import project.model.users.Organizer;

public class BookDiscussion extends Event{
    private String host;

    public BookDiscussion(String name, String note, RoomReservation roomReservation, Organizer organizer, String host) {
        super(name, note, roomReservation, organizer);
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Object clone(){
        return new BookDiscussion(this.getName(), this.getNote(), this.getReservation(), this.getOrganizer(), this.host);
    }
}
