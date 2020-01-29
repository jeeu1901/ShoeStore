package Model;

public class Address {
    private int id;
    private String addressName;
    private int adressNr;
    private City city;
    private PostCode postcode;

    public Address(int id, String addressName, int adressNr, City city, PostCode postcode) {
        this.id = id;
        this.addressName = addressName;
        this.adressNr = adressNr;
        this.city = city;
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public String getAddressName() {
        return addressName;
    }

    public int getAdressNr() {
        return adressNr;
    }

    public City getCity() {
        return city;
    }

    public PostCode getPostcode() {
        return postcode;
    }
}
