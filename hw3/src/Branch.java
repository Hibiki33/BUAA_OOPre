import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Branch {
    private ArrayList<Adventurer> adventurerArrayList;
    private String name;
    private ArrayList<int[]> hireRelation;

    Branch() { }

    Branch(String name, ArrayList<Adventurer> newAdventurers, ArrayList<int[]> newHireRelation) {
        hireRelation = newHireRelation;
        adventurerArrayList = newAdventurers;
        this.name = name;
    }

    public void setAdventurers(ArrayList<Adventurer> newAdventurers) {
        this.adventurerArrayList = newAdventurers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Adventurer> getAdventurers() {
        return adventurerArrayList;
    }

    public void orders(int type, Scanner reader) {
        switch (type) {
            case 1 : this.order1(reader);
                break;
            case 2 : this.order2(reader);
                break;
            case 3 : this.order3(reader);
                break;
            case 4 : this.order4(reader);
                break;
            case 5 : this.order5(reader);
                break;
            case 6 : this.order6(reader);
                break;
            case 7 : this.order7(reader);
                break;
            case 8 : this.order8(reader);
                break;
            case 9 : this.order9(reader);
                break;
            case 10 : this.order10(reader);
                break;
            default: break;
        }
    }

    public void order1(Scanner reader) {
        int advId = reader.nextInt();
        String advName = reader.next();
        Adventurer newAdventurer = new Adventurer(advId, advName);
        adventurerArrayList.add(newAdventurer);
    }

    public void order2(Scanner reader) {
        int advId = reader.nextInt();
        int equType = reader.nextInt();
        int equId = reader.nextInt();
        String equName = reader.next();
        long price = reader.nextLong();
        BigInteger equPrice = new BigInteger(Long.toString(price));
        switch (equType) {
            case 1 : double botCapacity = reader.nextDouble();
                Commodity bot = new Bottle(equId, equName, equPrice, botCapacity);
                Adventurer target1 = getAdventurer(advId);
                if (target1 != null) {
                    target1.addCommodity(bot);
                }
                break;
            case 2 : double hpCapacity = reader.nextDouble();
                double efficiency = reader.nextDouble();
                Commodity hp = new HealingPotion(equId, equName, equPrice, hpCapacity, efficiency);
                Adventurer target2 = getAdventurer(advId);
                if (target2 != null) {
                    target2.addCommodity(hp);
                }
                break;
            case 3 : double ebCapacity = reader.nextDouble();
                double expRatio = reader.nextDouble();
                Commodity eb = new ExpBottle(equId, equName, equPrice, ebCapacity, expRatio);
                Adventurer target3 = getAdventurer(advId);
                if (target3 != null) {
                    target3.addCommodity(eb);
                }
                break;
            case 4 : double sharpness = reader.nextDouble();
                Commodity s = new Sword(equId, equName, equPrice, sharpness);
                Adventurer target4 = getAdventurer(advId);
                if (target4 != null) {
                    target4.addCommodity(s);
                }
                break;
            case 5 : double rsSharpness = reader.nextDouble();
                double extraExpBonus = reader.nextDouble();
                Commodity rs = new RareSword(equId, equName, equPrice, rsSharpness, extraExpBonus);
                Adventurer target5 = getAdventurer(advId);
                if (target5 != null) {
                    target5.addCommodity(rs);
                }
                break;
            case 6 : double esSharpness = reader.nextDouble();
                double evolveRatio = reader.nextDouble();
                Commodity es = new EpicSword(equId, equName, equPrice, esSharpness, evolveRatio);
                Adventurer target6 = getAdventurer(advId);
                if (target6 != null) {
                    target6.addCommodity(es);
                }
                break;
            default: break;
        }
    }

    public void order3(Scanner reader) {
        int advId = reader.nextInt();
        int comId = reader.nextInt();
        Adventurer target = getAdventurer(advId);
        if (target != null) {
            target.removeCommodity(comId);
        }
    }

    public void order4(Scanner reader) {
        int advId = reader.nextInt();
        Adventurer target = getAdventurer(advId);
        if (target != null) {
            System.out.println(target.getSumCommodityPrice());
        }
    }

    public void order5(Scanner reader) {
        int advId = reader.nextInt();
        Adventurer target = getAdventurer(advId);
        if (target != null) {
            System.out.println(target.getMaxCommodityPrice());
        }
    }

    public void order6(Scanner reader) {
        int advId = reader.nextInt();
        Adventurer target = getAdventurer(advId);
        if (target != null) {
            System.out.println(target.commoditySize());
        }
    }

    public void order7(Scanner reader) {
        int advId = reader.nextInt();
        int comId = reader.nextInt();
        Adventurer target = getAdventurer(advId);
        if (target != null) {
            System.out.println(target.getCommodity(comId));
        }
    }

    public void order8(Scanner reader) {
        int advId = reader.nextInt();
        Adventurer target = getAdventurer(advId);
        if (target != null) {
            target.use();
        }
    }

    public void order9(Scanner reader) {
        int advId = reader.nextInt();
        System.out.println(getAdventurer(advId));
    }

    public void order10(Scanner reader) {
        int advId1 = reader.nextInt();
        int advId2 = reader.nextInt();
        Adventurer a = getAdventurer(advId1);
        Commodity b = getAdventurer(advId2);
        if (a != null && b != null) {
            a.addCommodity(b);
        }
        int[] newHire = {advId1, advId2};
        hireRelation.add(newHire);
    }

    public Adventurer getAdventurer(int advId) {
        for (Adventurer x : adventurerArrayList) {
            if (x.getId() == advId) {
                return x;
            }
        }
        return null;
    }

    public Branch branchClone(String newName) {
        ArrayList<Adventurer> newAdventurers = new ArrayList<>();
        ArrayList<int[]> newHireRelation = new ArrayList<>();
        for (Adventurer adventurer : adventurerArrayList) {
            Adventurer a = new Adventurer(adventurer.getId(), adventurer.getName());
            a.setExp(adventurer.getExp());
            a.setHealth(adventurer.getHealth());
            a.setMoney(adventurer.getMoney());
            for (Commodity commodity : adventurer.getCommodities()) {
                if (commodity instanceof Equipment) {
                    Commodity c = ((Equipment) commodity).deepClone();
                    a.addCommodity(c);
                }
            }
            newAdventurers.add(a);
        }
        for (int[] relation : hireRelation) {
            Adventurer adv1 = new Adventurer();
            Adventurer adv2 = new Adventurer();
            for (Adventurer n : newAdventurers) {
                if (relation[0] == n.getId()) {
                    adv1 = n;
                }
                if (relation[1] == n.getId()) {
                    adv2 = n;
                }
            }
            adv1.addCommodity(adv2);
            int[] r = {relation[0], relation[1]};
            newHireRelation.add(r);
        }
        return new Branch(newName, newAdventurers, newHireRelation);
    }
}
