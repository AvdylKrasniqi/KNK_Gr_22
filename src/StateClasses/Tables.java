package StateClasses;

import java.util.HashMap;
import java.util.Map;


public class Tables {
    private int id;
    private boolean isOccupied;
    private HashMap<String, Double> products = new HashMap<>();
    private double totalPrice;
    private int capacity;

    public Tables() {
        this.capacity = 4;
        this.isOccupied = false;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }


    public Tables(int id) {
        this.capacity = 4;
        this.id = id;
        this.isOccupied = false;
    }


    public int getId() {
        return this.id;
    }

    public void increaseTotalPrice(double price) {
        this.totalPrice += price;
    }


    public int getCapacity() {
        return this.capacity;
    }

    public boolean getOccupied() {
        return this.isOccupied;
    }


    public void occupyTable() {
        this.isOccupied = true;
    }


    public void clearTable() {
        this.isOccupied = false;
        this.products.clear();
    }


    public HashMap getProducts() {
        return this.products;
    }

    @Override
    public String toString() {
        StringBuilder mesazhi = new StringBuilder();
        if (this.getOccupied())
            mesazhi.append("Tavolina eshte e uzurpuar");
        mesazhi.append("\nTotal price eshte " + this.totalPrice);
        mesazhi.append("\nProduktet tona jane");
        for (Map.Entry<String, Double> entry : this.products.entrySet())
            mesazhi.append("\nSasia:" + entry.getKey() + " - " + entry.getValue() + " Euro");
        mesazhi.append("\nNumri i uleseve eshte " + this.capacity);
        return mesazhi.toString();

    }

    //  this.table.addProduct(quantity +" " + currentItem, price, quantity);
    public void addProduct(String name, Double price, int quantity) {
        this.products.put(name, price * quantity);
    }

    public void increaseProduct(String name, Double price, int quantity) {
        this.products.replace(name, price * quantity);
    }


}
