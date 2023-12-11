package pl.emilweglowski.domain.room;

import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.room.dto.RoomDTO;
import pl.emilweglowski.exceptions.WrongOptionException;
import pl.emilweglowski.util.SystemUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomService {

    private final RoomRepository repository = ObjectPool.getRoomRepository();
    private final static RoomService instance = new RoomService();

    private RoomService(){
    }

    public Room createNewRoom(int roomNumber, List<String> bedTypesAsStrings) {

        List<BedType> bedTypes = getBedTypes(bedTypesAsStrings);

        return repository.createNewRoom(roomNumber, bedTypes);
    }

    public Room createNewRoom(int roomNumber, int[] bedTypesOptions) {

        List<BedType> bedTypes = getBedTypes(bedTypesOptions);

        return repository.createNewRoom(roomNumber, bedTypes);
    }

    public List<Room> getAllRooms() {
        return this.repository.getAllRooms();
    }

    public void saveAll() {
        this.repository.saveAll();
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void removeRoom(long id) {
        this.repository.remove(id);
    }

    public void editRoom(long id, int roomNumber, List<String> bedTypesAsStrings) {

        List<BedType> bedTypes = getBedTypes(bedTypesAsStrings);

        this.repository.editRoom(id, roomNumber, bedTypes);
    }

    private static List<BedType> getBedTypes(List<String> bedTypesAsStrings) {

        BedType[] bedTypes = new BedType[bedTypesAsStrings.size()];

        for (int i = 0; i< bedTypesAsStrings.size(); i++) {

            BedType bedType;
            if (bedTypesAsStrings.get(i).equals(SystemUtils.SINGLE_BED)) {
                bedType = BedType.SINGLE;
            } else if (bedTypesAsStrings.get(i).equals(SystemUtils.DOUBLE_BED)) {
                bedType = BedType.DOUBLE;
            } else if (bedTypesAsStrings.get(i).equals(SystemUtils.KING_SIZE)) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }
            bedTypes[i] = bedType;
        }
        return Arrays.asList(bedTypes);
    }

    public void editRoom(int id, int roomNumber, int[] bedTypesOptions) {

        List<BedType> bedTypes = getBedTypes(bedTypesOptions);

        this.repository.editRoom(id, roomNumber, bedTypes);
    }

    private static List<BedType> getBedTypes(int[] bedTypesOptions) {

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
        return Arrays.asList(bedTypes);
    }

    public Room getRoomById(long roomId) {
        return this.repository.getById(roomId);
    }

    public List<RoomDTO> getRoomsAsDTO() {
        List<RoomDTO> result = new ArrayList<>();
        List<Room> allRooms = repository.getAllRooms();

        for(Room room : allRooms) {
            System.out.println("Room" + room.getId());
            RoomDTO dto = room.generateDTO();
            result.add(dto);
        }
        return result;
    }

    public static RoomService getInstance() {
        return instance;
    }
}
