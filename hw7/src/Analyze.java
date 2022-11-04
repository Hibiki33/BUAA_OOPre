import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyze implements Process {
    public static final String JSON_PATTERN =
        "\\{\"object_type\":\"(?<type>.+?)\"," +
        "\"download_datetime\":\"(?<date>\\d{4}-\\d{2}-\\d{2}) \\d{2}:\\d{2}\"," +
        "\"raw_value\":(?<value>\\{(.+)})}";

    @Override
    public void process(ArrayList<Json> jsonList) {
        String jsonLine = MainClass.READER.nextLine();
        while (!jsonLine.equals("END_OF_MESSAGE")) {
            Pattern pattern = Pattern.compile(JSON_PATTERN);
            Matcher matcher = pattern.matcher(jsonLine);
            if (matcher.find()) {
                String objectType = matcher.group("type");
                String downloadDate = matcher.group("date");
                String rawValue = matcher.group("value");
                jsonList.add(new Json(objectType, downloadDate, rawValue));
            }
            jsonLine = MainClass.READER.nextLine();
        }
    }
}
