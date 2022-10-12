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
            case 10 : OrderList.order10(adventurerHashMap, reader);
                break;
            case 11 : OrderList.order11(adventurerHashMap, reader);
                break;
            case 12 : OrderList.order12(adventurerHashMap, reader);
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
        int botId = reader.nextInt();
        String botName = reader.next();
        long botPrice = reader.nextLong();
        double botCapacity = reader.nextDouble();
        Bottle newBottle = new Bottle(botId, botName, botPrice, botCapacity);
        Adventurer target = adventurerHashMap.get(advId);
        target.addBottle(newBottle);
    }

    static void order3(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int botId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        target.removeBottle(botId);
    }

    static void order4(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int botId = reader.nextInt();
        long botPrice = reader.nextLong();
        Adventurer target = adventurerHashMap.get(advId);
        target.setBottlePrice(botId, botPrice);
    }

    static void order5(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int botId = reader.nextInt();
        boolean botFilled = reader.nextBoolean();
        Adventurer target = adventurerHashMap.get(advId);
        target.setBottleFilled(botId, botFilled);
    }

    static void order6(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int botId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.getBottleName(botId));
    }

    static void order7(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int botId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.getBottlePrice(botId));
    }

    static void order8(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int botId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.getBottleCapacity(botId));
    }

    static void order9(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int botId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.isBottleFilled(botId));
    }

    static void order10(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        int botId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.getBottle(botId));
    }

    static void order11(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.getSumBottlePrice());
    }

    static void order12(HashMap<Integer, Adventurer> adventurerHashMap, Scanner reader) {
        int advId = reader.nextInt();
        Adventurer target = adventurerHashMap.get(advId);
        System.out.println(target.getMaxBottlePrice());
    }
}
