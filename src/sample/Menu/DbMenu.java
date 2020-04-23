package sample.Menu;

public class DbMenu {
private int id;
private int waiterId;
private String title;
private double price;
private String imageSrc;
private String description;


public DbMenu(int id ,String title,double price,String imageSrc,String description)

{
    this(id,title,price,imageSrc);
    this.description=description;

}

public DbMenu(int id,double price,String title)
    {
        this.id=id;
        this.title=title;
        this.price=price;
    }

public DbMenu(int id,String title,double price,String imageSrc)
{
    this.title=title;
    this.price=price;
    this.id=id;
    this.imageSrc=imageSrc;

}




    public int getId() {
        return id;
    }



    public int getWaiterId() {
        return waiterId;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
