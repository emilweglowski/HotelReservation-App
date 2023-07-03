package pl.emilweglowski.ui.text;

import pl.emilweglowski.exceptions.OnlyNumberException;
import pl.emilweglowski.exceptions.WrongOptionException;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.room.Room;
import pl.emilweglowski.domain.room.RoomService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TextUI {

    private final GuestService guestService = new GuestService();
    private final RoomService roomService = new RoomService();

    private void readNewGuestData(Scanner input) {
        try {
            System.out.println("Add new guest");
            System.out.print("First name: ");
            String firstName = input.next();
            System.out.print("Last name: ");
            String lastName = input.next();
            System.out.print("Age: ");
            int age = input.nextInt();
            System.out.println("Choose gender: ");
            System.out.println("\t1. male");
            System.out.println("\t2. female");

            int genderOption = input.nextInt();

            if (genderOption != 1 && genderOption != 2) {
                throw new WrongOptionException("Wrong option in gender selection");
            }

            boolean isMale = false;

            if (genderOption == 1) {
                isMale = true;
            }

            Guest newGuest = guestService.createNewGuest(firstName, lastName, age, isMale);
            System.out.println(newGuest.getInfo());
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when choosing gender");
        }
    }

    private void readNewRoomData(Scanner input) {
        try {
            System.out.println("Add new room");
            System.out.println("pl.emilweglowski.domain.room.Room number: ");
            int roomNumber = input.nextInt();
            int[] bedTypes = chooseBedType(input);
            Room newRoom = roomService.createNewRoom(roomNumber, bedTypes);
            System.out.println(newRoom.getInfo());
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when creating new room");
        }
    }

    private int[] chooseBedType(Scanner input) {
        System.out.println("How many beds in this room?");
        int bedsNumber = input.nextInt();

        int[] bedTypes = new int[bedsNumber];

        for (int i = 0; i < bedsNumber; i++) {
            System.out.println("Choose bed type:");
            System.out.println("\t1. Single");
            System.out.println("\t2. Double");
            System.out.println("\t3. King size");

            int bedTypeOption = input.nextInt();

            bedTypes[i] = bedTypeOption;
        }

        return bedTypes;
    }

    public void showSystemInfo(String hotelName, int systemVersion, boolean isDeveloperVersion) {

        System.out.println("Welcome to the reservation system of " + hotelName);
        System.out.println("Actual system version: " + systemVersion);
        System.out.println("Developer version: " + isDeveloperVersion);

        System.out.println("\n=============================\n");
    }

    public void showMainMenu() {

        Scanner input = new Scanner(System.in);

        try {
            performAction(input);
        } catch (WrongOptionException | OnlyNumberException e) {
            System.out.println("Unexpected error occurred");
            System.out.println("Error code: " + e.getCode());
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error occurred");
            System.out.println("Unknown error code");
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Closing application");
        }
    }

    private void performAction(Scanner input) {
        int option = getActionFromUser(input);

        if (option == 1) {
            readNewGuestData(input);
        } else if (option == 2) {
            readNewRoomData(input);
        } else if (option == 3) {
            System.out.println("Option 3 chosen");
        } else {
            throw new WrongOptionException("Wrong option in main menu");
        }
    }

    private static int getActionFromUser(Scanner in) {

        System.out.println("1. Add new guest");
        System.out.println("2. Add new room");
        System.out.println("3. Search for a guest");
        System.out.println("Choose option: ");

        int actionNumber;

        try {
            actionNumber = in.nextInt();
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers in main menu");
        }
        return actionNumber;
    }
}
