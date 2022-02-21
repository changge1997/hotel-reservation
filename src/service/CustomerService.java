package service;

import model.Customer;

import java.io.*;
import java.util.*;

public class CustomerService {
    private static final Map<String, Customer> mapOfCustomers = new HashMap<>();
    private static CustomerService instance;

    private CustomerService () {}

    static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    public static void readCustomerSFromFile(String fileName) throws IOException {
        File f = new File(fileName);
        Scanner input = new Scanner(f);
        while(input.hasNext()) {
            String data = input.nextLine();
            if(data.isEmpty()) {
                throw new IOException();
            }
            String[] inputData = data.split(" ");
            Customer customer = new Customer(inputData[0], inputData[1], inputData[2]);
            mapOfCustomers.put(customer.getEmail(), customer);
        }
        input.close();
    }

    public static void addCustomer(String email, String firstName, String lastName) {
        mapOfCustomers.put(email, new Customer(firstName, lastName, email));
    }

    public static Customer getCustomer(String customerEmail) {
        return mapOfCustomers.get(customerEmail);
    }

    public static Set<Customer> getAllCustomers() throws NullPointerException{
        Set<Customer> setOfCustomers = new HashSet<>();
        if(mapOfCustomers.isEmpty()) {
            throw new NullPointerException();
        }
        for(String email: mapOfCustomers.keySet()) {
            setOfCustomers.add(mapOfCustomers.get(email));
        }
        return setOfCustomers;
    }

}
