package pl.emilweglowski.domain.room;

import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final List<Room> rooms = new ArrayList<>();

    Room createNewRoom(int roomNumber, BedType[] bedType) {
        Room newRoom = new Room(roomNumber, bedType);
        rooms.add(newRoom);
        return newRoom;
    }

    public List<Room> getAll() {
        return this.rooms;
    }
}
