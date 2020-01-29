package Model;

public class Price {
    private int shoePrice;
    private int id;

    public int getPrice() {
        return shoePrice;
    }

    public Price(int id, int price) {
        this.id = id;
        this.shoePrice = price;
    }

    public Price() {

    }

}
