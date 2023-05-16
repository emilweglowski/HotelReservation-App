public class Room {

    private int roomNumber;
    private int numberOfBeds;

    public Room(int roomNumber, int numberOfBeds) {
        this.roomNumber = roomNumber;
        this.numberOfBeds = numberOfBeds;
    }

    public String getInfo() {
        return String.format("New room number %d added, number of beds: %d", this.roomNumber, this.numberOfBeds);
    }
}
