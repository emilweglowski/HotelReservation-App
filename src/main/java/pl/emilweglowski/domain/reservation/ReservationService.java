package pl.emilweglowski.domain.reservation;

import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.reservation.dto.ReservationDTO;
import pl.emilweglowski.domain.room.Room;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.util.SystemUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    private final RoomService roomService = ObjectPool.getRoomService();
    private final GuestService guestService = ObjectPool.getGuestService();
    private final ReservationRepository repository = ObjectPool.getReservationRepository();

    public ReservationService() {
    }

    public Reservation createNewReservation(LocalDate from, LocalDate to, long roomId, long guestId) throws IllegalArgumentException {

        //TODO: handle null room
        Room room = this.roomService.getRoomById(roomId);
        //TODO: handle null guest
        Guest guest = this.guestService.getGuestById(guestId);

        LocalDateTime fromWithTime = from.atTime(SystemUtils.HOTEL_NIGHT_START_HOUR, SystemUtils.HOTEL_NIGHT_START_MINUTE);
        LocalDateTime toWithTime = to.atTime(SystemUtils.HOTEL_NIGHT_END_HOUR, SystemUtils.HOTEL_NIGHT_END_MINUTE);

        if (toWithTime.isBefore(fromWithTime)) {
            throw new IllegalArgumentException();
        }

        return this.repository.createNewReservation(room, guest, fromWithTime, toWithTime);
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void saveAll() {
        this.repository.saveAll();
    }

    public List<ReservationDTO> getReservationsAsDTO() {
        List<ReservationDTO> result = new ArrayList<>();
        List<Reservation> allReservations = repository.getAll();
        for(Reservation reservation : allReservations) {
            ReservationDTO dto = reservation.generateDTO();
            result.add(dto);
        }
        return result;
    }

    public void removeReservation(long id) {
        this.repository.remove(id);
    }

    public List<Reservation> getAllReservations() {
        return this.repository.getAll();
    }
}
