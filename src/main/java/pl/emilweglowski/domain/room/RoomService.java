package pl.emilweglowski.domain.room;

import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.room.dto.RoomDTO;
import pl.emilweglowski.exceptions.WrongOptionException;
import pl.emilweglowski.util.Properties;

import java.util.ArrayList;
import java.util.List;

public class RoomService {

    private final RoomRepository repository = ObjectPool.getRoomRepository();
    private final static RoomService instance = new RoomService();

    private RoomService(){
    }

    public Room createNewRoom(int roomNumber, List<String> bedTypesAsStrings) {

        BedType[] bedTypes = getBedTypes(bedTypesAsStrings);

        return repository.createNewRoom(roomNumber, bedTypes);
    }

    public Room createNewRoom(int roomNumber, int[] bedTypesOptions) {

        BedType[] bedTypes = getBedTypes(bedTypesOptions);

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

    public void editRoom(int id, int roomNumber, List<String> bedTypesAsStrings) {

        BedType[] bedTypes = getBedTypes(bedTypesAsStrings);

        this.repository.editRoom(id, roomNumber, bedTypes);
    }

    private static BedType[] getBedTypes(List<String> bedTypesAsStrings) {

        BedType[] bedTypes = new BedType[bedTypesAsStrings.size()];

        for (int i = 0; i< bedTypesAsStrings.size(); i++) {

            BedType bedType;
            if (bedTypesAsStrings.get(i).equals(Properties.SINGLE_BED)) {
                bedType = BedType.SINGLE;
            } else if (bedTypesAsStrings.get(i).equals(Properties.DOUBLE_BED)) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesAsStrings.get(i).equals(Properties.KING_SIZE)) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        return bedTypes;
    }

    public void editRoom(int id, int roomNumber, int[] bedTypesOptions) {

        BedType[] bedTypes = getBedTypes(bedTypesOptions);

        this.repository.editRoom(id, roomNumber, bedTypes);
    }

    private static BedType[] getBedTypes(int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for(int i = 0; i< bedTypesOptions.length; i=i+1) {
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
        return bedTypes;
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
