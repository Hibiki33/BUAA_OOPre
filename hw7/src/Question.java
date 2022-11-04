import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question implements Process, DateAnalyzer {
    public static final String TYPE_PATTERN = "Q[a-z]{4,9}";
    public static final String ID_PATTERN = " (?<id>\\d+)";
    public static final String RANGE_PATTERN =
        " (?<begin>\\d{4}-\\d{2}-\\d{2})~(?<end>\\d{4}-\\d{2}-\\d{2})";

    @Override
    public void process(ArrayList<Json> jsonList) {
        Pattern pattern = Pattern.compile(TYPE_PATTERN);
        while (MainClass.READER.hasNext()) {
            String question = MainClass.READER.nextLine();
            Matcher matcher = pattern.matcher(question);
            if (matcher.find()) {
                switch (matcher.group()) {
                    case "Qdate" : dateQuestion(jsonList, question);
                        break;
                    case "Qemoji" : emojiQuestion(jsonList, question);
                        break;
                    case "Qcount" : countQuestion(jsonList, question);
                        break;
                    case "Qtext" : textQuestion(jsonList, question);
                        break;
                    case "Qsensitive" : sensitiveQuestion(jsonList, question);
                        break;
                    case "Qlang" : langQuestion(jsonList, question);
                        break;
                    default : break;
                }
            }
        }
    }

    public void dateQuestion(ArrayList<Json> jsonList, String question) {
        Pattern pattern = Pattern.compile(ID_PATTERN + RANGE_PATTERN);
        Matcher matcher = pattern.matcher(question);
        if (matcher.find()) {
            int sum = 0;
            int sumRetweet = 0;
            int sumFavorite = 0;
            int sumReply = 0;
            String userId = matcher.group("id");
            int[] begin = parseDate(matcher.group("begin"));
            int[] end = parseDate(matcher.group("end"));
            for (Json json : jsonList) {
                RawValue rawValue = json.getRawValue();
                if (rawValue.getUserId().equals(userId)) {
                    if (isBetween(begin, end, rawValue.getCreateDate())) {
                        sum++;
                        sumRetweet += rawValue.getRetweetCount();
                        sumFavorite += rawValue.getFavoriteCount();
                        sumReply += rawValue.getReplyCount();
                    }
                }
            }
            System.out.println(sum + " " + sumRetweet + " " + sumFavorite + " " + sumReply);
        }
    }

    public void emojiQuestion(ArrayList<Json> jsonList, String question) {
        Pattern pattern = Pattern.compile(ID_PATTERN);
        Matcher matcher = pattern.matcher(question);
        if (matcher.find()) {
            String id = matcher.group("id");
            for (Json json : jsonList) {
                RawValue rawValue = json.getRawValue();
                if (rawValue.getId().equals(id)) {
                    ArrayList<Emoji> emojis = rawValue.getEmojis();
                    if (emojis.size() == 0) {
                        System.out.println("None");
                    } else {
                        Collections.sort(emojis);
                        for (Emoji emoji : emojis) {
                            System.out.print(emoji.getName() + " ");
                        }
                        System.out.println();
                    }
                }
            }
        }
    }

    public void countQuestion(ArrayList<Json> jsonList, String question) {
        Pattern pattern = Pattern.compile(RANGE_PATTERN);
        Matcher matcher = pattern.matcher(question);
        if (matcher.find()) {
            int sum = 0;
            int[] begin = parseDate(matcher.group("begin"));
            int[] end = parseDate(matcher.group("end"));
            for (Json json : jsonList) {
                if (isBetween(begin, end, json.getDownloadDates())) {
                    sum++;
                }
            }
            System.out.println(sum);
        }
    }

    public void textQuestion(ArrayList<Json> jsonList, String question) {
        Pattern pattern = Pattern.compile(ID_PATTERN);
        Matcher matcher = pattern.matcher(question);
        if (matcher.find()) {
            String id = matcher.group("id");
            for (Json json : jsonList) {
                RawValue rawValue = json.getRawValue();
                if (rawValue.getId().equals(id)) {
                    String fullText = rawValue.getFullText();
                    if (fullText.equals("null")) {
                        System.out.println("None");
                    } else if (fullText.equals("\"\"")) {
                        System.out.print("");
                    } else {
                        System.out.println(fullText.replace("\"", ""));
                    }
                }
            }
        }
    }

    public void sensitiveQuestion(ArrayList<Json> jsonList, String question) {
        Pattern pattern = Pattern.compile(ID_PATTERN);
        Matcher matcher = pattern.matcher(question);
        if (matcher.find()) {
            int sum = 0;
            String userId = matcher.group("id");
            for (Json json : jsonList) {
                RawValue rawValue = json.getRawValue();
                if (rawValue.getUserId().equals(userId) && rawValue.getSensitive()) {
                    sum++;
                }
            }
            System.out.println(sum);
        }
    }

    public void langQuestion(ArrayList<Json> jsonList, String question) {
        Pattern pattern = Pattern.compile(ID_PATTERN);
        Matcher matcher = pattern.matcher(question);
        if (matcher.find()) {
            String id = matcher.group("id");
            for (Json json : jsonList) {
                RawValue rawValue = json.getRawValue();
                if (rawValue.getId().equals(id)) {
                    System.out.println(rawValue.getLang());
                }
            }
        }
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

    public boolean isBetween(int[] begin, int[] end, int[] createDate) {
        long beginDate = (long)begin[0] * 10000 + (long)begin[1] * 100 + (long)begin[2];
        long endDate = (long)end[0] * 10000 + (long)end[1] * 100 + (long)end[2];
        long create = (long)createDate[0] * 10000 + (long)createDate[1] * 100 + (long)createDate[2];
        return create >= beginDate && create <= endDate;
    }
}
