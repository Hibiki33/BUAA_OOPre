import java.math.BigInteger;
import java.util.HashMap;

public class Adventurer {
    private int id;
    private String name;
    private HashMap<Integer, Bottle> bottles;

    Adventurer() { }

    Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        bottles = new HashMap<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void addBottle(Bottle newBottle) {
        bottles.put(newBottle.getId(), newBottle);
    }

    public Bottle getBottle(int bottleId) {
        return bottles.get(bottleId);
    }

    public void removeBottle(int bottleId) {
        Bottle target = bottles.get(bottleId);
        bottles.remove(target.getId());
    }

    public void setBottlePrice(int bottleId, long price) {
        Bottle target = bottles.get(bottleId);
        target.setPrice(price);
    }

    public void setBottleName(int bottleId, String name) {
        Bottle target = bottles.get(bottleId);
        target.setName(name);
    }

    public void setBottleFilled(int bottleId, boolean filled) {
        Bottle target = bottles.get(bottleId);
        target.setFilled(filled);
    }

    public String getBottleName(int bottleId) {
        Bottle target = bottles.get(bottleId);
        return target.getName();
    }

    public long getBottlePrice(int bottleId) {
        Bottle target = bottles.get(bottleId);
        return target.getPrice();
    }

    public double getBottleCapacity(int bottleId) {
        Bottle target = bottles.get(bottleId);
        return target.getCapacity();
    }

    public boolean isBottleFilled(int bottleId) {
        Bottle target = bottles.get(bottleId);
        return target.isFilled();
    }

    public String getSumBottlePrice() {
        if (bottles.isEmpty()) {
            return "0";
        } else {
            BigInteger sum = new BigInteger(Long.toString(0L));
            for (Bottle x : bottles.values()) {
                BigInteger temp = new BigInteger(Long.toString(x.getPrice()));
                sum = sum.add(temp);
            }
            return sum.toString();
        }
    }

    public long getMaxBottlePrice() {
        long max = -1;
        for (Bottle x : bottles.values()) {
            max = Math.max(max, x.getPrice());
        }
        return max;
    }
}