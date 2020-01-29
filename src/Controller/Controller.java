package Controller;

import Dao.Repository;
import Model.Category;
import Model.Customer;
import Model.Order;
import Model.Shoes;
import View.View;

import java.util.ArrayList;
import java.util.List;


public class Controller {
    private View view;
    private Repository rep;
    List<Shoes> shoesList;
    Customer customer;
    Order order;
    public Controller(View view) {
        shoesList = new ArrayList<>();
        rep = new Repository();
        this.view = view;
    }

    public boolean checkCred(String user, String pw) {
        customer = rep.credDatabase(user, pw);
        if(customer != null) {
            view.printString(customer.printWelcome());
            return true;
        }
        else {
            return false;
        }
    }

    public void getCategories() {
        for(Category c: rep.getAllCategories()) {
            view.printString(c.getCategory());
        }
    }

    public int getShoesByCat(int userInput) {
        int count = 0;
        shoesList = rep.getShoesByCategory(userInput);
        if(shoesList.size() > 0) {
            for (Shoes s : shoesList) {
                view.printString(++count + s.printShoes());
            }
        }
        return count;
    }

    public boolean placeOrder(int shoe) {
        System.out.println(shoesList.get(shoe-1).getId());
        int orderNr = 0;
        order = rep.getOrder(customer.getId());
        if(order == null) {
            orderNr = 0;
        }
        else {
            orderNr = order.getId();
        }

        boolean checker = rep.addOrder( shoesList.get(shoe-1).getId(), orderNr , customer.getId());
        if(checker) {
            return true;
        }
        else {
            return false;
        }

    }

    public void getList() {
        List<Shoes> orderShoesList = rep.getOrderItems(customer.getId());
//        for(Shoes s: rep.getOrderItems(customer.getId())) {
//            totalPrice += s.getPrice().getPrice();
//            view.printString(s.printListShoes());
//        }
        orderShoesList.forEach(l-> view.printString(l.printListShoes()));


    }


}
