import java.math.BigInteger;

public class Equipment implements Commodity, Comparable<Commodity> {
    private int id;
    private String name;
    private BigInteger price;

    Equipment() { }

    Equipment(int id, String name, BigInteger price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public BigInteger getPrice() {
        return this.price;
    }

    @Override
    public void usedBy(Adventurer adv) throws Exception { }

    @Override
    public int compareTo(Commodity other) {
        if (this.getPrice().compareTo(other.getPrice()) < 0) {
            return 1;
        } else if (this.getPrice().compareTo(other.getPrice()) > 0) {
            return -1;
        }
        return other.getId() - this.getId();
    }

    public Commodity deepClone() {
        return new Equipment(id, name, price);
    }
}
