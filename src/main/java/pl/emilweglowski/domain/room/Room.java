package pl.emilweglowski.domain.room;

public class Room {

    private final int roomNumber;
    private final BedType[] beds;

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

    String toCSV() {
        String[] bedsAsString = new String[this.beds.length];

        for (int i=0; i<this.beds.length; i++) {
            bedsAsString[i] = this.beds[i].toString();
        }

        String bedTypes = String.join("#", bedsAsString);

        return String.format("%d,%s%s", this.roomNumber, bedTypes, System.getProperty("line.separator"));
    }
}
