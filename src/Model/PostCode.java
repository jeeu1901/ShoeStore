package Model;

public class PostCode {
    private int postNr;
    private int id;

    public PostCode(int postNr, int id) {
        this.postNr = postNr;
        this.id = id;
    }

    public PostCode() {

    }

    public int getPostNr() {
        return postNr;
    }
}
