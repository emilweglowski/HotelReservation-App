package pl.emilweglowski.domain.reservation;

import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.room.Room;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.util.SystemUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    List<Reservation> reservations = new ArrayList<>();
    RoomService roomService = ObjectPool.getRoomService();
    GuestService guestService = ObjectPool.getGuestService();

    private static final ReservationFileRepository instance = new ReservationFileRepository();

    private ReservationFileRepository() {
    }

    long findNewId() {
        long max = 0;
        for (Reservation reservation : this.reservations) {
            if (reservation.getId() > max) {
                max = reservation.getId();
            }
        }
        return max + 1;
    }

    public static ReservationFileRepository getInstance() {
        return instance;
    }

    @Override
    public Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        Reservation reservation = new Reservation(findNewId(), room, guest, from, to);
        this.reservations.add(reservation);
        return reservation;
    }

    @Override
    public void readAll() {
        String name = "reservations.csv";

        Path file = Paths.get(SystemUtils.DATA_DIRECTORY.toString(), name);

        if (!Files.exists(file)) {
            return;
        }

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] reservationsAsString = data.split(System.getProperty("line.separator"));

            for (String reservationAsString : reservationsAsString) {
                String[] reservationData = reservationAsString.split(",");
                if (reservationData[0] == null || reservationData[0].trim().isEmpty()) {
                    continue;
                }
                int id = Integer.parseInt(reservationData[0]);
                int roomId = Integer.parseInt(reservationData[1]);
                int guestId = Integer.parseInt(reservationData[2]);
                String fromAsString = reservationData[3];
                String toAsString = reservationData[4];
                //TODO: handle null guest/room
                addExistingReservation(id,
                        this.roomService.getRoomById(roomId),
                        this.guestService.getGuestById(guestId),
                        LocalDateTime.parse(fromAsString),
                        LocalDateTime.parse(toAsString));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addExistingReservation(long id, Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        Reservation reservation = new Reservation(id, room, guest, from, to);
        this.reservations.add(reservation);
    }

    @Override
    public void saveAll() {
        String name = "reservations.csv";

        Path file = Paths.get(SystemUtils.DATA_DIRECTORY.toString(), name);

        StringBuilder sb = new StringBuilder("");

        for (Reservation reservation : reservations) {
            sb = sb.append(reservation.toCSV());
        }

        try {
            Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "write", "reservations data");
        }
    }

    @Override
    public List<Reservation> getAll() {
        return this.reservations;
    }

    @Override
    public void remove(long id) {
        int reservationToBeRemovedIndex = -1;
        for (int i = 0; i < this.reservations.size(); i++) {
            if (this.reservations.get(i).getId() == id) {
                reservationToBeRemovedIndex = i;
                break;
            }
        }
        if (reservationToBeRemovedIndex > -1) {
            this.reservations.remove(reservationToBeRemovedIndex);
        }
    }
}
