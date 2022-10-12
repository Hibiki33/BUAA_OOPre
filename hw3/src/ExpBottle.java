import java.math.BigInteger;

public class ExpBottle extends Bottle {
    private double expRatio;

    ExpBottle() { }

    ExpBottle(int id, String name, BigInteger price, double capacity, double expRatio) {
        setId(id);
        setName(name);
        setPrice(price);
        setCapacity(capacity);
        setFilled(true);
        this.expRatio = expRatio;
    }

    public void setExpRatio(double expRatio) {
        this.expRatio = expRatio;
    }

    public double getExpRatio() {
        return this.expRatio;
    }

    @Override
    public Commodity deepClone() {
        ExpBottle ret = new ExpBottle(getId(), getName(),
            getPrice(), getCapacity(), expRatio);
        ret.setFilled(getFilled());
        return ret;
    }

    @Override
    public void usedBy(Adventurer adv) throws Exception {
        super.usedBy(adv);
        adv.setExp(adv.getExp() * getExpRatio());
        System.out.println(adv.getName() + "'s exp became " + adv.getExp() + ".");
    }

    @Override
    public String toString() {
        return "The expBottle's id is " + getId() +
                ", name is " + getName() +
                ", capacity is " + getCapacity() +
                ", filled is " + isFilled() +
                ", expRatio is " + expRatio + ".";
    }
}
