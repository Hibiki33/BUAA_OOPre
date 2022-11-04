public class Emoji implements Comparable<Emoji> {
    private String name;
    private String emojiId;
    private int count;

    Emoji() {}

    Emoji(String name, String id, String cnt) {
        this.name = name;
        this.emojiId = id;
        count = 0;
        for (int i = 0; i < cnt.length(); i++) {
            count = count * 10 + cnt.charAt(i) - '0';
        }
    }

    public String getName() {
        return name;
    }

    public String getEmojiId() {
        return emojiId;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(Emoji other) {
        if (this.count > other.getCount()) {
            return -1;
        } else if (this.count == other.getCount()) {
            return this.name.compareTo(other.getName());
        }
        return 1;
    }
}
