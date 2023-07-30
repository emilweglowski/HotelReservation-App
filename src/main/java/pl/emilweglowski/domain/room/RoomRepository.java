package pl.emilweglowski.domain.room;

import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.util.Properties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository {

    private final List<Room> rooms = new ArrayList<>();

    Room createNewRoom(int roomNumber, BedType[] bedType) {
        Room newRoom = new Room(findNewId(), roomNumber, bedType);
        rooms.add(newRoom);
        return newRoom;
    }

    Room addRoomFromFile(int id, int roomNumber, BedType[] bedType) {
        Room newRoom = new Room(id, roomNumber, bedType);
        rooms.add(newRoom);
        return newRoom;
    }

    public List<Room> getAll() {
        return this.rooms;
    }

    void saveAll() {
        String name = "rooms.csv";

        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

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

        Path file = Paths.get(Properties.DATA_DIRECTORY.toString(), name);

        if (!Files.exists(file)) {
            return;
        }

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] roomsAsString = data.split(System.getProperty("line.separator"));

            for (String roomAsString : roomsAsString) {
                String[] roomData = roomAsString.split(",");
                int id = Integer.parseInt(roomData[0]);
                int number = Integer.parseInt(roomData[1]);
                String bedTypesData = roomData[2];

                String[] bedTypesAsString = bedTypesData.split("#");
                BedType[] bedTypes = new BedType[bedTypesAsString.length];

                for (int i = 0; i < bedTypes.length; i++) {
                    bedTypes[i] = BedType.valueOf(bedTypesAsString[i]);
                }

                addRoomFromFile(id, number, bedTypes);
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
}
