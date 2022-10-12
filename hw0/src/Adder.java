import java.util.Scanner;

public class Adder {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int a = reader.nextInt();
        int b = reader.nextInt();
        reader.close();
        System.out.println(a + b);
    }
}
