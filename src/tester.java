import Dao.Repository;
import Model.Shoes;
import View.View;

import java.util.ArrayList;
import java.util.List;

public class tester {
    public static void main(String[] args) {
        List<Shoes> shoesList = new ArrayList<>();
        Repository rep = new Repository();
        shoesList = rep.getAllShoes();
//        for(Shoes s: shoesList) {
//            System.out.println(s.getColor().getName());
//        }
        View v = new View();
    }
}
