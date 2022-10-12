import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static final Scanner READER = new Scanner(System.in);

    public static final String MESSAGE_PATTERN = "(\\d{1,4}/\\d{1,2}/\\d{1,2})-([A-Za-z0-9]+)" +
        "((@[A-Za-z0-9 ]+)|()):\"([A-Za-z0-9 !?,.@]+)\";";

    public static final String QUESTION_PATTERN = "(qdate (\\d{0,4}/\\d{0,2}/\\d{0,2}))|" +
        "(qsend ((-v )|())\"([A-Za-z0-9]+)\")|(qrecv ((-v )|())\"([A-Za-z0-9]+)\")";

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
                    dateQuestion(matcher, messages);
                } else if (matcher.group(3) != null) {
                    sendQuestion(matcher, messages);
                } else if (matcher.group(8) != null) {
                    recvQuestion(matcher, messages);
                }
            }
        }
    }

    public static void dateQuestion(Matcher matcher, ArrayList<Message> messages) {
        String[] d = matcher.group(2).split("/");
        int[] dates = new int[3];
        for (int i = 0; i < 3; i++) {
            dates[i] = -1;
        }
        for (int i = 0; i < d.length; i++) {
            int temp = 0;
            if (d[i].length() != 0) {
                for (int j = 0; j < d[i].length(); j++) {
                    temp = temp * 10 + d[i].charAt(j) - '0';
                }
                dates[i] = temp;
            }
        }
        for (Message message : messages) {
            if ((message.getYear() == dates[0] || dates[0] == -1)
                && (message.getMonth() == dates[1] || dates[1] == -1)
                && (message.getDay() == dates[2] || dates[2] == -1)) {
                System.out.println(message.getMessage());
            }
        }
    }

    public static void sendQuestion(Matcher matcher, ArrayList<Message> messages) {
        if (matcher.group(5) == null) {
            for (Message message : messages) {
                if (message.getSender().equals(matcher.group(7))) {
                    System.out.println(message.getMessage());
                }
            }
        } else {
            for (Message message : messages) {
                if (message.getSender().contains(matcher.group(7))) {
                    System.out.println(message.getMessage());
                }
            }
        }
    }

    public static void recvQuestion(Matcher matcher, ArrayList<Message> messages) {
        if (matcher.group(10) == null) {
            for (Message message : messages) {
                if (message.getReceiver() != null) {
                    if (message.getReceiver().equals(matcher.group(12))) {
                        System.out.println(message.getMessage());
                    }
                }
            }
        } else {
            for (Message message : messages) {
                if (message.getReceiver() != null) {
                    if (message.getReceiver().contains(matcher.group(12))) {
                        System.out.println(message.getMessage());
                    }
                }
            }
        }
    }
}
