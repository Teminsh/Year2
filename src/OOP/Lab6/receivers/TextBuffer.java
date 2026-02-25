package OOP.Lab6.receivers;

public class TextBuffer {
    private final StringBuilder sb = new StringBuilder();

    public void append(char ch) {
        sb.append(ch);
    }

    public void backspace() {
        if (sb.isEmpty()) return;
        sb.deleteCharAt(sb.length() - 1);
    }

    public String snapshot() {
        return sb.toString();
    }
}
