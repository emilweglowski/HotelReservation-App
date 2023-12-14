package pl.emilweglowski.domain.room;

import pl.emilweglowski.domain.room.dto.RoomDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {

    private final long id;
    private int roomNumber;
    private List<BedType> beds;

    Room(long id, int roomNumber, List<BedType> bedTypes) {
        this.id = id;
        this.roomNumber = roomNumber;
        if (bedTypes == null) {
            this.beds = new ArrayList<>();
        } else {
            this.beds = bedTypes;
        }
    }

    public long getId() {
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
        List<String> bedsAsString = getBedsAsStrings();

        String bedTypes = String.join("#", bedsAsString);

        return String.format("%d,%d,%s%s", this.id, this.roomNumber, bedTypes, System.getProperty("line.separator"));
    }

    private List<String> getBedsAsStrings() {

        List<String> bedsAsString = new ArrayList<>();

        for (int i=0; i<this.beds.size(); i++) {
            bedsAsString.add(this.beds.get(i).toString());
        }
        return bedsAsString;
    }

    public RoomDTO generateDTO() {

        List<String> bedsAsString = getBedsAsStrings();

        String bedTypes = String.join(",", bedsAsString);

        int roomSize = 0;

        for(BedType bedType : beds) {
            roomSize += bedType.getSize();
        }

        return new RoomDTO(this.id, this.roomNumber, bedTypes, beds.size(), roomSize);
    }

    void addBed(BedType bedType) {
        this.beds.add(bedType);
    }

    void setNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    void setBeds(List<BedType> bedTypes) {
        this.beds = bedTypes;
    }

    public List<BedType> getBeds() {
        return this.beds;
    }
}
