package Model;

public class OrderItem {
    private int id;
    private String date;
    private Shoes shoes;
    private Order order;
    private int antal;

    public OrderItem(int id, String date, Shoes shoes, Order order, int antal) {
        this.id = id;
        this.date = date;
        this.shoes = shoes;
        this.order = order;
        this.antal = antal;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public Order getOrder() {
        return order;
    }

    public int getAntal() {
        return antal;
    }
}
