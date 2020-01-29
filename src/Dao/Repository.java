package Dao;

import Model.*;
import util.ConDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private Connection con;

    public Repository() {
        con = ConDatabase.getConnection();
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "Select kategorier.id, kategorier.kategori_namn from skodatabas.kategorier;";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                categories.add(new Category(rs.getInt("id"), rs.getString("kategori_namn")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Shoes> getShoesByCategory(int categoryId) {
        List<Shoes> shoeByCategoryList = new ArrayList<>();
        String query = "select tillhör.sko from skodatabas.tillhör " +
                "where tillhör.kategori = ? ;";
        Shoes shoes = new Shoes();
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                shoes = getShoe(rs.getInt("sko"));
                shoeByCategoryList.add(shoes);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoeByCategoryList;
    }


//    public List<Shoes> getShoesByCategory(int categoryId) {
//        String query = "Select skodatabas.skor.id, skodatabas.skor.pris, skodatabas.skor.färg," +
//                "skodatabas.skor.storlek, skodatabas.skor.märke, skodatabas.skor.antal_lager " +
//                "from skodatabas.skor where skodatabas.skor.id = ? ;";
//        List<Shoes> shoeList = new ArrayList<>();
//
//        Color color = getColor(category);
//        Size size = getSize(shoeId);
//        Price price = getPrice(shoeId);
//        Label label = getLabel(shoeId);
//        Shoes shoes = new Shoes();
//
//        try (PreparedStatement stmt = con.prepareStatement(query)) {
//            stmt.setInt(1, shoeId);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                shoes = new Shoes(rs.getInt("id"), price, size, label, color,
//                        rs.getInt("antal_lager"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return shoeList;
//    }

    public boolean addOrder(int shoeId, int orderId , int custId) {
        int checkOrder;
        boolean checker = true;
        try(CallableStatement stmt = con.prepareCall("CALL skodatabas.AddToCart(?, ?, ?)")) {
            stmt.setInt(1, custId);
            if(orderId == 0) {
                stmt.setString(2, null);
            }
            else {
                stmt.setInt(2, orderId);
            }
            stmt.setInt(3, shoeId);
            checkOrder = stmt.executeUpdate();
            if(checkOrder > 0) {
                checker = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checker;
    }

    public Order getOrder(int custId) {
        Order order = new Order();
        String query = "Select beställning.id, beställning.kund from skodatabas.beställning" +
                " where kund = ?";

        try(PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, custId);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                order = new Order(rs.getInt("id"), getCustomer(custId));
            }
            else {
                order = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<Shoes> getOrderItems(int custId) {
        String query = "Select beställ_item.id, beställ_item.datum, beställ_item.antal, beställ_item.sko from skodatabas.beställ_item" +
                " inner join skodatabas.beställning on beställ_item.beställning = beställning.id " +
                " inner join skodatabas.kunder on beställning.kund = kunder.id" +
                " where beställning = ? ;";
        OrderItem orderItem = new OrderItem();
        List<Shoes> orderShoes = new ArrayList<>();
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, custId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                    Shoes shoes = getShoe(rs.getInt("sko"));
                    orderShoes.add(shoes);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderShoes;
    }

    // Tar alla skor från min vy "allShoes" i databasen
    public List<Shoes> getAllShoes() {
        List<Shoes> allShoes = new ArrayList<>();
        List<Integer> allId = new ArrayList<>();
        String query = "select id from skodatabas.skor";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                allId.add(rs.getInt("id"));
//                allShoes.add(new Shoes(rs.getInt("shoeId"), rs.getString("märke_namn"),
//                        rs.getString("färg_namn"), rs.getInt("pris_summa"),
//                        rs.getInt("sko_storlek"), rs.getInt("antal_lager")));
            }
            allShoes = allId.stream().map(this::getShoe).collect(Collectors.toList());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allShoes;
    }

    public Shoes getShoe(int shoeId) {
        String query = "Select skodatabas.skor.id, skodatabas.skor.pris, skodatabas.skor.färg," +
                "skodatabas.skor.storlek, skodatabas.skor.märke, skodatabas.skor.antal_lager " +
                "from skodatabas.skor where skodatabas.skor.id = ? ;";
        Color color = getColor(shoeId);
        Size size = getSize(shoeId);
        Price price = getPrice(shoeId);
        Label label = getLabel(shoeId);
        Shoes shoes = new Shoes();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, shoeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                shoes = new Shoes(rs.getInt("id"), price, size, label, color,
                        rs.getInt("antal_lager"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoes;
    }


    public Color getColor(int shoeId) {
        String query = "select skodatabas.färger.id, skodatabas.färger.färg_namn from skodatabas.färger " +
                "inner join skodatabas.skor on färger.id = skor.färg" +
                " where skor.id = ? ;";
        Color color = new Color();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, shoeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                color = new Color(rs.getInt("id"), rs.getString("färg_namn"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return color;
    }

    public Label getLabel(int shoeId) {
        String query = "select skodatabas.märken.id, skodatabas.märken.märke_namn from skodatabas.märken " +
                "inner join skodatabas.skor on märken.id = skor.märke" +
                " where skor.id = ? ;";
        Label label = new Label();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, shoeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                label = new Label(rs.getInt("id"), rs.getString("märke_namn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return label;
    }

    public Price getPrice(int shoeId) {
        String query = "select skodatabas.pris.id, skodatabas.pris.pris_summa from skodatabas.pris " +
                "inner join skodatabas.skor on pris.id = skor.pris" +
                " where skor.id = ? ;";
        Price price = new Price();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, shoeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                price = new Price(rs.getInt("id"), rs.getInt("pris_summa"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public Size getSize(int shoeId) {
        String query = "select skodatabas.storlekar.id, skodatabas.storlekar.sko_storlek" +
                " from skodatabas.storlekar inner join skodatabas.skor on storlekar.id = skor.storlek" +
                " where skor.id = ? ;";
        Size size = new Size();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, shoeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                size = new Size(rs.getInt("id"), rs.getInt("sko_storlek"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return size;
    }

    public Customer credDatabase(String user, String pw) {
        String query = "Select skodatabas.kunder.id, skodatabas.kunder.förnamn, skodatabas.kunder.lösenord" +
                " from skodatabas.kunder where förnamn = ? and lösenord = ? ;";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, user);
            stmt.setString(2, pw);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return getCustomer(rs.getInt("id"));
            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Customer getCustomer(int custId) {
        String query = "Select skodatabas.kunder.id, skodatabas.kunder.förnamn, skodatabas.kunder.efternamn, skodatabas.kunder.adress " +
                "from skodatabas.kunder where id = ? ;";
        Address address = getAddress(custId);
        Customer customer = new Customer();

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, custId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                customer = new Customer(rs.getInt("id"), rs.getString("förnamn"),
                        rs.getString("efternamn"), address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Address getAddress(int custId) {
        String query = "Select adresser.id, adresser.adress_namn, adresser.adress_nr, adresser.postkod," +
                "adresser.ort " +
                "from skodatabas.adresser inner join skodatabas.kunder on adresser.id = kunder.adress" +
                " where kunder.id = ? ;";
        PostCode postCode = getPostcode(custId);
        City city = getCity(custId);

        try {
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, custId);
            ResultSet rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PostCode getPostcode(int custId) {
        String query = "select skodatabas.postkoder.id, skodatabas.postkoder.postnummer from skodatabas.postkoder" +
                " inner join skodatabas.adresser on postkoder.id = adresser.postkod " +
                "inner join skodatabas.kunder on adresser.id = kunder.adress" +
                " where kunder.id = ? ;";
        PostCode postCode = new PostCode();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, custId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                postCode = new PostCode(rs.getInt("postnummer"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postCode;
    }

    public City getCity(int custId) {
        String query = "select skodatabas.orter.id, skodatabas.orter.ort_namn from skodatabas.orter" +
                " inner join skodatabas.adresser on orter.id = adresser.ort " +
                " inner join skodatabas.kunder on adresser.id = kunder.adress" +
                " where kunder.id = ? ;";
        City city = new City();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, custId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                city = new City(rs.getString("ort_namn"), rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }

}
