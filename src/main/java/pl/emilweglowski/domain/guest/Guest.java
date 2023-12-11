package pl.emilweglowski.domain.guest;

import pl.emilweglowski.domain.guest.dto.GuestDTO;

public class Guest {

    private final long id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final Gender gender;

    public Guest(long id, String firstName, String lastName, int age, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getInfo() {
        return String.format("%d %s %s (%d, %s)", this.id, this.firstName, this.lastName, this.age, this.gender.toString());
    }

    String toCSV() {
        return String.format("%d,%s,%s,%d,%s%s", this.id, this.firstName, this.lastName, this.age, this.gender, System.getProperty("line.separator"));
    }

    public GuestDTO generateDTO() {
        String gender = "male";
        if(this.gender.equals(Gender.FEMALE)) {
            gender = "female";
        }
        return new GuestDTO(this.id, this.firstName, this.lastName, this.age, gender);
    }
}
