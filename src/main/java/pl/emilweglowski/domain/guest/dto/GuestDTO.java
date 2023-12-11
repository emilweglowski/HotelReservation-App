package pl.emilweglowski.domain.guest.dto;
import pl.emilweglowski.domain.guest.Gender;

public class GuestDTO {

    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;

    public GuestDTO(long id, String firstName, String lastName, int age, String gender) {
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

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
