import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String hotelName = "Grand Hotel";
        int systemVersion = 2;
        boolean isDeveloperVersion = true;

        showSystemInfo(hotelName, systemVersion, isDeveloperVersion);

        Scanner input = new Scanner(System.in);

        int option = getActionFromUser(input);

        if (option == 1) {
            Guest newGuest = createNewGuest(input);
        } else if (option == 2) {
            Room newRoom = createNewRoom(input);
        } else if (option == 3) {
            System.out.println("Option 3 chosen");
        } else {
            System.out.println("Incorrect action");
        }
    }

    private static void showSystemInfo(String hotelName, int systemVersion, boolean isDeveloperVersion) {

        System.out.println("Welcome to the reservation system of "+hotelName);
        System.out.println("Actual system version: "+systemVersion);
        System.out.println("Developer version: "+isDeveloperVersion);

        System.out.println("\n=============================\n");
    }

    private static int getActionFromUser(Scanner in) {

        System.out.println("1. Add new guest");
        System.out.println("2. Add new room");
        System.out.println("3. Search for a guest");
        System.out.println("Choose option: ");

        int actionNumber = 0;

        try {
            actionNumber = in.nextInt();
        } catch (Exception e) {
            System.out.println("Incorrect input data, choose number");
            e.printStackTrace();
        }
        return actionNumber;
    }

    private static Guest createNewGuest(Scanner input) {
        try {
            System.out.println("Add new guest");
            System.out.print("First name: ");
            String firstName = input.next();
            System.out.print("Last name: ");
            String lastName = input.next();
            System.out.print("Age: ");
            int age = input.nextInt();
            Guest newGuest = new Guest(firstName, lastName, age);
            System.out.println(newGuest.getInfo());
            return newGuest;
        } catch (Exception e) {
            System.out.println("New guest adding failed: age has to be number");
            e.printStackTrace();
            return null;
        }
    }

    private static Room createNewRoom(Scanner input) {
        try {
            System.out.println("Add new room");
            System.out.print("Room number: ");
            int roomNumber = input.nextInt();
            System.out.print("Number of beds: ");
            int numberOfBeds = input.nextInt();
            Room newRoom = new Room(roomNumber, numberOfBeds);
            System.out.println(newRoom.getInfo());
            return newRoom;
        } catch (Exception e) {
            System.out.println("Wrong digits, numbers only");
            e.printStackTrace();
            return null;
        }
    }
}