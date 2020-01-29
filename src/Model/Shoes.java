package Model;

public class Shoes {
    private int id;
    private Price price;
    private Size size;
    private Label label;
    private Color color;
    private int amount;

    public Shoes(int id, Price price, Size size, Label label, Color color, int amount) {
        this.id = id;
        this.price = price;
        this.size = size;
        this.label = label;
        this.color = color;
        this.amount = amount;
    }

    public Shoes() {

    }

    public int getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public Size getSize() {
        return size;
    }

    public Label getLabel() {
        return label;
    }

    public Color getColor() {
        return color;
    }

    public int getAmount() {
        return amount;
    }

    public String printShoes() {
        return ". Märke: " + getLabel().getName() + " Färg: " + getColor().getName() + " Storlek: " + getSize().getShoeSize() +
                " Kostar: " + getPrice().getPrice() + " Lager antal: " + getAmount();
    }
}
