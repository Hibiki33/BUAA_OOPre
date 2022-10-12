public class RareSword extends Sword {
    private double extraExpBonus;

    RareSword() { }

    RareSword(int id, String name, long price, double sharpness, double extraExpBonus) {
        setId(id);
        setName(name);
        setPrice(price);
        setSharpness(sharpness);
        this.extraExpBonus = extraExpBonus;
    }

    public void setEvolveRatio(double extraExpBonus) {
        this.extraExpBonus = extraExpBonus;
    }

    public double getEvolveRatio() {
        return this.extraExpBonus;
    }

    @Override
    public void usedBy(Adventurer adv) {
        super.usedBy(adv);
        adv.setExp(adv.getExp() + extraExpBonus);
        System.out.println(adv.getName() + " got extra exp " + extraExpBonus + ".");
    }

    @Override
    public String toString() {
        return "The rareSword's id is " + getId() +
               ", name is " + getName() +
               ", sharpness is " + getSharpness() +
               ", extraExpBonus is " + extraExpBonus + ".";
    }
}
