package StateClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Waiter {
    private String name;
    private String email;
    private double salary;
    private Date joined;


    public Waiter(String name,String email,double salary,Date joined)
    {
        this.name=name;
        this.email=email;
        this.salary=salary;
        this.joined=joined;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    public Date getJoined() {
        return joined;
    }

    public static Date parseDate(String date,String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date parseDate(String date)
    {
        return parseDate(date,"yyyy-MM-dd");
    }


    public void setJoined(String joined)
    {

        this.setJoined(parseDate(joined,"yyyy-MM-dd"));
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }
}
