package pl.emilweglowski.domain.room;

import pl.emilweglowski.domain.room.dto.RoomDTO;

public class Room {

    private final int id;
    private final int roomNumber;
    private final BedType[] beds;

    Room(int id, int roomNumber, BedType[] beds) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.beds = beds;
    }

    public int getId() {
        return id;
    }
    public int getNumber() {
        return roomNumber;
    }

    public String getInfo() {

        StringBuilder bedInfo = new StringBuilder("Bed types in this room:\n");

        for (BedType bed : beds) {
            bedInfo.append("\t").append(bed).append("\n");
        }

        return String.format("%d Number: %d \n%s", this.id, this.roomNumber, bedInfo);
    }

    String toCSV() {
        String[] bedsAsString = getBedsAsStrings();

        String bedTypes = String.join("#", bedsAsString);

        return String.format("%d,%d,%s%s", this.id, this.roomNumber, bedTypes, System.getProperty("line.separator"));
    }

    private String[] getBedsAsStrings() {
        String[] bedsAsString = new String[this.beds.length];

        for (int i=0; i<this.beds.length; i++) {
            bedsAsString[i] = this.beds[i].toString();
        }
        return bedsAsString;
    }

    public RoomDTO generateDTO() {
        String[] bedsAsString = getBedsAsStrings();

        String bedTypes = String.join(",", bedsAsString);

        return new RoomDTO(this.id, this.roomNumber, bedTypes);
    }
}
