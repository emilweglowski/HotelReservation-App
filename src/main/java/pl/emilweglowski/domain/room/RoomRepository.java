package pl.emilweglowski.domain.room;

import java.util.List;

public interface RoomRepository {

    void saveAll();

    void readAll();

    void remove(int id);

    void editRoom(int id, int roomNumber, List<BedType> bedTypes);

    Room getById(int id);

    Room createNewRoom(int roomNumber, List<BedType> bedType);

    List<Room> getAll();
}
