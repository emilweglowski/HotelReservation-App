import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        String hotelName = "Grand Hotel";
        int systemVersion = 2;
        boolean isDeveloperVersion = true;

        showSystemInfo(hotelName, systemVersion, isDeveloperVersion);

        Scanner input = new Scanner(System.in);

        try {
            performAction(input);
        } catch (WrongOptionException | OnlyNumberException e) {
            System.out.println("Unexpected error occurred");
            System.out.println("Error code: "+e.getCode());
            System.out.println("Error message: "+e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error occurred");
            System.out.println("Unknown error code");
            System.out.println("Error message: "+e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Closing application");
        }
    }

    private static void performAction(Scanner input) {
        int option = getActionFromUser(input);

        if (option == 1) {
            Guest newGuest = createNewGuest(input);
        } else if (option == 2) {
            Room newRoom = createNewRoom(input);
        } else if (option == 3) {
            System.out.println("Option 3 chosen");
        } else {
            throw new WrongOptionException("Wrong option in main menu");
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
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers in main menu");
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
            Gender gender = chooseGender(input);
            Guest newGuest = new Guest(firstName, lastName, age, gender);
            System.out.println(newGuest.getInfo());
            return newGuest;
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when choosing gender");
        }
    }

    private static Gender chooseGender(Scanner input) {
        System.out.println("Choose gender: ");
        System.out.println("\t1. male");
        System.out.println("\t2. female");
        Gender gender = Gender.MALE;
        int genderType = input.nextInt();
        if (genderType == 1) {
            gender = Gender.MALE;
        } else if (genderType == 2) {
            gender = Gender.FEMALE;
        } else {
            throw new WrongOptionException("Wrong option in gender selection");
        }
        return gender;
    }

    private static Room createNewRoom(Scanner input) {
        try {
            System.out.println("Add new room");
            System.out.println("Room number: ");
            int roomNumber = input.nextInt();
            BedType[] bedType = chooseBedType(input);
            Room newRoom = new Room(roomNumber, bedType);
            System.out.println(newRoom.getInfo());
            return newRoom;
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when creating new room");
        }
    }

    private static BedType[] chooseBedType(Scanner input) {
        System.out.println("How many beds in this room?");
        int bedsNumber = input.nextInt();
        BedType[] bedTypes = new BedType[bedsNumber];

        for (int i=0; i<bedsNumber; i++) {
            System.out.println("Choose bed type: ");
            System.out.println("\t1. Single");
            System.out.println("\t2. Double");
            System.out.println("\t3. King size");

            BedType bedType = BedType.SINGLE;
            int bedTypeOption = input.nextInt();

            if (bedTypeOption == 1) {
                bedType = BedType.SINGLE;
            } else if (bedTypeOption == 2) {
                bedType = BedType.DOUBLE;
            } else if (bedTypeOption == 3) {
                bedType = BedType.KING_SIZE;
            } else {
                throw new WrongOptionException("Wrong option when selecting bed type");
            }

            bedTypes[i] = bedType;
        }
        return bedTypes;
    }
}