import java.math.BigInteger;

public interface Commodity extends Comparable<Commodity> {
    void setId(int id);

    void setName(String name);

    int getId();

    String getName();

    BigInteger getPrice();

    void usedBy(Adventurer adv) throws Exception;

    String toString();
}
