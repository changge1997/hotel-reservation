package model;

public class Tester {
    public static void main(String[] args) {
        /*
        //Customer customer = new Customer("first","second", "j@domain.com");
        //System.out.println(customer);

        //Customer customer = new Customer("first","second", "email");
        try {
            Customer customer = new Customer("first","second", "email");
            System.out.println(customer);
        }
        catch(IllegalArgumentException e) {
            System.out.println("Invalid email address!");
        }*/

        Room room = new Room("121", 89.99, RoomType.SINGLE );
        System.out.println(room);
    }
}
