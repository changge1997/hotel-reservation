package api;

import model.Customer;
import model.Reservation;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Date;
import java.util.Set;

public class HotelResource {
    public static Customer getCustomer(String email) {
        return CustomerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName) {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    public static IRoom getRoom(String roomNumber) {
        return ReservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return ReservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public static Set<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = getCustomer(customerEmail);
        return ReservationService.getCustomerReservation(customer);
    }

    public static Set<IRoom> findARoom(Date checkIn, Date checkOut) {
        Set<IRoom> rooms = ReservationService.findRooms(checkIn, checkOut);
        return rooms;
    }

    public static Date addDaysToDate(Date date, int days) {
        return ReservationService.addDaysToDate(date, days);
    }

    public static Set<IRoom> findARoom(Date checkIn, Date checkOut, int days) {
        return ReservationService.findRooms(checkIn, checkOut, days);
    }
}
