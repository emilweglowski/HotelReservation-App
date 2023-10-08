package pl.emilweglowski.domain;

import pl.emilweglowski.domain.guest.GuestRepository;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.reservation.ReservationRepository;
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.domain.room.RoomRepository;
import pl.emilweglowski.domain.room.RoomService;

public class ObjectPool {

    private ObjectPool(){
    }

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {
        return GuestRepository.getInstance();
    }

    public static RoomService getRoomService() {
        return RoomService.getInstance();
    }

    public static RoomRepository getRoomRepository() {
        return RoomRepository.getInstance();
    }

    public static ReservationService getReservationService() {
        return ReservationService.getInstance();
    }

    public static ReservationRepository getReservationRepository() {
        return ReservationRepository.getInstance();
    }
}
