package pl.emilweglowski.domain.guest;

import pl.emilweglowski.domain.ObjectPool;
import pl.emilweglowski.domain.guest.dto.GuestDTO;

import java.util.ArrayList;
import java.util.List;

public class GuestService {

    private final GuestRepository repository = ObjectPool.getGuestRepository();
    private final static GuestService instance = new GuestService();

    private GuestService() {
    }

    public static GuestService getInstance() {
        return instance;
    }

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

    public void removeGuest(long id) {
        this.repository.remove(id);
    }

    public void editGuest(long id, String firstName, String lastName, int age, boolean isMale) {
        Gender gender = Gender.FEMALE;
        if (isMale){
            gender = Gender.MALE;
        }
        this.repository.edit(id, firstName, lastName, age, gender);
    }

    public Guest getGuestById(long guestId) {
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
