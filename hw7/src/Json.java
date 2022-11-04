import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Json implements DateAnalyzer {
    public static final String RAWVALUE_PATTERN =
        "\"created_at\":\"(?<createAt>.+?)\"," +
        "\"id\":(?<id>\\d+)," +
        "\"full_text\":(?<fullText>(\"(.*?)\")|(null))," +
        "\"user_id\":(?<userId>\\d+)," +
        "\"retweet_count\":(?<retweetCount>\\d+)," +
        "\"favorite_count\":(?<favoriteCount>\\d+)," +
        "\"reply_count\":(?<replyCount>\\d+)," +
        "\"possibly_sensitive_editable\":(?<sensitive>(true)|(false))," +
        "\"lang\":\"(?<lang>.+?)\"," +
        "\"emojis\":\\[(?<emojis>.*)]";

    private String objectType;
    private int[] downloadDate;
    private RawValue rawValue;

    Json() {}

    Json(String objectType, String dd, String rv) {
        this.objectType = objectType;
        downloadDate = parseDate(dd);
        rawValue = parseRawValue(rv);
    }

    public String getObjectType() {
        return objectType;
    }

    public int[] getDownloadDates() {
        return downloadDate;
    }

    public RawValue getRawValue() {
        return rawValue;
    }

    @Override
    public int[] parseDate(String srcDate) {
        int[] res = new int[3];
        Pattern pattern = Pattern.compile(MainClass.DATE_PATTERN);
        Matcher matcher = pattern.matcher(srcDate);
        if (matcher.find()) {
            res[0] = toInteger(matcher.group("year"));
            res[1] = toInteger(matcher.group("month"));
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

    public RawValue parseRawValue(String rawValueSrc) {
        Pattern pattern = Pattern.compile(RAWVALUE_PATTERN);
        Matcher matcher = pattern.matcher(rawValueSrc);
        if (matcher.find()) {
            String createAt = matcher.group("createAt");
            String id = matcher.group("id");
            String fullText = matcher.group("fullText");
            String userId = matcher.group("userId");
            String retweetCount = matcher.group("retweetCount");
            String favoriteCount = matcher.group("favoriteCount");
            String replyCount = matcher.group("replyCount");
            String sensitive = matcher.group("sensitive");
            String lang = matcher.group("lang");
            String emojis = matcher.group("emojis");
            return new RawValue(createAt, id, fullText, userId, retweetCount,
                favoriteCount, replyCount, sensitive, lang, emojis);
        }
        return new RawValue();
    }
}
