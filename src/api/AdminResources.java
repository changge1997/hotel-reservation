package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.io.IOException;
import java.util.Set;

public class AdminResources {

    public static void addACustomer(String email, String firstName, String lastName) {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    public static void addCustomersFromFile(String fileName) throws IOException {
            CustomerService.readCustomerSFromFile(fileName);
    }

    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    public static void addRoom(IRoom room) {
            ReservationService.addRoom(room);
    }

    public static void addRoomsFromFile(String fileName) throws IOException {
        ReservationService.readRoomsFromFile(fileName);
    }

    public static Set<IRoom> getAllRooms() {
        return ReservationService.getAllRooms();
    }

    public static Set<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    public static Set<Reservation> getAllReservations() {
        return ReservationService.getAllReservation();
    }
}
