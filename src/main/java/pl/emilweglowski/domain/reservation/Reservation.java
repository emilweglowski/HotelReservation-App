package pl.emilweglowski.domain.reservation;

import jakarta.persistence.*;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.reservation.dto.ReservationDTO;
import pl.emilweglowski.domain.room.Room;

import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private final Room room;
    @OneToOne
    private final Guest guest;
    @Column(name = "fromDate")
    private final LocalDateTime from;
    private final LocalDateTime to;

    public Guest getGuest() {
        return guest;
    }

    public LocalDateTime getFrom() {
        return this.from;
    }

    public LocalDateTime getTo() {
        return this.to;
    }

    public Reservation(long id, Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.from = from;
        this.to = to;
    }

    public Reservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
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
        return this.room;
    }

    public ReservationDTO generateDTO() {
        return new ReservationDTO(this.id, this.from, this.to, (int)this.room.getId(), this.room.getNumber(),
                this.guest.getId(), this.guest.getFirstName()+" "+this.guest.getLastName());
    }

    public String getInfo() {
        return String.format("Reservation with id %d with room number %d by %s %s",
                this.id, this.room.getNumber(), this.guest.getFirstName(), this.guest.getLastName());
    }
}
