public class Room {

    private int roomNumber;
    private BedType[] beds;

    public Room(int roomNumber, BedType[] beds) {
        this.roomNumber = roomNumber;
        this.beds = beds;
    }

    public String getInfo() {

        String bedInfo = "Bed types in this room:\n";

        for (BedType bed : beds) {
            bedInfo = bedInfo + "\t" + bed + "\n";
        }

        return String.format("New room number %d added \n%s", this.roomNumber, bedInfo);
    }
}
