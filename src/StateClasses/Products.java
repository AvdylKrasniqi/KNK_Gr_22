package StateClasses;

public class Products {
    private String title;
    private double price;
    private int quantity;

    public Products(String title, double price, int quantity) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return this.title;

    }


    public double getPrice() {
        return this.price;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
