package ADS.Semester2.Lab3;

public class PatternSearchResult {
    private final int index;
    private final String matchedSubstring;

    public PatternSearchResult(int index, String matchedSubstring) {
        this.index = index;
        this.matchedSubstring = matchedSubstring;
    }

    public int getIndex() {
        return index;
    }

    public String getMatchedSubstring() {
        return matchedSubstring;
    }

    @Override
    public String toString() {
        return "Совпадение найдено по индексу: " + index +
                ", подстрока: \"" + matchedSubstring + "\"";
    }
}