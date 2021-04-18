package project.model.events;

import project.model.Rooms.RoomReservation;
import project.model.users.Organizer;
import project.model.users.Reader;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String note;
    private RoomReservation roomReservation;
    private Organizer organizer;
    private List<Reader> volunteers;

    public Event(String name, String note, RoomReservation roomReservation, Organizer organizer) {
        this.name = name;
        this.note = note;
        this.roomReservation = roomReservation;
        this.organizer = organizer;
        this.volunteers = new ArrayList<>();
    }

    public List<Reader> getVolunteers() {
        List<Reader> returnList = new ArrayList<>();
        for(Reader volunteer: volunteers){
            returnList.add((Reader) volunteer.clone());
        }
        return returnList;
    }

    public void setVolunteers(List<Reader> volunteers) {
        List<Reader> list = new ArrayList<>();
        for(Reader volunteer: volunteers){
            list.add((Reader) volunteer.clone());
        }
        this.volunteers = list;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomReservation getReservation() {
        return (RoomReservation) roomReservation.clone();
    }

    public void setReservation(RoomReservation roomReservation) {
        this.roomReservation = (RoomReservation) roomReservation.clone();
    }

    public String toString(){
        return name + ", od: " + roomReservation.getDateFrom() + ", do: " + roomReservation.getDateTo();
    }

    public Object clone(){
        Event event = new Event(this.name, this.note, this.roomReservation, this.organizer);
        event.setVolunteers(this.volunteers);
        return event;
    }
}
