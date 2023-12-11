package pl.emilweglowski.domain.guest;

import pl.emilweglowski.util.SystemUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GuestDatabaseRepository implements GuestRepository {

    private static final GuestRepository instance = new GuestDatabaseRepository();

    public static GuestRepository getInstance() {
        return instance;
    }

    private List<Guest> guests = new ArrayList<>();

    @Override
    public Guest createNewGuest(String firstName, String lastName, int age, Gender gender) {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            String createGuestTemplate = "INSERT INTO GUESTS(FIRST_NAME, lAST_NAME, AGE, GENDER) VALUES('%s', '%s', %d, '%s')";
            String createGuestQuery = String.format(createGuestTemplate, firstName, lastName, age, gender.toString());
            statement.execute(createGuestQuery, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.getGeneratedKeys();
            long newId = -1;
            while (resultSet.next()) {
                newId = resultSet.getLong(1);
            }
            statement.close();
            Guest newGuest = new Guest(newId, firstName, lastName, age, gender);
            this.guests.add(newGuest);
            return newGuest;
        } catch (SQLException e) {
            System.out.println("Error while creating guest");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Guest> getAll() {
        return this.guests;
    }

    @Override
    public void saveAll() {

    }

    @Override
    public void readAll() {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM GUESTS");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                int age = resultSet.getInt(4);
                String genderAsString = resultSet.getString(5);
                Gender gender = Gender.FEMALE;
                if (SystemUtils.MALE.equals(genderAsString)) {
                    gender = Gender.MALE;
                }
                Guest newGuest = new Guest(id, firstName, lastName, age, gender);
                this.guests.add(newGuest);
            }
            statement.close();
        } catch (SQLException e) {
            System.out.println("Error while reading guests data from database");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(long id) {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            String removeGuestTemplate = "DELETE GUESTS WHERE ID = %d";
            String removeGuestQuery = String.format(removeGuestTemplate, id);
            statement.execute(removeGuestQuery);
            statement.close();
            this.removeById(id);
        } catch (SQLException e) {
            System.out.println("Error while deleting guests data from database");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void edit(long id, String firstName, String lastName, int age, Gender gender) {
        try {
            Statement statement = SystemUtils.connection.createStatement();
            String updateGuestTemplate = "UPDATE GUESTS SET FIRST_NAME='%s', LAST_NAME='%s', AGE=%d, GENDER='%s' WHERE ID=%d";
            String updateQuery = String.format(updateGuestTemplate, firstName, lastName, age, gender.toString(), id);
            statement.executeUpdate(updateQuery);
            statement.close();

            this.removeById(id);
            this.guests.add(new Guest(id, firstName, lastName, age, gender));
        } catch (SQLException e) {
            System.out.println("Error while updating guest data in database");
            throw new RuntimeException(e);
        }
    }

    public void removeById(long id) {
        int indexToBeRemoved = -1;
        for (int i = 0; i < guests.size(); i++) {
            if (this.guests.get(i).getId() == id) {
                indexToBeRemoved = i;
                break;
            }
        }
        if (indexToBeRemoved != -1) {
            this.guests.remove(indexToBeRemoved);
        }
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
