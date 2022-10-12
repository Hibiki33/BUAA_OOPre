import java.util.HashMap;
import java.util.Scanner;

public class OrderList {
    static void orders(int type, HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        switch (type) {
            case 1 : OrderList.order1(adventurerHashMap, reader);
                break;
            case 2 : OrderList.order2(adventurerHashMap, reader);
                break;
            case 3 : OrderList.order3(adventurerHashMap, reader);
                break;
            case 4 : OrderList.order4(adventurerHashMap, reader);
                break;
            case 5 : OrderList.order5(adventurerHashMap, reader);
                break;
            case 6 : OrderList.order6(adventurerHashMap, reader);
                break;
            case 7 : OrderList.order7(adventurerHashMap, reader);
                break;
            case 8 : OrderList.order8(adventurerHashMap, reader);
                break;
            case 9 : OrderList.order9(adventurerHashMap, reader);
                break;
            default: break;
        }
    }

    static void order1(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        String advName = reader.next();
        Adventurer newAdventurer = new Adventurer(advId, advName);
        adventurerHashMap.put(advId, newAdventurer);
    }

    static void order2(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int equType = reader.nextInt();
        int equId = reader.nextInt();
        String equName = reader.next();
        long equPrice = reader.nextLong();
        switch (equType) {
            case 1 :
                double botCapacity = reader.nextDouble();
                Equipment bot = new Bottle(equId, equName, equPrice, botCapacity);
                adventurerHashMap.get(advId).addEquipment(bot);
                break;
            case 2 :
                double hpCapacity = reader.nextDouble();
                double efficiency = reader.nextDouble();
                Equipment hp = new HealingPotion(equId, equName, equPrice, hpCapacity, efficiency);
                adventurerHashMap.get(advId).addEquipment(hp);
                break;
            case 3 :
                double ebCapacity = reader.nextDouble();
                double expRatio = reader.nextDouble();
                Equipment eb = new ExpBottle(equId, equName, equPrice, ebCapacity, expRatio);
                adventurerHashMap.get(advId).addEquipment(eb);
                break;
            case 4 :
                double sharpness = reader.nextDouble();
                Equipment s = new Sword(equId, equName, equPrice, sharpness);
                adventurerHashMap.get(advId).addEquipment(s);
                break;
            case 5 :
                double rsSharpness = reader.nextDouble();
                double extraExpBonus = reader.nextDouble();
                Equipment rs = new RareSword(equId, equName, equPrice, rsSharpness, extraExpBonus);
                adventurerHashMap.get(advId).addEquipment(rs);
                break;
            case 6 :
                double esSharpness = reader.nextDouble();
                double evolveRatio = reader.nextDouble();
                Equipment es = new EpicSword(equId, equName, equPrice, esSharpness, evolveRatio);
                adventurerHashMap.get(advId).addEquipment(es);
                break;
            default: break;
        }
    }

    static void order3(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int equId = reader.nextInt();
        adventurerHashMap.get(advId).removeEquipment(equId);
    }

    static void order4(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.getSumEquipmentPrice());
    }

    static void order5(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.getMaxEquipmentPrice());
    }

    static void order6(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        System.out.println(adventurerHashMap.get(advId).equipmentSize());
    }

    static void order7(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int equId = reader.nextInt();
        System.out.println(adventurerHashMap.get(advId).getEquipment(equId));
    }

    static void order8(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int equId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        target.use(target.getEquipment(equId));
    }

    static void order9(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        System.out.println(adventurerHashMap.get(advId));
    }
}
