package pl.emilweglowski.domain.reservation;

import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.room.Room;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.util.SystemUtils;

import javax.swing.plaf.nimbus.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDatabaseRepository implements ReservationRepository {

    private final static ReservationRepository instance = new ReservationDatabaseRepository();

    private final RoomService roomService = ObjectPool.getRoomService();
    private final GuestService guestService = ObjectPool.getGuestService();
    private List<Reservation> reservations = new ArrayList<>();

    public static ReservationRepository getInstance() {
        return instance;
    }

    @Override
    public Reservation createNewReservation(Room room, Guest guest, LocalDateTime from, LocalDateTime to) {
        try {
            String fromAsStr = from.format(DateTimeFormatter.ISO_DATE_TIME);
            String toAsStr = to.format(DateTimeFormatter.ISO_DATE_TIME);
            Statement statement = SystemUtils.connection.createStatement();
            String createReservationTemplate = "INSERT INTO RESERVATIONS(ROOM_ID, GUEST_ID, RES_FROM, RES_TO) VALUES(%d, %d, '%s', '%s')";
            String createReservationQuery = String.format(createReservationTemplate, room.getId(), guest.getId(), fromAsStr, toAsStr);
            statement.execute(createReservationQuery,Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            long id = -1;
            while(resultSet.next()) {
                id = resultSet.getLong(1);
            }
            statement.close();
            Reservation newReservation = new Reservation(id, room, guest, from, to);
            this.reservations.add(newReservation);
            return newReservation;

        } catch (SQLException e) {
            System.out.println("Error while creating reservation");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void readAll() {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM RESERVATIONS");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long roomId = resultSet.getLong(2);
                long guestId = resultSet.getLong(3);
                String fromAsString = resultSet.getString(4);
                String toAsString = resultSet.getString(5);

                LocalDateTime from = LocalDateTime.parse(fromAsString.replace(" ", "T"), DateTimeFormatter.ISO_DATE_TIME);
                LocalDateTime to = LocalDateTime.parse(toAsString.replace(" ", "T"), DateTimeFormatter.ISO_DATE_TIME);

                Reservation newReservation = new Reservation(id, this.roomService.getRoomById(roomId), this.guestService.getGuestById(guestId), from, to);
                this.reservations.add(newReservation);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error while reading reservations");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll() {

    }

    @Override
    public List<Reservation> getAll() {
        return this.reservations;
    }

    @Override
    public void remove(long id) {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            String removeReservationTemplate = "DELETE FROM RESERVATIONS WHERE ID=%d";
            String removeQuery = String.format(removeReservationTemplate, id);
            statement.execute(removeQuery);
            statement.close();
            this.removeById(id);
        } catch (SQLException e) {
            System.out.println("Error while removing reservation");
            throw new RuntimeException(e);
        }
    }

    private void removeById(long id) {
        int indexToBeRemoved = -1;
        for (int i=0; i<this.reservations.size(); i++) {
            if (this.reservations.get(i).getId()==id) {
                indexToBeRemoved = i;
                break;
            }
        }
        this.reservations.remove(indexToBeRemoved);
    }
}
