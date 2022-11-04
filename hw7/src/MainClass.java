import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
    public static final Scanner READER = new Scanner(System.in);
    public static final Analyze ANALYZER = new Analyze();
    public static final Question QUESTIONER = new Question();
    public static final String DATE_PATTERN = "(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})";

    public static void main(String[] args) {
        ArrayList<Json> jsonList = new ArrayList<>();
        ANALYZER.process(jsonList);
        QUESTIONER.process(jsonList);
        READER.close();
    }
}
