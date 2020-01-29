package View;

import Controller.Controller;

import java.util.Scanner;

public class View {
    private Scanner sc;
    private Controller controller;
    public View() {
        controller = new Controller(this);
        sc = new Scanner(System.in);
        boolean loginCheck = false;

        while(!loginCheck) {
            String user;
            String pw;
            System.out.println("Välkommen, vänligen logga in: ");
            System.out.print("Förnamn: ");
            user = sc.nextLine();
            System.out.println();
            System.out.print("Lösenord: ");
            pw = sc.nextLine();
            if(controller.checkCred(user, pw)) {
                loginCheck = true;
            }
            else {
                System.out.println("Fel försök igen ");
            }
            categoryCheck();
        }
    }
    public void categoryCheck() {
        boolean categoryCheck = false;
        int antalSkor = 0;
        while(!categoryCheck) {

            int selectCategory;
            System.out.println("---------Kategorier----------");
            controller.getCategories();
            System.out.println("\n\n\nVälj en skokategori: (1-6) eller avsluta med \'q\'");

            if(sc.hasNextInt()) {
                selectCategory = sc.nextInt();
                if(selectCategory > 6 || selectCategory < 0) {
                    System.out.println("Vänligen välj mellan 1-6");
                }

                antalSkor = controller.getShoesByCat(selectCategory);
                categoryCheck = true;

            }
            else {
                if(sc.next().equalsIgnoreCase("q")) {
                    System.out.println("Avslutar, hejdå!");
                    System.exit(0);
                }
                System.out.println("Ange ett heltal (1-6)");
                sc.next();
            }
        }
        pickShoe(antalSkor);
    }

    public void pickShoe(int antalSkor) {
        boolean shoeCheck = false;
        int pickedShoe = 0;
        while(!shoeCheck) {
            System.out.println("\nAnge vilken sko du vill beställa: (1-" + antalSkor + ") \n" +
                    "eller gå tillbaka till kategorier (skriv \'b\')");
            if (sc.hasNextInt()) {
                pickedShoe = sc.nextInt();
                if(pickedShoe > antalSkor || pickedShoe < 0) {
                    System.out.println("Vänligen välj mellan 1-" + antalSkor + ")");
                }
                if(controller.placeOrder(pickedShoe)) {

                    System.out.println("Din beställning lyckades. Vill du beställa mer? (k) för kategori (q) för avsluta");
                    String input = sc.next();
                    shoeCheck = true;

                    if(input.equalsIgnoreCase("k")) {
                        categoryCheck();
                    }
                    else if(input.equalsIgnoreCase("q")) {
                        System.out.println("Avslutar!");
                        System.exit(0);
                    }
                }
                else  {
                    System.out.println("Något gick fel med din beställning avslutar");
                    System.exit(0);
                }


            }
            else {
                if(sc.next().equalsIgnoreCase("b")) {
                    categoryCheck();
                }
                System.out.println("Ange ett heltal eller gå tillbaka till kategorier (skriv \'b\')");
            }
        }
    }

    public void printString (String msg) {
        System.out.println(msg);
    }
}
