public class Bottle extends Equipment {
    private double capacity;
    private boolean filled;

    Bottle() { }

    Bottle(int id, String name, long price, double capacity) {
        setId(id);
        setName(name);
        setPrice(price);
        this.capacity = capacity;
        this.filled = true;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public boolean isFilled() {
        return this.filled;
    }

    @Override
    public void usedBy(Adventurer adv) throws Exception {
        if (!isFilled()) {
            throw new Exception("Failed to use " + getName() + " because it is empty.");
        }
        adv.setHealth(adv.getHealth() + getCapacity() / 10);
        setFilled(false);
        setPrice(getPrice() / 10);
        System.out.println(adv.getName() + " drank " + getName() +
                           " and recovered " + getCapacity() / 10 + ".");
    }

    @Override
    public String toString() {
        return  "The bottle's id is " + getId() +
                ", name is " + getName() +
                ", capacity is " + capacity +
                ", filled is " + filled + ".";
    }
}
