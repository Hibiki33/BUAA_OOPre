public class Bottle {
    private int id;
    private String name;
    private long price;
    private double capacity;
    private boolean filled;

    Bottle() { }

    Bottle(int id, String name, long price, double capacity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.capacity = capacity;
        this.filled = true;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public long getPrice() {
        return this.price;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public boolean isFilled() {
        return this.filled;
    }

    @Override public String toString() {
        String a = "The bottle's id is ";
        String b = ", name is ";
        String c = ", capacity is ";
        String d = ", filled is ";
        String e = ".";
        return  a + id + b + name + c + capacity + d + filled + e;
    }
}
