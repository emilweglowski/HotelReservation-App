package pl.emilweglowski.domain.reservation;

import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.reservation.dto.ReservationDTO;
import pl.emilweglowski.domain.room.Room;

import java.time.LocalDateTime;

public class Reservation {

    private final long id;
    private final Room room;
    private final Guest guest;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Guest getGuest() {
        return guest;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public Reservation(long id, Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.from = from;
        this.to = to;
    }

    String toCSV() {
        return String.format("%s,%s,%s,%s,%s%s", this.id, this.room.getId(), this.guest.getId(), this.from.toString(),
                this.to.toString(), System.getProperty("line.separator"));
    }

    public long getId() {
        return this.id;
    }

    public Room getRoom() {
        return room;
    }

    public ReservationDTO generateDTO() {
        return new ReservationDTO(this.id, this.from, this.to, (int)this.room.getId(), this.room.getNumber(),
                this.guest.getId(), this.guest.getFirstName()+" "+this.guest.getLastName());
    }
}
