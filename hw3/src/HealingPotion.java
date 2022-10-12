import java.math.BigInteger;

public class HealingPotion extends Bottle {
    private double efficiency;

    HealingPotion() { }

    HealingPotion(int id, String name, BigInteger price, double capacity, double efficiency) {
        setId(id);
        setName(name);
        setPrice(price);
        setCapacity(capacity);
        setFilled(true);
        this.efficiency = efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public double getEfficiency() {
        return this.efficiency;
    }

    @Override
    public Commodity deepClone() {
        HealingPotion ret = new HealingPotion(getId(), getName(),
            getPrice(), getCapacity(), efficiency);
        ret.setFilled(getFilled());
        return ret;
    }

    @Override
    public void usedBy(Adventurer adv) throws Exception {
        super.usedBy(adv);
        adv.setHealth(adv.getHealth() + getCapacity() * getEfficiency());
        System.out.println(adv.getName() + " recovered extra " +
                           getCapacity() * getEfficiency() + ".");
    }

    @Override
    public String toString() {
        return "The healingPotion's id is " + getId() +
                ", name is " + getName() +
                ", capacity is " + getCapacity() +
                ", filled is " + isFilled() +
                ", efficiency is " + efficiency + ".";
    }
}
