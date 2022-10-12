import java.util.Scanner;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        HashMap<Integer, Adventurer> adventurerHashMap = new HashMap<>();
        int operateNumbers = reader.nextInt();
        for (int i = 0; i < operateNumbers; i++) {
            int orderType = reader.nextInt();
            OrderList.orders(orderType, adventurerHashMap, reader);
        }
        reader.close();
    }
}
