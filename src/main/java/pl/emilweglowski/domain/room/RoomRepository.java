package pl.emilweglowski.domain.room;

public class RoomRepository {

    Room createNewRoom(int roomNumber, BedType[] bedType) {
        return new Room(roomNumber, bedType);
    }
}
