package pl.emilweglowski.domain.guest;

import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.util.SystemUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GuestFileRepository implements GuestRepository {

    private final List<Guest> guests = new ArrayList<>();
    private final static GuestRepository instance = new GuestFileRepository();

    private GuestFileRepository(){
    }

    public static GuestRepository getInstance() {
        return instance;
    }

    @Override
    public Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        Guest newGuest = new Guest(findNewId(), firstName, lastName, age, gender);
        guests.add(newGuest);
        return newGuest;
    }

    Guest addExistingGuest(long id, String firstName, String lastName, int age, Gender gender) {
        Guest newGuest = new Guest(id, firstName, lastName, age, gender);
        guests.add(newGuest);
        return newGuest;
    }

    @Override
    public List<Guest> getAll() {
        return this.guests;
    }

    @Override
    public void saveAll() {
        String name = "guests.csv";

        Path file = Paths.get(SystemUtils.DATA_DIRECTORY.toString(), name);

        StringBuilder sb = new StringBuilder("");

        for (Guest guest : this.guests) {
            sb.append(guest.toCSV());
        }

        try {
            Files.writeString(file, sb.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "write", "guests data");
        }
    }

    @Override
    public void readAll() {
        String name = "guests.csv";

        Path file = Paths.get(SystemUtils.DATA_DIRECTORY.toString(), name);

        if (!Files.exists(file)) {
            return;
        }

        try {
            String data = Files.readString(file, StandardCharsets.UTF_8);
            String[] guestsAsString = data.split(System.getProperty("line.separator"));

            for (String guestAsString : guestsAsString) {
                String[] guestData = guestAsString.split(",");
                if(guestData[0]==null || guestData[0].trim().isEmpty()) {
                    continue;
                }
                int id = Integer.parseInt(guestData[0]);
                int age = Integer.parseInt(guestData[3]);

                Gender gender = Gender.FEMALE;

                if(guestData[4].equals(SystemUtils.MALE)) {
                    gender = Gender.MALE;
                }

                addExistingGuest(id, guestData[1], guestData[2], age, gender);
            }

        } catch (IOException e) {
            throw new PersistenceToFileException(file.toString(), "read", "guests data");
        }
    }

    private long findNewId() {
        long max = 0;
        for (Guest guest : guests) {
            if (guest.getId() > max) {
                max = guest.getId();
            }
        }
        return max + 1;
    }

    @Override
    public void remove(long id) {
        int guestToBeRemovedIndex = -1;
        for (int i=0; i<this.guests.size(); i++) {
            if (this.guests.get(i).getId() == id) {
                guestToBeRemovedIndex = i;
                break;
            }
        }
        if (guestToBeRemovedIndex > -1) {
            this.guests.remove(guestToBeRemovedIndex);
        }
    }

    @Override
    public void edit(long id, String firstName, String lastName, int age, Gender gender) {
        this.remove(id);
        this.addExistingGuest(id,firstName,lastName,age,gender);
    }

    @Override
    public Guest getById(long id) {
        for (Guest guest : guests) {
            if (guest.getId() == id) {
                return guest;
            }
        }
        return null;
    }
}

