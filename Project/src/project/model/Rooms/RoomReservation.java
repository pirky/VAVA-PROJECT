package project.model.Rooms;

import java.time.LocalDate;

public class RoomReservation {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LibraryRoom room;

    public RoomReservation(LocalDate dateFrom, LocalDate dateTo, LibraryRoom room) {
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
        return new RoomReservation(this.dateFrom, this.dateTo, this.room);
    }
}
