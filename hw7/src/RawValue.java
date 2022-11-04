import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class RawValue implements DateAnalyzer {
    public static final String EMOJI_PATTERN =
        "\\{\"name\":\"(?<name>.+?)\",\"emoji_id\":(?<id>\\d+),\"count\":(?<cnt>\\d+)}";
    public static final String CREATEDATE_PATTERN =
        "([a-zA-Z]+) (?<month>[a-zA-Z]+) (?<day>\\d{2}) (\\d{2}:\\d{2}:\\d{2}) (?<year>\\d{4})";
    public static final Map<String, Integer> MONTHINFER = new HashMap<String, Integer>() {{
            put("Jan", 1);
            put("Feb", 2);
            put("Mar", 3);
            put("Apr", 4);
            put("May", 5);
            put("Jun", 6);
            put("Jul", 7);
            put("Aug", 8);
            put("Sep", 9);
            put("Oct", 10);
            put("Nov", 11);
            put("Dec", 12); }};

    private int[] createDate;
    private String id;
    private String fullText;
    private String userId;
    private int retweetCount;
    private int favoriteCount;
    private int replyCount;
    private boolean isSensitive;
    private String lang;
    private ArrayList<Emoji> emojis;

    RawValue() {}

    RawValue(String createdAt, String id, String fullText, String userId, String rtc, String fvc,
             String rpc, String sensitive, String lang, String emj) {
        createDate = parseDate(createdAt);
        this.id = id;
        this.fullText = fullText;
        this.userId = userId;
        retweetCount = toInteger(rtc);
        favoriteCount = toInteger(fvc);
        replyCount = toInteger(rpc);
        isSensitive = sensitive.equals("true");
        this.lang = lang;
        emojis = parseEmoji(emj);
    }

    public int[] getCreateDate() {
        return createDate;
    }

    public String getId() {
        return id;
    }

    public String getFullText() {
        return fullText;
    }

    public String getUserId() {
        return userId;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public boolean getSensitive() {
        return isSensitive;
    }

    public String getLang() {
        return lang;
    }

    public ArrayList<Emoji> getEmojis() {
        return emojis;
    }

    @Override
    public int[] parseDate(String srcDate) {
        int[] res = new int[3];
        Pattern pattern = Pattern.compile(CREATEDATE_PATTERN);
        Matcher matcher = pattern.matcher(srcDate);
        if (matcher.find()) {
            res[0] = toInteger(matcher.group("year"));
            res[1] = MONTHINFER.get(matcher.group("month"));
            res[2] = toInteger(matcher.group("day"));

        }
        return res;
    }

    @Override
    public int toInteger(String src) {
        int res = 0;
        for (int i = 0; i < src.length(); i++) {
            res = res * 10 + src.charAt(i) - '0';
        }
        return res;
    }

    public ArrayList<Emoji> parseEmoji(String emojisSrc) {
        ArrayList<Emoji> res = new ArrayList<>();
        Pattern pattern = Pattern.compile(EMOJI_PATTERN);
        Matcher matcher = pattern.matcher(emojisSrc);
        while (matcher.find()) {
            String name = matcher.group("name");
            String emojiId = matcher.group("id");
            String count = matcher.group("cnt");
            Emoji emoji = new Emoji(name, emojiId, count);
            res.add(emoji);
        }
        return res;
    }
}
