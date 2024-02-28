package pl.emilweglowski.domain;

import pl.emilweglowski.domain.guest.GuestJPARepository;
import pl.emilweglowski.domain.guest.GuestRepository;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.guest.GuestDatabaseRepository;
import pl.emilweglowski.domain.reservation.ReservationDatabaseRepository;
import pl.emilweglowski.domain.reservation.ReservationRepository;
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.domain.room.RoomDatabaseRepository;
import pl.emilweglowski.domain.room.RoomRepository;
import pl.emilweglowski.domain.room.RoomService;

public class ObjectPool {

    private final static RoomService roomService = new RoomService();
    private final static ReservationService reservationService = new ReservationService();
    private ObjectPool(){
    }

    public static GuestService getGuestService() {
        return GuestService.getInstance();
    }

    public static GuestRepository getGuestRepository() {
//        return GuestFileRepository.getInstance();
//        return GuestDatabaseRepository.getInstance();
        return GuestJPARepository.getInstance();
    }

    public static RoomService getRoomService() {
        return roomService;
    }

    public static RoomRepository getRoomRepository() {
//        return RoomFileRepository.getInstance();
        return RoomDatabaseRepository.getInstance();
    }

    public static ReservationService getReservationService() {
        return reservationService;
    }

    public static ReservationRepository getReservationRepository() {
//        return ReservationFileRepository.getInstance();
        return ReservationDatabaseRepository.getInstance();
    }
}
