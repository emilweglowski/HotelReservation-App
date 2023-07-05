package pl.emilweglowski.domain.room;

public class Room {

    private final int roomNumber;
    private final BedType[] beds;

    //default package-private constructor for Room object - only room domain can use this
    Room(int roomNumber, BedType[] beds) {
        this.roomNumber = roomNumber;
        this.beds = beds;
    }

    public String getInfo() {

        StringBuilder bedInfo = new StringBuilder("Bed types in this room:\n");

        for (BedType bed : beds) {
            bedInfo.append("\t").append(bed).append("\n");
        }

        return String.format("Number: %d \n%s", this.roomNumber, bedInfo);
    }
}
