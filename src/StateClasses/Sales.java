package StateClasses;

import java.sql.Date;

public class Sales {
    private int id;
    private double amount;
    private Date date;

    public int getId() {
        return id;
    }

   public Sales(int id,double amount,Date date)
   {
       this.id=id;
       this.amount=amount;
       this.date=date;
   }
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
