import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static final Scanner READER = new Scanner(System.in);

    public static final String MESSAGE_PATTERN = "(\\d{1,4}/\\d{1,2}/\\d{1,2})-([A-Za-z0-9]+)" +
        "((@[A-Za-z0-9 ]+)|()):\"([A-Za-z0-9 !?,.@]+)\";";

    public static final String QUESTION_PATTERN = "(qdate (\\d{1,4}/\\d{1,2}/\\d{1,2}))|" +
        "(qsend \"([A-Za-z0-9]+)\")|(qrecv \"([A-Za-z0-9]+)\")";

    public static void main(String[] args) {
        ArrayList<Message> messages = new ArrayList<>();
        getMessages(messages);
        questions(messages);
    }

    public static void getMessages(ArrayList<Message> messages) {
        Pattern pattern = Pattern.compile(MESSAGE_PATTERN);
        String line = READER.nextLine();
        while (!line.equals("END_OF_MESSAGE")) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String message = matcher.group().trim();
                String date = matcher.group(1).trim();
                String sender = matcher.group(2).trim();
                String receiver;
                Message m;
                if (matcher.group(4) != null) {
                    receiver = matcher.group(4).substring(1).trim();
                    m = new Message(message, date, sender, receiver);
                } else {
                    receiver = findReceiver(matcher.group(6));
                    if (receiver != null) {
                        m = new Message(message, date, sender, receiver);
                    } else {
                        m = new Message(message, date, sender);
                    }
                }
                messages.add(m);
            }
            line = READER.nextLine();
        }
    }

    public static String findReceiver(String sentence) {
        int start = sentence.indexOf("@");
        if (start != -1) {
            int end = sentence.indexOf(" ", start);
            return sentence.substring(start + 1, end).trim();
        }
        return null;
    }

    public static void questions(ArrayList<Message> messages) {
        Pattern pattern = Pattern.compile(QUESTION_PATTERN);
        String question;
        while (READER.hasNext()) {
            question = READER.nextLine();
            Matcher matcher = pattern.matcher(question);
            if (matcher.find()) {
                if (matcher.group(1) != null) {
                    String[] dates = matcher.group(2).split("/");
                    int y = 0;
                    for (int i = 0; i < dates[0].length(); i++) {
                        y = y * 10 + dates[0].charAt(i) - '0';
                    }
                    int m = 0;
                    for (int i = 0; i < dates[1].length(); i++) {
                        m = m * 10 + dates[1].charAt(i) - '0';
                    }
                    int d = 0;
                    for (int i = 0; i < dates[2].length(); i++) {
                        d = d * 10 + dates[2].charAt(i) - '0';
                    }
                    for (Message message : messages) {
                        if (message.getYear() == y && message.getMouth() == m
                            && message.getDay() == d) {
                            System.out.println(message.getMessage());
                        }
                    }
                } else if (matcher.group(3) != null) {
                    for (Message message : messages) {
                        if (message.getSender().equals(matcher.group(4))) {
                            System.out.println(message.getMessage());
                        }
                    }
                } else if (matcher.group(5) != null) {
                    for (Message message : messages) {
                        String receiver = message.getReceiver();
                        if (receiver != null && receiver.equals(matcher.group(6))) {
                            System.out.println(message.getMessage());
                        }
                    }
                }
            }
        }
    }
}
