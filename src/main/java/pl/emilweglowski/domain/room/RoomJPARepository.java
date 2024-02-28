package pl.emilweglowski.domain.room;
import java.util.List;

public class RoomJPARepository implements RoomRepository {
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
    public Room createNewRoom(int roomNumber, List<BedType> bedType) {
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        return null;
    }
}
