package pl.emilweglowski.domain.room;

import java.util.List;

public interface RoomRepository {

    void saveAll();

    void readAll();

    void remove(long id);

    void editRoom(long id, int roomNumber, List<BedType> bedTypes);

    Room getById(long id);

    Room createNewRoom(int roomNumber, List<BedType> bedType);

    List<Room> getAllRooms();
}
