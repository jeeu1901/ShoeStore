package Model;

public class Size {
    private int id;
    private int ShoeSize;

    public Size() {

    }

    public int getShoeSize() {
        return ShoeSize;
    }

    public Size(int id, int shoeSize) {
        this.id = id;
        ShoeSize = shoeSize;
    }
}
