package ui;

import api.AdminResources;
import model.Room;
import model.RoomType;


import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Set;

public class AdminMenu {
    public static void adminMenu(Scanner scanner) throws ParseException, IOException {
        int selection = 0;
        do {
            printAdminMenu();
            selection = MainMenu.validInt(scanner);
            switch(selection) {
                case 1:
                    seeAllCustomers();
                    break;
                case 2:
                    seeAllRooms();
                    break;
                case 3:
                    seeAllReservation();
                    break;
                case 4:
                    addARoom(scanner);
                    break;
                case 5:
                    readFromFile(scanner);
                    break;
                case 6:
                    MainMenu.mainMenu();
                    break;
                default:
                    System.out.println("Sorry, please enter number 1-6 for the menu option: ");
            }
        }while(selection != 6);
    }

    public static void printAdminMenu() {
        System.out.println("\nAdmin Menu");
        System.out.println("-----------------------------------------------------");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Get data from files");
        System.out.println("6. Back to Main Menu");
        System.out.println("-----------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }


    public static void seeAllCustomers() {
        try {
            printSet(AdminResources.getAllCustomers());
        }
        catch (NullPointerException e) {
            System.out.println("Sorry, there are no customers in our system.");
        }
    }

    public static void seeAllRooms() {
        try {
            printSet(AdminResources.getAllRooms());
        }
        catch (NullPointerException e) {
            System.out.println("Sorry, there are no rooms in our system.");
        }
    }

    public static void seeAllReservation() {
        try {
            printSet(AdminResources.getAllReservations());
        }
        catch (NullPointerException e) {
            System.out.println("Sorry, there are no reservations in our system.");
        }
    }

    public static void addARoom(Scanner scanner) {
        System.out.println("Enter a room number");
        String roomNumber = scanner.nextLine();
        System.out.println("Enter price per night");
        Double price = validDouble(scanner);
        System.out.println("Enter room type: 1 for single bed, 2 for double bed");
        RoomType roomType = validRoomType(scanner);
        AdminResources.addRoom(new Room(roomNumber, price, roomType));
        System.out.println("Would you like to add another room? y/n");
        String addAnotherRoom = MainMenu.validYorN(scanner);
        if(addAnotherRoom.equals("y")) {
            addARoom(scanner);
        }else {
            System.out.println("Thank you for using this system. Have a nice day.");
        }
    }

    public static RoomType validRoomType(Scanner scanner) {
        String input = scanner.nextLine();
        while(!input.equals("1") && !input.equals("2")) {
            System.out.println("Invalid input! Please re-enter your selection(1 or 2)");
            input = scanner.nextLine();
        }
        if(input.equals("1")) {
            return RoomType.SINGLE;
        }
        if(input.equals("2")) {
            return RoomType.DOUBLE;
        }
        return null;
    }

    public static double validDouble(Scanner scanner) throws NumberFormatException {
        String input = scanner.nextLine();
        try{
            double value = Double.parseDouble(input.trim());
            return value;
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter a valid price value");
            return validDouble(scanner);
        }
    }

    public static void readFromFile(Scanner scanner) throws IOException{
        System.out.println("Enter the file for customers");//"testData/customerInfo.txt"
        try {
            String customerInfo = scanner.nextLine();
            AdminResources.addCustomersFromFile(customerInfo);
        }

        catch (IOException e) {
            System.out.println("Fail to get customer information!");
        }
        System.out.println("Enter the file for rooms");//"testData/roomInfo.txt"
        try {
            String roomInfo = scanner.nextLine();
            AdminResources.addRoomsFromFile(roomInfo);
        }
        catch (IOException e) {
            System.out.println("Fail to get room information!");
        }
        System.out.println("Information has been added successfully!");
    }

    public static <T> void printSet(Set<T> setToShow) {
        for(T item: setToShow) {
            System.out.println(item);
        }
    }

}
