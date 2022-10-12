public class EpicSword extends Sword {
    private double evolveRatio;

    EpicSword() { }

    EpicSword(int id, String name, long price, double sharpness, double evolveRatio) {
        setId(id);
        setName(name);
        setPrice(price);
        setSharpness(sharpness);
        this.evolveRatio = evolveRatio;
    }

    public void setEvolveRatio(double evolveRatio) {
        this.evolveRatio = evolveRatio;
    }

    public double getEvolveRatio() {
        return this.evolveRatio;
    }

    @Override
    public void usedBy(Adventurer adv) {
        super.usedBy(adv);
        setSharpness(getSharpness() * evolveRatio);
        System.out.println(getName() + "'s sharpness became " + getSharpness() + ".");
    }

    @Override
    public String toString() {
        return "The epicSword's id is " + getId() +
               ", name is " + getName() +
               ", sharpness is " + getSharpness() +
               ", evolveRatio is " + evolveRatio + ".";
    }
}
