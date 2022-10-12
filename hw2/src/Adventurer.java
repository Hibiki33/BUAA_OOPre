import java.math.BigInteger;
import java.util.HashMap;

public class Adventurer {
    private int id;
    private String name;
    private double health;
    private double exp;
    private double money;
    private HashMap<Integer, Equipment> equipments;

    Adventurer() { }

    Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.health = 100.0D;
        this.exp = 0.0D;
        this.money = 0.0D;
        equipments = new HashMap<>();
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getId() {
        return this.id;
    }

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

    public void addEquipment(Equipment newEquipment) {
        equipments.put(newEquipment.getId(), newEquipment);
    }

    public Equipment getEquipment(int equipmentId) {
        return equipments.get(equipmentId);
    }

    public int equipmentSize() {
        return equipments.size();
    }

    public void removeEquipment(int equipmentId) {
        equipments.remove(equipmentId);
    }

    public void setEquipmentPrice(int equipmentId, long price) {
        equipments.get(equipmentId).setPrice(price);
    }

    public void setEquipmentName(int equipmentId, String name) {
        equipments.get(equipmentId).setName(name);
    }

    public String getSumEquipmentPrice() {
        if (equipments.isEmpty()) {
            return "0";
        } else {
            BigInteger sum = new BigInteger(Long.toString(0L));
            for (Equipment x : equipments.values()) {
                BigInteger temp = new BigInteger(Long.toString(x.getPrice()));
                sum = sum.add(temp);
            }
            return sum.toString();
        }
    }

    public long getMaxEquipmentPrice() {
        long max = -1;
        for (Equipment x : equipments.values()) {
            max = Math.max(max, x.getPrice());
        }
        return max;
    }

    public void use(Equipment equipment) {
        try {
            equipment.usedBy(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
}