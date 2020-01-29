package Model;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private Address address;

    public Customer(int id, String firstName, String lastName, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Customer() {

    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String printWelcome() {
        return "Välkommen " + getFirstName() + " " + getLastName();
    }
}
