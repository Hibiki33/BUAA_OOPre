import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Adventurer implements Commodity, Comparable<Commodity> {
    private int id;
    private String name;
    private double health;
    private double exp;
    private double money;
    private ArrayList<Commodity> commodities;

    Adventurer() { }

    Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.health = 100.0D;
        this.exp = 0.0D;
        this.money = 0.0D;
        commodities = new ArrayList<>();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public double getHealth() {
        return this.health;
    }

    public double getExp() {
        return this.exp;
    }

    public double getMoney() {
        return this.money;
    }

    @Override
    public BigInteger getPrice() {
        if (commodities.isEmpty()) {
            return new BigInteger("0");
        }
        BigInteger price = new BigInteger("0");
        for (Commodity x : commodities) {
            price = price.add(x.getPrice());
        }
        return price;
    }

    public ArrayList<Commodity> getCommodities() {
        return commodities;
    }

    public void addCommodity(Commodity newCommodity) {
        commodities.add(newCommodity);
    }

    public Commodity getCommodity(int comId) {
        for (Commodity x : commodities) {
            if (x.getId() == comId) {
                return x;
            }
        }
        return null;
    }

    public int commoditySize() {
        return commodities.size();
    }

    public void removeCommodity(int comId) {
        commodities.remove(getCommodity(comId));
    }

    public BigInteger getSumCommodityPrice() {
        if (commodities.isEmpty()) {
            return new BigInteger("0");
        } else {
            BigInteger sum = new BigInteger("0");
            for (Commodity x : commodities) {
                sum = sum.add(x.getPrice());
            }
            return sum;
        }
    }

    public BigInteger getMaxCommodityPrice() {
        BigInteger max = new BigInteger("0");
        for (Commodity x : commodities) {
            if (max.compareTo(x.getPrice()) < 0) {
                max = x.getPrice();
            }
        }
        return max;
    }

    public void use() {
        Collections.sort(commodities);
        for (Commodity commodity : commodities) {
            try {
                commodity.usedBy(this);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void usedBy(Adventurer adv) {
        Collections.sort(commodities);
        for (Commodity commodity : commodities) {
            try {
                commodity.usedBy(adv);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String toString() {
        return "The adventurer's id is " + id +
               ", name is " + name +
               ", health is " + health +
               ", exp is " + exp +
               ", money is " + money + ".";
    }

    @Override
    public int compareTo(Commodity other) {
        if (this.getPrice().compareTo(other.getPrice()) < 0) {
            return 1;
        } else if (this.getPrice().compareTo(other.getPrice()) > 0) {
            return -1;
        }
        return other.getId() - this.getId();
    }
}