package ui;

import api.AdminResources;
import api.HotelResource;
import model.IRoom;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Set;


public class MainMenu {
    public static void mainMenu() throws ParseException, IOException {
        Scanner scanner = new Scanner(System.in);
        int selection = 0;
        do {
            printMainMenu();
            selection = validInt(scanner);
            switch(selection) {
                case 1:
                    System.out.println("Enter CheckIn Date(mm/dd/yyyy)");
                    Date checkIn = validDate(scanner);
                    System.out.println("Enter CheckOut Date(mm/dd/yyyy)");
                    Date checkOut = validDate(scanner);;

                    findAndReserveARoom(scanner, checkIn, checkOut);
                    break;
                case 2:
                    seeAReservation(scanner);
                    break;
                case 3:
                    createAnAccount(scanner);
                    break;
                case 4:
                    enterAdminMenu(scanner);
                    break;
                case 5:
                    writeToFile();
                    System.out.println("Thank you for visiting our hotel. See you next time.");
                    break;
                default:
                    System.out.println("Sorry, please enter number 1-5 for the menu option: ");
            }
        }while(selection != 5);
    }

    public static void printMainMenu() {
        System.out.println("\nWelcome to the Hotel Reservation Application");
        System.out.println("-----------------------------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservation");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("-----------------------------------------------------");
        System.out.println("Please select a number for the menu option");
    }

    public static void findAndReserveARoom(Scanner scanner, Date checkIn, Date checkOut) {
        if(HotelResource.findARoom(checkIn, checkOut).isEmpty()) {
            System.out.println("Sorry, we don't have any rooms available on these dates. " +
                    "Would you like to check for a later date? y/n");
            String checkLater = validYorN(scanner);
            if(checkLater.equals("y")) {
                System.out.println("Please enter the amount of days out you want to search.");
                int days = validInt(scanner);
                if(HotelResource.findARoom(checkIn, checkOut, days).isEmpty()) {
                    System.out.println("Sorry, we don't have any rooms available on these dates.");
                }else {
                    Set<IRoom> availableRooms = HotelResource.findARoom(checkIn, checkOut, days);
                    AdminMenu.printSet(availableRooms);
                    Date newCheckIn = HotelResource.addDaysToDate(checkIn, days);
                    Date newCheckOut = HotelResource.addDaysToDate(checkOut, days);
                    reserveARoom(scanner, availableRooms, newCheckIn, newCheckOut);
                }
            }else {
                System.out.println("Thank you for visiting our hotel.See you next time.");
            }
        }else {
            Set<IRoom> availableRooms = HotelResource.findARoom(checkIn, checkOut);
            AdminMenu.printSet(availableRooms);
            reserveARoom(scanner, availableRooms, checkIn, checkOut);
        }
    }


    public static Date validDate(Scanner scanner) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        format.setLenient(false);

        try{
            String sDate = scanner.nextLine();
            Date date = format.parse(sDate);
            return date;
        } catch(ParseException e) {
            System.out.println("Please enter a valid date(mm/dd/yyyy)");
            return validDate(scanner);
        }
    }

    public static String validYorN(Scanner scanner) {
        String input = scanner.nextLine();
        while(!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid input! Please re-enter your selection(y/n)");
            input = scanner.nextLine();
        }
        return input;
    }

    public static int validInt(Scanner scanner) throws NumberFormatException {
        String input = scanner.nextLine();
        try{
            int number = Integer.parseInt(input.trim());
            return number;
        }
        catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
            return validInt(scanner);
        }
    }

    public static void reserveARoom(Scanner scanner, Set<IRoom> roomList, Date checkIn, Date checkOut) {
        System.out.println("Would you like to book a room? y/n");
        String bookRoom = validYorN(scanner);
        if(bookRoom.equals("y")) {
            System.out.println("Do you have an account with us? y/n");
            String haveAccount = validYorN(scanner);
            if(haveAccount.equals("y")) {
                System.out.println("Enter Email(name@domain.com)");
                String email = validAccount(scanner);
                System.out.println("What room number would you like to reserve?");
                String roomNumber = validRoom(scanner, roomList);
                System.out.println("Your reservation has been completed :)");
                System.out.println(HotelResource.bookARoom(email, HotelResource.getRoom(roomNumber), checkIn, checkOut));
            }else {
                String email = createAnAccount(scanner);
                System.out.println("What room number would you like to reserve?");
                String roomNumber = validRoom(scanner, roomList);
                System.out.println("Your reservation has been completed :)");
                System.out.println(HotelResource.bookARoom(email, HotelResource.getRoom(roomNumber), checkIn, checkOut));
            }
        }else {
            System.out.println("Thank you for visiting our hotel.See you next time.");
        }
    }

    public static String validAccount(Scanner scanner) {
        String email = scanner.nextLine();
        while(AdminResources.getCustomer(email) == null) {
            System.out.println("Sorry we cannot find this account. Please enter again.");
            email = scanner.nextLine();
        }
        return email;
    }

    public static String validRoom(Scanner scanner, Set<IRoom> roomList) {
        String roomNumber = scanner.nextLine();
        boolean isValid = false;
        for(IRoom room: roomList) {
            if(room.getRoomNumber().equals(roomNumber)) {
                isValid = true;
            }
        }
        if(!isValid) {
            System.out.println("This room number is invalid. Please enter a room number again.");
            return validRoom(scanner,roomList);
        }else {
            return roomNumber;
        }
    }

    public static void seeAReservation(Scanner scanner) {
        System.out.println("Please enter your email(name@domain.com)");
        String email = scanner.nextLine();
        System.out.println("These are your reservation details.");
        System.out.println(HotelResource.getCustomersReservations(email));
    }

    public static String createAnAccount(Scanner scanner) {
        System.out.println("Please enter an email address(name@domain.com) to create an account.");
        String email = validEmailAddress(scanner);
        System.out.println("Please enter your first name");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name");
        String lastName = scanner.nextLine();
        AdminResources.addACustomer(email, firstName, lastName);
        System.out.println("Congratulations! You have created an account successfully :)");
        return email;
    }

    public static String validEmailAddress(Scanner scanner) {
        String email = scanner.nextLine();
        try {
            AdminResources.addACustomer(email, "test", "test");
        }
        catch(IllegalArgumentException e) {
            System.out.println("Invalid email address! Please enter the email again.");
             return validEmailAddress(scanner);
        }
        return email;
    }

    public static void enterAdminMenu(Scanner scanner) throws ParseException, IOException {
        System.out.println("Please enter the password to access the admin menu");
        String password = scanner.nextLine();
        if(password.equals("administrator")) {
            AdminMenu.adminMenu(scanner);
        }else {
            System.out.println("Sorry, the password is wrong. Please try again later.\n");
        }
    }

    public static <T> void writeSet(FileWriter output, Set<T> setToWrite) throws IOException {
        for(T item: setToWrite) {
            output.write(item.toString());
            output.close();
        }
    }
    public static void writeToFile() throws IOException {
        try{
            FileWriter outCustomer = new FileWriter("testResult/CustomerList.txt");
            writeSet(outCustomer, AdminResources.getAllCustomers());
        }
        catch(NullPointerException e) {
            FileWriter outCustomer = new FileWriter("testResult/CustomerList.txt");
            outCustomer.write("Sorry, there are no customers in our system.");
            outCustomer.close();
        }

        try{
            FileWriter outRoom = new FileWriter("testResult/RoomList.txt");
            writeSet(outRoom, AdminResources.getAllRooms());
        }
        catch(NullPointerException e) {
            FileWriter outRoom = new FileWriter("testResult/RoomList.txt");
            outRoom.write("Sorry, there are no rooms in our system.");
            outRoom.close();
        }

        try{
            FileWriter outReservation = new FileWriter("testResult/ReservationList.txt");
            writeSet(outReservation, AdminResources.getAllReservations());
        }
        catch(NullPointerException e) {
            FileWriter outReservation = new FileWriter("testResult/ReservationList.txt");
            outReservation.write("Sorry, there are no reservations in our system.");
            outReservation.close();
        }
    }
}




