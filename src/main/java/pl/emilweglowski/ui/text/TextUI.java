package pl.emilweglowski.ui.text;

import pl.emilweglowski.domain.reservation.Reservation;
import pl.emilweglowski.domain.reservation.ReservationService;
import pl.emilweglowski.exceptions.OnlyNumberException;
import pl.emilweglowski.exceptions.PersistenceToFileException;
import pl.emilweglowski.exceptions.WrongOptionException;
import pl.emilweglowski.domain.guest.Guest;
import pl.emilweglowski.domain.guest.GuestService;
import pl.emilweglowski.domain.room.Room;
import pl.emilweglowski.domain.room.RoomService;
import pl.emilweglowski.util.Properties;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TextUI {

    private final GuestService guestService = new GuestService();
    private final RoomService roomService = new RoomService();
    private final ReservationService reservationService = new ReservationService();

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
            System.out.println("New guest added: " + newGuest.getInfo());
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use only numbers when choosing gender");
        }
    }

    private void readNewRoomData(Scanner input) {
        try {
            System.out.println("Add new room");
            System.out.println("Room number: ");
            int roomNumber = input.nextInt();
            int[] bedTypes = chooseBedType(input);
            Room newRoom = roomService.createNewRoom(roomNumber, bedTypes);
            System.out.println("New room added: " + newRoom.getInfo());
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

    public void showSystemInfo() {

        System.out.println("Welcome to the reservation system of " + Properties.HOTEL_NAME);
        System.out.println("Actual system version: " + Properties.SYSTEM_VERSION);
        System.out.println("Developer version: " + Properties.IS_DEVELOPER_VERSION);

        System.out.println("\n=============================\n");
    }

    public void showMainMenu() {

        System.out.println("Loading data...");
        this.guestService.readAll();
        this.roomService.readAll();

        Scanner input = new Scanner(System.in);

        try {
            performAction(input);
        } catch (WrongOptionException | OnlyNumberException | PersistenceToFileException e) {
            System.out.println("Unexpected error occurred");
            System.out.println("Error code: " + e.getCode());
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Unexpected error occurred");
            System.out.println("Unknown error code");
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void performAction(Scanner input) {

        int option = -1;

        while (option != 0) {

            option = getActionFromUser(input);

            if (option == 1) {
                readNewGuestData(input);
            } else if (option == 2) {
                readNewRoomData(input);
            } else if (option == 3) {
                showAllGuests();
            } else if (option == 4) {
                showAllRooms();
            } else if (option == 5) {
                removeGuest(input);
            } else if (option == 6) {
                editGuest(input);
            } else if (option == 7) {
                removeRoom(input);
            } else if (option == 8) {
                editRoom(input);
            } else if (option == 9) {
                createReservation(input);
            } else if (option == 0) {
                System.out.println("Closing application. Saving data.");
                this.guestService.saveAll();
                this.roomService.saveAll();
            } else {
                throw new WrongOptionException("Wrong option in main menu");
            }
        }
    }

    private void createReservation(Scanner input) {
        System.out.println("Check-in date (DD.MM.YYYY):");
        String fromAsString = input.next();
        LocalDate from = LocalDate.parse(fromAsString, Properties.DATE_FORMATTER);
        System.out.println("Check-out date (DD.MM.YYYY):");
        String toAsString = input.next();
        LocalDate to = LocalDate.parse(toAsString, Properties.DATE_FORMATTER);
        System.out.println("Enter room ID:");
        int roomId = input.nextInt();
        System.out.println("Enter guest ID:");
        int guestId = input.nextInt();

        //TODO: handle null reservation
        Reservation reservation = this.reservationService.createNewReservation(from, to, roomId, guestId);
        if (reservation!=null) {
            System.out.println("Reservation created successfully");
        }
    }

    private void editRoom(Scanner input) {
        System.out.println("Enter room ID to be edited");
        try {
            int id = input.nextInt();
            System.out.println("Room number: ");
            int roomNumber = input.nextInt();
            int[] bedTypes = chooseBedType(input);
            roomService.editRoom(id, roomNumber, bedTypes);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when editing room data");
        }
    }

    private void removeRoom(Scanner input) {
        System.out.println("Enter room ID to be removed");
        try {
            int id = input.nextInt();
            this.roomService.removeRoom(id);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when inserting ID");
        }
    }

    private void editGuest(Scanner input) {
        System.out.println("Enter guest ID to be edited");
        try {
            int id = input.nextInt();

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

            guestService.editGuest(id, firstName, lastName, age, isMale);

        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when editing guest data");
        }
    }

    private void removeGuest(Scanner input) {
        System.out.println("Enter guest ID to be removed");
        try {
            int id = input.nextInt();
            this.guestService.removeGuest(id);
        } catch (InputMismatchException e) {
            throw new OnlyNumberException("Use numbers when inserting ID");
        }
    }

    private void showAllRooms() {
        List<Room> rooms = this.roomService.getAllRooms();

        for (Room room : rooms) {
            System.out.println(room.getInfo());
        }
    }

    private void showAllGuests() {
        List<Guest> guests = this.guestService.getAllGuests();

        for (Guest guest : guests) {
            System.out.println(guest.getInfo());
        }
    }

    private static int getActionFromUser(Scanner in) {

        System.out.println("1 - Add new guest");
        System.out.println("2 - Add new room");
        System.out.println("3 - Show all guests");
        System.out.println("4 - Show all rooms");
        System.out.println("5 - Remove guest");
        System.out.println("6 - Edit guest data");
        System.out.println("7 - Remove room");
        System.out.println("8 - Edit room");
        System.out.println("9 - Create reservation");
        System.out.println("0 - Exit from the application");
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
