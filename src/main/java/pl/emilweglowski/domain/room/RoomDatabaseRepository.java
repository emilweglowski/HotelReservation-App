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

    private final String removeBedsTemplate = "DELETE BEDS WHERE ROOM_ID = %d";
    private final String createBedTemplate = "INSERT INTO BEDS(ROOM_ID, BED) VALUES(%d, '%s')";

    @Override
    public void saveAll() {

    }

    @Override
    public void readAll() {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            ResultSet resultSet1 = statement.executeQuery("SELECT * FROM ROOMS");
            while (resultSet1.next()) {
                long id = resultSet1.getLong(1);
                int roomNumber = resultSet1.getInt(2);
                List<BedType> beds = new ArrayList<>();
                this.rooms.add((new Room(id, roomNumber, beds)));
            }
            ResultSet resultSet2 = statement.executeQuery("SELECT * FROM BEDS");
            while (resultSet2.next()) {
                long roomId = resultSet2.getLong(2);
                String bedType = resultSet2.getString(3);
                BedType bedTypeAsEnum = BedType.SINGLE;
                if (SystemUtils.DOUBLE_BED.equals(bedType)) {
                    bedTypeAsEnum = BedType.DOUBLE;
                } else if (SystemUtils.KING_SIZE.equals(bedType)) {
                    bedTypeAsEnum = BedType.KING_SIZE;
                }
                this.getById(roomId).addBed(bedTypeAsEnum);
            }
        } catch (SQLException e) {
            System.out.println("Error while reading rooms data from database");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(long id) {

        try {
            Statement statement = SystemUtils.connection.createStatement();
            String removeBedsQuery = String.format(removeBedsTemplate, id);
            statement.execute(removeBedsQuery);
            String removeRoomTemplate = "DELETE ROOMS WHERE ID = %d";
            String removeRoomQuery = String.format(removeRoomTemplate, id);
            statement.execute(removeRoomQuery);
            statement.close();
            this.removeById(id);
        } catch (SQLException e) {
            System.out.println("Error while deleting rooms data from database");
            throw new RuntimeException(e);
        }
    }

    private void removeById(long id) {
        int indexToBeRemoved = -1;
        for (int i=0; i< rooms.size(); i++) {
            if (this.rooms.get(i).getId()==id) {
                indexToBeRemoved = i;
                break;
            }
        }
        this.rooms.remove(indexToBeRemoved);
    }

    @Override
    public void editRoom(long id, int roomNumber, List<BedType> bedTypes) {

        try {
            Statement statement = SystemUtils.connection.createStatement();
            String updateRoomTemplate = "UPDATE ROOMS SET ROOM_NUMBER=%d WHERE ID=%d";
            String updateQuery = String.format(updateRoomTemplate, roomNumber, id);
            statement.executeUpdate(updateQuery);

            String removeBedsQuery = String.format(removeBedsTemplate, id);
            statement.execute(removeBedsQuery);

            for (BedType bedType : bedTypes) {
                String updateBedsQuery = String.format(createBedTemplate, id, bedType.toString());
                statement.execute(updateBedsQuery);
            }

            statement.close();

            Room roomToBeUpdated = getById(id);
            roomToBeUpdated.setNumber(roomNumber);
            roomToBeUpdated.setBeds(bedTypes);
        } catch (SQLException e) {
            System.out.println("Error while updating room data in database");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Room getById(long id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
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

            for(BedType bedType : bedTypes) {
                statement.execute(String.format(createBedTemplate, newID, bedType.toString()));
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
