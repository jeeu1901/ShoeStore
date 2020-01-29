package Model;

public class Order {
    private int id;
    private Customer customer;

    public Order(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public Order() {

    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }
}
