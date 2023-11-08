package pl.emilweglowski.domain.room;

import pl.emilweglowski.util.SystemUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoomDatabaseRepository implements RoomRepository {

    private final List<Room> rooms = new ArrayList<>();
    private static RoomRepository instance = new RoomDatabaseRepository();

    public static RoomRepository getInstance() {
        return instance;
    }

    @Override
    public void saveAll() {

    }

    @Override
    public void readAll() {

    }

    @Override
    public void remove(long id) {

    }

    @Override
    public void editRoom(long id, int roomNumber, List<BedType> bedTypes) {

    }

    @Override
    public Room getById(long id) {
        return null;
    }

    @Override
    public Room createNewRoom(int roomNumber, List<BedType> bedTypes) {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            String insertRoomTemplate = "INSERT INTO ROOMS(ROOM_NUMBER) VALUES(%d)";
            String query = String.format(insertRoomTemplate, roomNumber);
            statement.execute(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();

            long newID = -1;
            while(resultSet.next()) {
                newID = resultSet.getLong(1);
            }

            String insertBedTemplate = "INSERT INTO BEDS(ROOM_ID, BED) VALUES(%d, '%s')";
            for(BedType bedType : bedTypes) {
                statement.execute(String.format(insertBedTemplate, newID, bedType.toString()));
            }

            statement.close();

            Room newRoom = new Room(newID, roomNumber, bedTypes);
            this.rooms.add(newRoom);
            return newRoom;

        } catch (SQLException e) {
            System.out.println("Error when creating new room");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> getAllRooms() {
        return this.rooms;
    }
}
