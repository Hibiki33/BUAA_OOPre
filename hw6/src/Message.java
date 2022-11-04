public class Message {
    private String message;
    private int[] dates;
    private String sender;
    private String receiver;

    Message() { }

    Message(String message, String date, String sender) {
        this.message = message;
        String[] d = date.split("/");
        dates = new int[3];
        int year = 0;
        for (int i = 0; i < d[0].length(); i++) {
            year = year * 10 + d[0].charAt(i) - '0';
        }
        dates[0] = year;
        int m = 0;
        for (int i = 0; i < d[1].length(); i++) {
            m = m * 10 + d[1].charAt(i) - '0';
        }
        dates[1] = m;
        int day = 0;
        for (int i = 0; i < d[2].length(); i++) {
            day = day * 10 + d[2].charAt(i) - '0';
        }
        dates[2] = day;
        this.sender = sender;
    }

    Message(String message, String date, String sender, String receiver) {
        this.message = message;
        String[] d = date.split("/");
        dates = new int[3];
        int year = 0;
        for (int i = 0; i < d[0].length(); i++) {
            year = year * 10 + d[0].charAt(i) - '0';
        }
        dates[0] = year;
        int m = 0;
        for (int i = 0; i < d[1].length(); i++) {
            m = m * 10 + d[1].charAt(i) - '0';
        }
        dates[1] = m;
        int day = 0;
        for (int i = 0; i < d[2].length(); i++) {
            day = day * 10 + d[2].charAt(i) - '0';
        }
        dates[2] = day;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public int getYear() {
        return dates[0];
    }

    public int getMonth() {
        return dates[1];
    }

    public int getDay() {
        return dates[2];
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
