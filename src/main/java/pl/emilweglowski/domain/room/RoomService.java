package pl.emilweglowski.domain.room;

import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.reservation.Reservation;
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.domain.room.dto.RoomDTO;
import pl.emilweglowski.exceptions.WrongOptionException;
import pl.emilweglowski.util.SystemUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomService {

    private RoomRepository repository = ObjectPool.getRoomRepository();
    private ReservationService reservationService = ObjectPool.getReservationService();


    public RoomService(){
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

    List<BedType> getBedTypes(int[] bedTypesOptions) {

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

    public List<Room> getAvailableRooms(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Parameters can not be null");
        }
        if(to.isBefore(from)) {
            throw new IllegalArgumentException("End date can not be before start date");
        }

        List<Room> availableRooms = this.repository.getAllRooms();

        LocalDateTime fromWithHour = from.atTime(SystemUtils.HOTEL_NIGHT_START_HOUR, SystemUtils.HOTEL_NIGHT_START_MINUTE);
        LocalDateTime toWithHour = to.atTime(SystemUtils.HOTEL_NIGHT_END_HOUR, SystemUtils.HOTEL_NIGHT_END_MINUTE);

        List<Reservation> reservations = this.reservationService.getAllReservations();

        for(Reservation reservation : reservations) {
            if (reservation.getFrom().isEqual(fromWithHour)) {
                availableRooms.remove(reservation.getRoom());
            } else if (reservation.getTo().isEqual(toWithHour)) {
                availableRooms.remove(reservation.getRoom());
            } else if (reservation.getFrom().isAfter(fromWithHour) && reservation.getFrom().isBefore(toWithHour)) {
                availableRooms.remove(reservation.getRoom());
            } else if (reservation.getTo().isAfter(fromWithHour) && reservation.getTo().isBefore(toWithHour)) {
                availableRooms.remove(reservation.getRoom());
            } else if (fromWithHour.isAfter(reservation.getFrom()) && toWithHour.isBefore(reservation.getTo())) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

    public void setRepository(RoomRepository roomRepository) {
        this.repository = roomRepository;
    }

    public void setReservationService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
}
