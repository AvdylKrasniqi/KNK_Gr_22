package StateClasses;

import java.util.HashMap;

public class Tables {

    private boolean isOccupied;
    private HashMap<String,Double> products = new HashMap<>();
    private int totalPrice;
    public Tables()
    {
        this.isOccupied=false;
    }


    public void occupyTable()
    {
        this.isOccupied=true;
    }


    public void  clearTable()
    {
        this.isOccupied=false;
        for (String key: products.keySet()) {
            totalPrice+=products.get(key);
            //TODO: Kuponi Fiskal
        }
     this.products.clear();
    }
    public void addProduct(String name,Double price,int quantity)
    {
        this.products.put(name,price*quantity);
    }

    public void increaseProduct(String name ,Double price , int quantity)
    {
        this.products.replace(name,price*quantity);
    }


}
