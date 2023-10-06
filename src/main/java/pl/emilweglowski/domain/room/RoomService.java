package pl.emilweglowski.domain.room;

import pl.emilweglowski.domain.room.dto.RoomDTO;
import pl.emilweglowski.exceptions.WrongOptionException;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private final RoomRepository repository = RoomRepository.getInstance();
    private final static RoomService instance = new RoomService();

    private RoomService(){
    }

    public Room createNewRoom(int roomNumber, int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for (int i=0; i<bedTypesOptions.length; i++) {

            BedType bedType;
            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }
            bedTypes[i] = bedType;
        }

        return repository.createNewRoom(roomNumber, bedTypes);
    }

    public List<Room> getAllRooms() {
        return this.repository.getAll();
    }

    public void saveAll() {
        this.repository.saveAll();
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void removeRoom(int id) {
        this.repository.remove(id);
    }

    public void editRoom(int id, int roomNumber, int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for (int i=0; i<bedTypesOptions.length; i++) {
            BedType bedType;
            if (bedTypesOptions[i] == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypesOptions[i] == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesOptions[i] == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        this.repository.editRoom(id, roomNumber, bedTypes);
    }

    public Room getRoomById(int roomId) {
        return this.repository.getById(roomId);
    }

    public List<RoomDTO> getRoomsAsDTO() {
        List<RoomDTO> result = new ArrayList<>();
        List<Room> allRooms = repository.getAll();

        for(Room room : allRooms) {
            RoomDTO dto = room.generateDTO();
            result.add(dto);
        }
        return result;
    }

    public static RoomService getInstance() {
        return instance;
    }
}
