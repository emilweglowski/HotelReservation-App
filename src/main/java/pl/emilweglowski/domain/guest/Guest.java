package pl.emilweglowski.domain.guest;
import jakarta.persistence.*;
import pl.emilweglowski.domain.guest.dto.GuestDTO;

@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    @Enumerated
    private Gender gender;

    public Guest(long id, String firstName, String lastName, int age, Gender gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public Guest(String firstName, String lastName, int age, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public Guest() {
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
