package pl.emilweglowski.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class SystemUtils {

    private final Properties props = new Properties();

    public static final String HOTEL_NAME = "Grand Hotel";
    public static String SYSTEM_VERSION;
    public static final boolean IS_DEVELOPER_VERSION = true;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final int HOTEL_NIGHT_START_HOUR = 15;
    public static final int HOTEL_NIGHT_START_MINUTE = 0;
    public static final int HOTEL_NIGHT_END_HOUR = 10;
    public static final int HOTEL_NIGHT_END_MINUTE = 0;

    public static final String SINGLE_BED = "Single";
    public static final String DOUBLE_BED = "Double";
    public static final String KING_SIZE = "King size";

    public static final Path DATA_DIRECTORY = Paths.get(System.getProperty("user.home"), "reservation_system");
    public static final String FEMALE = "Female";
    public static final String MALE = "Male";

    public static Connection connection;

    public static void createDataDirectory() throws IOException {
        if(!Files.isDirectory(DATA_DIRECTORY)) {
            Files.createDirectory(DATA_DIRECTORY);
        }
    }

    public SystemUtils() {
        try {
            this.props.load(this.getClass().getClassLoader().getResourceAsStream(".properties"));
            SystemUtils.SYSTEM_VERSION = props.get("system.version").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDataBaseConnection() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/reservationSystem", "test", "");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS ROOMS(ID INT PRIMARY KEY AUTO_INCREMENT, ROOM_NUMBER INT NOT NULL UNIQUE)");
            statement.execute("CREATE TABLE IF NOT EXISTS BEDS(ID INT PRIMARY KEY AUTO_INCREMENT, ROOM_ID INT NOT NULL, " +
                    "BED VARCHAR2(55), FOREIGN KEY (ROOM_ID) REFERENCES ROOMS(ID))");
            statement.execute("CREATE TABLE IF NOT EXISTS GUESTS(ID INT PRIMARY KEY AUTO_INCREMENT, " +
                    "FIRST_NAME VARCHAR2(100) NOT NULL, LAST_NAME VARCHAR2(100) NOT NULL, AGE INT NOT NULL, GENDER VARCHAR2(25) NOT NULL)");
            statement.execute("CREATE TABLE IF NOT EXISTS RESERVATIONS(ID INT PRIMARY KEY AUTO_INCREMENT, ROOM_ID INT NOT NULL," +
                    "GUEST_ID INT NOT NULL, RES_FROM SMALLDATETIME NOT NULL, RES_TO SMALLDATETIME NOT NULL," +
                    "FOREIGN KEY (ROOM_ID) REFERENCES ROOMS(ID), FOREIGN KEY (GUEST_ID) REFERENCES GUESTS(ID))");
            System.out.println("Connection with database created successfully");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error while creating database connection "+e.getMessage());
            e.printStackTrace();
        }
    }
}
