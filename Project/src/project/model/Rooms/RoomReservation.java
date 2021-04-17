package project.model.Rooms;

import java.time.LocalDate;

public class RoomReservation {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int roomId;

    public RoomReservation(LocalDate dateFrom, LocalDate dateTo, int roomId) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.roomId = roomId;
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Object clone(){
        return new RoomReservation(this.dateFrom, this.dateTo, this.roomId);
    }
}
