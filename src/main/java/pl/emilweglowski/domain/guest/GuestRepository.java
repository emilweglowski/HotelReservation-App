package pl.emilweglowski.domain.guest;

import java.util.ArrayList;
import java.util.List;

public class GuestRepository {

    private final List<Guest> guests = new ArrayList<>();

    Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest newGuest = new Guest(firstName, lastName, age, gender);
        guests.add(newGuest);
        return newGuest;
    }

    List<Guest> getAll() {
        return this.guests;
    }
}

