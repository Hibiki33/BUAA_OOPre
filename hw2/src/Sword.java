public class Sword extends Equipment {
    private double sharpness;

    Sword() { }

    Sword(int id, String name, long price, double sharpness) {
        setId(id);
        setName(name);
        setPrice(price);
        this.sharpness = sharpness;
    }

    public void setSharpness(double sharpness) {
        this.sharpness = sharpness;
    }

    public double getSharpness() {
        return this.sharpness;
    }

    @Override
    public void usedBy(Adventurer adv) {
        adv.setHealth(adv.getHealth() - 10.0D);
        adv.setExp(adv.getExp() + 10.0D);
        adv.setMoney(adv.getMoney() + sharpness);
        System.out.println(adv.getName() + " used " + getName() +
                           " and earned " + sharpness + ".");
    }

    @Override
    public String toString() {
        return "The sword's id is " + getId() +
               ", name is " + getName() +
               ", sharpness is " + sharpness + ".";
    }
}
