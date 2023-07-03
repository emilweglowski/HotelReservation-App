package pl.emilweglowski.domain.room;

import pl.emilweglowski.exceptions.WrongOptionException;

public class RoomService {

    private final RoomRepository repository = new RoomRepository();

    public Room createNewRoom(int roomNumber, int[] bedTypesOptions) {

        BedType[] bedTypes = new BedType[bedTypesOptions.length];

        for (int i=0; i<bedTypesOptions.length; i++) {

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

        return repository.createNewRoom(roomNumber, bedTypes);
    }
}
