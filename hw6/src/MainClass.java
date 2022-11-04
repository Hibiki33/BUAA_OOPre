import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainClass {
    public static final Scanner READER = new Scanner(System.in);

    public static final String MESSAGE_PATTERN = "(\\d{1,4}/\\d{1,2}/\\d{1,2})-([A-Za-z0-9]+)" +
        "((@[A-Za-z0-9 ]+)|()):\"([A-Za-z0-9 !?,.@]+)\";";

    public static final String QUESTION_PATTERN = "((?<qdate>qdate (\\d{0,4}/\\d{0,2}/\\d{0,2}))|" +
        "(?<qsend>qsend ((-v )|()|(-ssq )|(-ssr )|(-pre )|(-pos ))" +
        "((-ssq )|(-ssr )|(-pre )|(-pos )|()|(-v ))\"([A-Za-z0-9]+)\")|" +
        "(?<qrecv>qrecv ((-v )|()|(-ssq )|(-ssr )|(-pre )|(-pos ))" +
        "((-ssq )|(-ssr )|(-pre )|(-pos )|()|(-v ))\"([A-Za-z0-9]+)\"))" +
        "((?<clean> -c \"([A-Za-z0-9 !?,.@]+)\")|())";

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
                if (matcher.group("clean") == null) {
                    if (matcher.group("qdate") != null) {
                        dateQuestion(matcher, messages);
                    } else if (matcher.group("qsend") != null) {
                        sendQuestion(matcher, messages);
                    } else if (matcher.group("qrecv") != null) {
                        recvQuestion(matcher, messages);
                    }
                } else {
                    if (matcher.group("qdate") != null) {
                        dateQuestion(matcher, messages, matcher.group(38));
                    } else if (matcher.group("qsend") != null) {
                        sendQuestion(matcher, messages, matcher.group(38));
                    } else if (matcher.group("qrecv") != null) {
                        recvQuestion(matcher, messages, matcher.group(38));
                    }
                }
            }
        }
    }

    public static void dateQuestion(Matcher matcher, ArrayList<Message> messages) {
        String[] d = matcher.group(3).split("/");
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
        if (checkDates(dates)) {
            System.out.print("Command Error!: Wrong Date Format! ");
            System.out.println("\"" + matcher.group(0) + "\"");
            return;
        }
        for (Message message : messages) {
            if ((message.getYear() == dates[0] || dates[0] == -1)
                && (message.getMonth() == dates[1] || dates[1] == -1)
                && (message.getDay() == dates[2] || dates[2] == -1)) {
                System.out.println(message.getMessage());
            }
        }
    }

    public static void dateQuestion(Matcher matcher, ArrayList<Message> messages, String clean) {
        String[] d = matcher.group(3).split("/");
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
        if (checkDates(dates)) {
            System.out.print("Command Error!: Wrong Date Format! ");
            System.out.println("\"" + matcher.group("qdate") + "\"");
            return;
        }
        for (Message message : messages) {
            if ((message.getYear() == dates[0] || dates[0] == -1)
                && (message.getMonth() == dates[1] || dates[1] == -1)
                && (message.getDay() == dates[2] || dates[2] == -1)) {
                String ori = message.getMessage();
                String replacedString = cleanReplace(ori, clean);
                System.out.println(replacedString);
            }
        }
    }

    public static boolean checkDates(int[] dates) {
        // check year
        if (dates[0] == 0) {
            return true;
        }
        // check month
        if (dates[1] == 0 || dates[1] > 12) {
            return true;
        }
        // check date
        if (dates[1] == 2) {
            if (dates[0] % 400 == 0 || (dates[0] % 100 != 0 && dates[0] % 4 == 0)) {
                if (dates[2] == 0 || dates[2] > 29) {
                    return true;
                }
            } else if (dates[0] != -1) {
                if (dates[2] == 0 || dates[2] > 28) {
                    return true;
                }
            } else {
                if (dates[2] == 0 || dates[2] > 29) {
                    return true;
                }
            }
        } else if (dates[1] != -1) {
            if (dates[1] == 4 || dates[1] == 6 || dates[1] == 9 || dates[1] == 11) {
                if (dates[2] == 0 || dates[2] > 30) {
                    return true;
                }
            } else {
                if (dates[2] == 0 || dates[2] > 31) {
                    return true;
                }
            }
        } else {
            if (dates[2] == 0 || dates[2] > 31) {
                return true;
            }
        }
        return false;
    }

    public static void sendQuestion(Matcher matcher, ArrayList<Message> messages) {
        switch (checkQuestion(matcher.group(5), matcher.group(12))) {
            case 0 : System.out.print("Command Error!: Not Vague Query! ");
                System.out.println("\"" + matcher.group(0) + "\"");
                return;
            case 1 :
                for (Message message : messages) {
                    if (message.getSender().equals(matcher.group(19))) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            case 2 :
                for (Message message : messages) {
                    if (isSubsequence(matcher.group(19), message.getSender())) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            case 3 :
                for (Message message : messages) {
                    if (message.getSender().contains(matcher.group(19))) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            case 4 :
                for (Message message : messages) {
                    if (message.getSender().startsWith(matcher.group(19))) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            case 5 :
                for (Message message : messages) {
                    if (message.getSender().endsWith(matcher.group(19))) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            default : break;
        }
    }

    public static void sendQuestion(Matcher matcher, ArrayList<Message> messages, String clean) {
        switch (checkQuestion(matcher.group(5), matcher.group(12))) {
            case 0 : System.out.print("Command Error!: Not Vague Query! ");
                System.out.println("\"" + matcher.group(0) + "\"");
                return;
            case 1 :
                for (Message message : messages) {
                    if (message.getSender().equals(matcher.group(19))) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            case 2 :
                for (Message message : messages) {
                    if (isSubsequence(matcher.group(19), message.getSender())) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            case 3 :
                for (Message message : messages) {
                    if (message.getSender().contains(matcher.group(19))) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            case 4 :
                for (Message message : messages) {
                    if (message.getSender().startsWith(matcher.group(19))) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            case 5 :
                for (Message message : messages) {
                    if (message.getSender().endsWith(matcher.group(19))) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            default : break;
        }
    }

    public static void recvQuestion(Matcher matcher, ArrayList<Message> messages) {
        switch (checkQuestion(matcher.group(21), matcher.group(28))) {
            case 0 : System.out.print("Command Error!: Not Vague Query! ");
                System.out.println("\"" + matcher.group(0) + "\"");
                return;
            case 1 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        message.getReceiver().equals(matcher.group(35))) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            case 2 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        isSubsequence(matcher.group(35), message.getReceiver())) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            case 3 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        message.getReceiver().contains(matcher.group(35))) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            case 4 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        message.getReceiver().startsWith(matcher.group(35))) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            case 5 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        message.getReceiver().endsWith(matcher.group(35))) {
                        System.out.println(message.getMessage());
                    }
                }
                break;
            default : break;
        }
    }

    public static void recvQuestion(Matcher matcher, ArrayList<Message> messages, String clean) {
        switch (checkQuestion(matcher.group(21), matcher.group(28))) {
            case 0 : System.out.print("Command Error!: Not Vague Query! ");
                System.out.println("\"" + matcher.group(0) + "\"");
                return;
            case 1 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        message.getReceiver().equals(matcher.group(35))) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            case 2 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        isSubsequence(matcher.group(35), message.getReceiver())) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            case 3 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        message.getReceiver().contains(matcher.group(35))) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            case 4 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        message.getReceiver().startsWith(matcher.group(35))) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            case 5 :
                for (Message message : messages) {
                    if (message.getReceiver() != null &&
                        message.getReceiver().endsWith(matcher.group(35))) {
                        String ori = message.getMessage();
                        String replacedString = cleanReplace(ori, clean);
                        System.out.println(replacedString);
                    }
                }
                break;
            default : break;
        }
    }

    public static int checkQuestion(String v, String param) {
        if (v.equals("")) {
            if (!param.equals("")) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (param.equals("-v ")) {
                return 0;
            }
            else if (param.equals("-ssq ")) {
                return 2;
            } else if (param.equals("-ssr ")) {
                return 3;
            } else if (param.equals("-pre ")) {
                return 4;
            } else if (param.equals("pos ")) {
                return 5;
            } else {
                return 3;
            }
        }
    }

    public static String cleanReplace(String ori, String clean) {
        StringBuffer res = new StringBuffer(ori);
        int fromIndex = res.indexOf(":");
        StringBuffer rp = new StringBuffer(clean.length());
        for (int i = 0; i < clean.length(); i++) {
            rp.append("*");
        }
        int front = res.indexOf(clean, fromIndex);
        while (front != -1) {
            res.replace(front, front + clean.length(), rp.toString());
            front = res.indexOf(clean, fromIndex);
        }
        return res.toString();
    }

    public static boolean isSubsequence(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] f = new int[m + 1][128];
        for (int i = 0; i < 128; i++) {
            f[m][i] = m;
        }
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < 128; j++) {
                if (t.charAt(i) == j) {
                    f[i][j] = i;
                } else {
                    f[i][j] = f[i + 1][j];
                }
            }
        }
        int add = 0;
        for (int i = 0; i < n; i++) {
            if (f[add][s.charAt(i)] == m) {
                return false;
            }
            add = f[add][s.charAt(i)] + 1;
        }
        return true;
    }
}
