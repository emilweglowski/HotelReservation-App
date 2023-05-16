public class Guest {

    private String firstName;
    private String lastName;
    private int age;

    public Guest(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getInfo() {
        return String.format("New guest added: %s %s (%d)", this.firstName, this.lastName, this.age);
    }
}
