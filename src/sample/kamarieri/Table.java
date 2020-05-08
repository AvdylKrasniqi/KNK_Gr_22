package sample.kamarieri;

public class Table {

    private int id;
    private boolean busy;
    // not sure tbh on that one
    private int capacity;


    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


}

