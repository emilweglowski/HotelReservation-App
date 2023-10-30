package pl.emilweglowski.domain.room;

import pl.emilweglowski.domain.room.BedType;
import pl.emilweglowski.domain.room.Room;
import pl.emilweglowski.domain.room.RoomRepository;

import java.util.List;

public class RoomDatabaseRepository implements RoomRepository {

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
    public void remove(int id) {

    }

    @Override
    public void editRoom(int id, int roomNumber, List<BedType> bedTypes) {

    }

    @Override
    public Room getById(int id) {
        return null;
    }

    @Override
    public Room createNewRoom(int roomNumber, List<BedType> bedType) {
        return null;
    }

    @Override
    public List<Room> getAll() {
        return null;
    }
}
