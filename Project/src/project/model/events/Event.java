package project.model.events;

import project.model.Reservation;
import project.model.users.Organizer;
import project.model.users.Reader;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String note;
    private Reservation reservation;
    private Organizer organizer;
    private List<Reader> volunteers;

    public Event(String name, String note, Reservation reservation, Organizer organizer) {
        this.name = name;
        this.note = note;
        this.reservation = reservation;
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

    public Reservation getReservation() {
        return (Reservation) reservation.clone();
    }

    public void setReservation(Reservation reservation) {
        this.reservation = (Reservation) reservation.clone();
    }

    public Object clone(){
        Event event = new Event(this.name, this.note, this.reservation, this.organizer);
        event.setVolunteers(this.volunteers);
        return event;
    }
}
