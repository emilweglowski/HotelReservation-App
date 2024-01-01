package pl.emilweglowski.domain.room;

import java.util.List;

public interface DatabaseRoomConnector {
    List<Room> getAllRooms();

    List<Object[]> getAllBeds();

    void remove(long id);

    void editRoom(long id, int roomNumber, List<BedType> bedTypes);

    Room createNewRoom(int roomNumber, List<BedType> bedTypes);
}
