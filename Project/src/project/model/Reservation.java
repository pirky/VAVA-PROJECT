package project.model;

import java.time.LocalDate;

public class Reservation {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LibraryRoom room;

    public Reservation(LocalDate dateFrom, LocalDate dateTo, LibraryRoom room) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.room = room;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public LibraryRoom getRoom() {
        return (LibraryRoom) room.clone();
    }

    public void setRoom(LibraryRoom room) {
        this.room = (LibraryRoom) room.clone();
    }

    public Object clone(){
        return new Reservation(this.dateFrom, this.dateTo, this.room);
    }
}
