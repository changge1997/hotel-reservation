package model;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    private final String emailRegex = "^[a-zA-Z]+@(.+).(.+)$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException{
        this.firstName = firstName;
        this.lastName = lastName;
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException();
        }else {
            this.email = email;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getEmailRegex() {
        return emailRegex;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object other) {
        if(other == this)
            return true;
        if(!(other instanceof Customer))
            return false;
        Customer o = (Customer)other;
        return o.firstName.equals(firstName) && o.lastName == lastName && o.email == email;
    }

    @Override
    public final int hashCode() {
        int result = 17;
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
    @Override
    public String toString() {
        return "Customer: " + this.firstName + " " + this.lastName + " [Email: " + this.email + "]";
    }
}
