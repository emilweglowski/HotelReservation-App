package pl.emilweglowski.domain.reservation;

import org.junit.jupiter.api.Test;
import pl.emilweglowski.domain.guest.Gender;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.reservation.dto.ReservationDTO;
import pl.emilweglowski.domain.room.BedType;
import pl.emilweglowski.domain.room.Room;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTest {

    @Test
    public void toCSVTest() {

        //given
        List<BedType> beds = Arrays.asList(BedType.values());
        Room room = new Room(1, 301, beds);
        Guest guest = new Guest(1, "Jan", "Kowalski", 40, Gender.MALE);
        Reservation reservation = new Reservation(1, room, guest, LocalDateTime.of(2023,12,15,15,0),
                LocalDateTime.of(2023,12,18,10,0));

        //when
        String createdCSV = reservation.toCSV();

        //then
        String csvTemplate = "1,1,1,2023-12-15T15:00,2023-12-18T10:00"+System.getProperty("line.separator");
        assertEquals(createdCSV, csvTemplate);
    }

    @Test
    public void generateDTOTest() {

        //given
        List<BedType> beds = Arrays.asList(BedType.values());
        Room room = new Room(1, 301, beds);
        Guest guest = new Guest(2, "Jan", "Kowalski", 40, Gender.MALE);
        Reservation reservation = new Reservation(3, room, guest, LocalDateTime.of(2023,12,15,15,0),
                LocalDateTime.of(2023,12,18,10,0));

        //when
        ReservationDTO reservationDTO = reservation.generateDTO();

        //then
        assertEquals(reservationDTO.getId(), 3);
        assertEquals(reservationDTO.getFrom().toString(), "2023-12-15T15:00");
        assertEquals(reservationDTO.getTo().toString(), "2023-12-18T10:00");
        assertEquals(reservationDTO.getRoomId(), 1);
        assertEquals(reservationDTO.getRoomNumber(), 301);
        assertEquals(reservationDTO.getGuestId(), 2);
        assertEquals(reservationDTO.getGuestName(), "Jan Kowalski");
    }

    @Test
    public void generateDTOWithNullBedsListTest() {

        //given
        Room room = new Room(1, 301, null);
        Guest guest = new Guest(2, "Jan", "Kowalski", 40, Gender.MALE);
        Reservation reservation = new Reservation(3, room, guest, LocalDateTime.of(2023,12,15,15,0),
                LocalDateTime.of(2023,12,18,10,0));

        //when
        ReservationDTO reservationDTO = reservation.generateDTO();

        //then
        assertEquals(reservationDTO.getId(), 3);
        assertEquals(reservationDTO.getFrom().toString(), "2023-12-15T15:00");
        assertEquals(reservationDTO.getTo().toString(), "2023-12-18T10:00");
        assertEquals(reservationDTO.getRoomId(), 1);
        assertEquals(reservationDTO.getRoomNumber(), 301);
        assertEquals(reservationDTO.getGuestId(), 2);
        assertEquals(reservationDTO.getGuestName(), "Jan Kowalski");
    }
}
