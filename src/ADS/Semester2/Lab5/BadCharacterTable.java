package ADS.Semester2.Lab5;

import java.util.HashMap;
import java.util.Map;

public class BadCharacterTable {
    private final Map<Character, Integer> lastOccurrence;

    public BadCharacterTable(String pattern) {
        lastOccurrence = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            lastOccurrence.put(pattern.charAt(i), i);
        }
    }

    public int getLastOccurrence(char c) {
        return lastOccurrence.getOrDefault(c, -1);
    }
}