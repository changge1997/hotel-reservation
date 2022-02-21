package service;

import model.*;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class ReservationService {
    private static final Map<String, IRoom> mapOfRooms = new HashMap<>();
    private static final Set<Reservation> setOfReservation =  new HashSet<>();
    private static ReservationService instance;

    private ReservationService () {}

    static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public static void readRoomsFromFile(String fileName) throws IOException {
        File f = new File(fileName);
        Scanner input = new Scanner(f);
        //Scanner scanner = new Scanner(Paths.get(fileName), StandardCharsets.UTF_8.name());
        while(input.hasNext()) {
            String data = input.nextLine();
            if(data.isEmpty()) {
                throw new IOException();
            }
            String[] inputData = data.split(" ");
            IRoom room = new Room(inputData[0], Double.parseDouble(inputData[1].trim()), RoomType.StringToEnum(inputData[2]));
            mapOfRooms.put(room.getRoomNumber(), room);
        }
        input.close();
    }

    public static void addRoom(IRoom room) {
        mapOfRooms.put(room.getRoomNumber(),room);
    }

    public static IRoom getARoom(String roomId) {
        return mapOfRooms.get(roomId);
    }

    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reserve = new Reservation(customer, room, checkInDate, checkOutDate);
        setOfReservation.add(reserve);
        return reserve;
    }

    public static Set<IRoom> getAllRooms() throws NullPointerException{
        Set<IRoom> setOfRooms = new HashSet<>();
        if(mapOfRooms.isEmpty()) {
            throw new NullPointerException();
        }
        for(String roomNumber: mapOfRooms.keySet()) {
            setOfRooms.add(mapOfRooms.get(roomNumber));
        }
        return setOfRooms;
    }

    public static Set<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> availableRooms = new HashSet<>(mapOfRooms.values());
        for(Reservation reservation: setOfReservation) {
            if(!(checkInDate.compareTo(reservation.getCheckOutDate()) >= 0 ||
                    checkOutDate.compareTo(reservation.getCheckInDate()) <= 0)) {
                availableRooms.remove(reservation.getRoom());}
        }
        return availableRooms;
    }

     public static Date addDaysToDate(Date date, int days) {
        Calendar addDays = Calendar.getInstance();
        addDays.setTime(date);
        addDays.add(Calendar.DAY_OF_MONTH, days);
        Date newDate = addDays.getTime();
        return newDate;
        }

    public static Set<IRoom> findRooms(Date checkInDate, Date checkOutDate, int days) {
        Date newCheckInDate = addDaysToDate(checkInDate, days);
        Date newCheckOutDate = addDaysToDate(checkOutDate, days);

        Set<IRoom> availableRooms = new HashSet<>(mapOfRooms.values());
        for(Reservation reservation: setOfReservation) {
            if(!(newCheckInDate.compareTo(reservation.getCheckOutDate()) >= 0 ||
               newCheckOutDate.compareTo(reservation.getCheckInDate()) <= 0 )) {
                    availableRooms.remove(reservation.getRoom());}
        }
        return availableRooms;
    }

    public static Set<Reservation> getCustomerReservation(Customer customer) throws NullPointerException{
        Set<Reservation> setOfCustomerReservation = new HashSet<>();
        if(setOfReservation.isEmpty()) {
            throw new NullPointerException();
        }
        for(Reservation reservation: setOfReservation) {
            if(reservation.getCustomer().equals(customer)) {
                setOfCustomerReservation.add(reservation);
            }
        }
        return setOfCustomerReservation;
        }

    public static Set<Reservation> getAllReservation() throws NullPointerException {
        if(mapOfRooms.isEmpty()) {
            throw new NullPointerException();
        }
        return setOfReservation;
    }
    }





