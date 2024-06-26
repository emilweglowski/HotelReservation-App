package pl.emilweglowski.domain.room;

import java.util.ArrayList;
import java.util.List;

public class RoomDatabaseRepository implements RoomRepository {

    private final List<Room> rooms = new ArrayList<>();
    private DatabaseRoomConnector connector = new JDBCRoomConnector();
    private static RoomDatabaseRepository instance = new RoomDatabaseRepository();

    public static RoomDatabaseRepository getInstance() {
        return instance;
    }

    RoomDatabaseRepository() {

    }

    @Override
    public void saveAll() {
    }

    @Override
    public void readAll() {

        this.rooms.addAll(connector.getAllRooms());
        List<Object[]> allBeds = connector.getAllBeds();

        for (Object[] tuple : allBeds) {
            this.getById((long)tuple[0]).addBed((BedType)tuple[1]);
        }
    }

    @Override
    public void remove(long id) {

        connector.remove(id);
        this.removeById(id);
    }

    private void removeById(long id) {
        int indexToBeRemoved = -1;
        for (int i = 0; i < rooms.size(); i++) {
            if (this.rooms.get(i).getId() == id) {
                indexToBeRemoved = i;
                break;
            }
        }
        this.rooms.remove(indexToBeRemoved);
    }

    @Override
    public void editRoom(long id, int roomNumber, List<BedType> bedTypes) {

        connector.editRoom(id, roomNumber, bedTypes);

        Room roomToBeUpdated = getById(id);
        roomToBeUpdated.setNumber(roomNumber);
        roomToBeUpdated.setBeds(bedTypes);
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

        Room newRoom = connector.createNewRoom(roomNumber, bedTypes);
        this.rooms.add(newRoom);
        return newRoom;
    }

    @Override
    public List<Room> getAllRooms() {
        return new ArrayList<>(this.rooms);
    }

    void setConnector(DatabaseRoomConnector connector) {
        this.connector = connector;
    }
}
