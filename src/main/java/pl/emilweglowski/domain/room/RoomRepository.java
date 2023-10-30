package pl.emilweglowski.domain.room;

import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.util.SystemUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomRepository {

    private final static RoomRepository instance = new RoomRepository();

    private RoomRepository() {
    }

    public static RoomRepository getInstance() {
        return instance;
    }

    private final List<Room> rooms = new ArrayList<>();

    Room createNewRoom(int roomNumber, List<BedType> bedType) {
        Room newRoom = new Room(findNewId(), roomNumber, bedType);
        rooms.add(newRoom);
        return newRoom;
    }

    Room addExistingRoom(int id, int roomNumber, List<BedType> bedType) {
        Room newRoom = new Room(id, roomNumber, bedType);
        rooms.add(newRoom);
        return newRoom;
    }

    public List<Room> getAll() {
        return this.rooms;
    }

    void saveAll() {
        String name = "rooms.csv";

        Path file = Paths.get(SystemUtils.DATA_DIRECTORY.toString(), name);

        StringBuilder sb = new StringBuilder("");

        for (Room room : this.rooms) {
            sb.append(room.toCSV());
        }

        try {
            Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "write", "rooms data");
        }
    }

    void readAll() {
        String name = "rooms.csv";

        Path file = Paths.get(SystemUtils.DATA_DIRECTORY.toString(), name);

        if (!Files.exists(file)) {
            return;
        }

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] roomsAsString = data.split(System.getProperty("line.separator"));

            for (String roomAsString : roomsAsString) {
                String[] roomData = roomAsString.split(",");
                if(roomData[0]==null || roomData[0].trim().isEmpty()) {
                    continue;
                }
                int id = Integer.parseInt(roomData[0]);
                int number = Integer.parseInt(roomData[1]);
                String bedTypesData = roomData[2];

                String[] bedTypesAsString = bedTypesData.split("#");
                BedType[] bedTypes = new BedType[bedTypesAsString.length];

                for (int i = 0; i < bedTypes.length; i++) {

                    if(bedTypesAsString[i].equals(SystemUtils.SINGLE_BED)) {
                        bedTypes[i] = BedType.SINGLE;
                    } else if(bedTypesAsString[i].equals(SystemUtils.DOUBLE_BED)) {
                        bedTypes[i] = BedType.DOUBLE;
                    } else if(bedTypesAsString[i].equals(SystemUtils.KING_SIZE)) {
                        bedTypes[i] = BedType.KING_SIZE;
                    }
                }

                addExistingRoom(id, number, Arrays.asList(bedTypes));
            }
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "read", "rooms data");
        }
    }

    private int findNewId() {
        int max = 0;
        for (Room room : rooms) {
            if (room.getId() > max) {
                max = room.getId();
            }
        }
        return max + 1;
    }

    public void remove(int id) {
        int roomToBeRemovedIndex = -1;
        for (int i=0; i<this.rooms.size(); i++) {
            if (this.rooms.get(i).getId() == id) {
                roomToBeRemovedIndex = i;
                break;
            }
        }
        if (roomToBeRemovedIndex > -1) {
            this.rooms.remove(roomToBeRemovedIndex);
        }
    }

    public void editRoom(int id, int roomNumber, List<BedType> bedTypes) {
        this.remove(id);
        this.addExistingRoom(id, roomNumber, bedTypes);
    }

    public Room getById(int id) {
        for (Room room : rooms) {
            if (room.getId() == id) {
                return room;
            }
        }
        return null;
    }
}
