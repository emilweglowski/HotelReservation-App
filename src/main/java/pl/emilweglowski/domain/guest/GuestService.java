package pl.emilweglowski.domain.guest;

import pl.emilweglowski.domain.guest.dto.GuestDTO;

import java.util.ArrayList;
import java.util.List;

public class GuestService {

    private final static GuestRepository repository = new GuestRepository();

    public Guest createNewGuest(String firstName, String lastName, int age, boolean isMale) {

        Gender gender = Gender.FEMALE;
        if (isMale){
            gender = Gender.MALE;
        }
        return repository.createNewGuest(firstName, lastName, age, gender);
    }

    public List<Guest> getAllGuests() {
        return this.repository.getAll();
    }

    public void saveAll() {
        this.repository.saveAll();
    }

    public void readAll() {
        this.repository.readAll();
    }

    public void removeGuest(int id) {
        this.repository.remove(id);
    }

    public void editGuest(int id, String firstName, String lastName, int age, boolean isMale) {
        Gender gender = Gender.FEMALE;
        if (isMale){
            gender = Gender.MALE;
        }
        this.repository.edit(id, firstName, lastName, age, gender);
    }

    public Guest getGuestById(int guestId) {
        return this.repository.getById(guestId);
    }

    public List<GuestDTO> getGuestsAsDTO() {
        List<GuestDTO> result = new ArrayList<>();
        List<Guest> allGuests = repository.getAll();

        for(Guest guest : allGuests) {
            GuestDTO dto = guest.generateDTO();
            result.add(dto);
        }
        return result;
    }
}
