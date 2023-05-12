import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String hotelName = "Grand Hotel";
        int systemVersion = 2;
        boolean isDeveloperVersion = true;

        System.out.print("Welcome to the reservation system of "+hotelName);
        System.out.print("Actual system version: "+systemVersion);
        System.out.print("Developer version: "+isDeveloperVersion);

        System.out.println("\n=============================\n");
        Scanner input = new Scanner(System.in);

        System.out.println("1. Add new guest");
        System.out.println("2. Add new room");
        System.out.println("3. Search for a guest");
        System.out.println("Choose option: ");

        int option = 0;

        try {
            option = input.nextInt();
        } catch (Exception e) {
            System.out.println("Incorrect input data, choose number");
            e.printStackTrace();
        }

        if (option == 1) {
            System.out.println("Add new guest");
            try {
                System.out.print("First name: ");
                String firstName = input.next();
                System.out.print("Last name: ");
                String lastName = input.next();
                System.out.print("Age: ");
                int age = input.nextInt();
                Guest newGuest = new Guest(firstName, lastName, age);
                String guestInfo = String.format("New guest added: %s %s (%d)", newGuest.firstName, newGuest.lastName, newGuest.age);
                System.out.println(guestInfo);
            } catch (Exception e) {
                System.out.println("Wrong digits - numbers only");
                e.printStackTrace();
            }
        } else if (option == 2) {
            System.out.println("Add new room");
            try {
                System.out.print("Room number: ");
                int roomNumber = input.nextInt();
                System.out.print("Number of beds: ");
                int numberOfBeds = input.nextInt();
                Room newRoom = new Room(roomNumber, numberOfBeds);
                String roomInfo = String.format("New room number %d added, number of beds: %d", newRoom.roomNumber, newRoom.numberOfBeds);
                System.out.println(roomInfo);
            } catch (Exception e) {
                System.out.println("Wrong digits, numbers only");
                e.printStackTrace();
            }
        } else if (option == 3) {
            System.out.println("Option 3 chosen");
        } else {
            System.out.println("Incorrect action");
        }
    }
}
