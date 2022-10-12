public class Message {
    private String message;
    private int year;
    private int mouth;
    private int day;
    private String sender;
    private String receiver;

    Message() { }

    Message(String message, String date, String sender) {
        this.message = message;
        String[] dates = date.split("/");
        int y = 0;
        for (int i = 0; i < dates[0].length(); i++) {
            y = y * 10 + dates[0].charAt(i) - '0';
        }
        year = y;
        int m = 0;
        for (int i = 0; i < dates[1].length(); i++) {
            m = m * 10 + dates[1].charAt(i) - '0';
        }
        mouth = m;
        int d = 0;
        for (int i = 0; i < dates[2].length(); i++) {
            d = d * 10 + dates[2].charAt(i) - '0';
        }
        day = d;
        this.sender = sender;
    }

    Message(String message, String date, String sender, String receiver) {
        this.message = message;
        String[] dates = date.split("/");
        int y = 0;
        for (int i = 0; i < dates[0].length(); i++) {
            y = y * 10 + dates[0].charAt(i) - '0';
        }
        year = y;
        int m = 0;
        for (int i = 0; i < dates[1].length(); i++) {
            m = m * 10 + dates[1].charAt(i) - '0';
        }
        mouth = m;
        int d = 0;
        for (int i = 0; i < dates[2].length(); i++) {
            d = d * 10 + dates[2].charAt(i) - '0';
        }
        day = d;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public int getYear() {
        return year;
    }

    public int getMouth() {
        return mouth;
    }

    public int getDay() {
        return day;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
