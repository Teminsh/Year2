package ADS.Semester2.Lab6;

import java.util.ArrayList;
import java.util.List;

public class RabinKarpSearcher {
    private static final int BASE = 256;
    private static final int MOD = 101;

    public SearchResult search(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("Текст и образец не должны быть null.");
        }

        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Образец не должен быть пустым.");
        }

        if (pattern.length() > text.length()) {
            return new SearchResult(false, new ArrayList<>(), 0);
        }

        int n = text.length();
        int m = pattern.length();

        int patternHash = 0;
        int textHash = 0;
        int h = 1;
        int comparisons = 0;
        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < m - 1; i++) {
            h = (h * BASE) % MOD;
        }

        for (int i = 0; i < m; i++) {
            patternHash = (BASE * patternHash + pattern.charAt(i)) % MOD;
            textHash = (BASE * textHash + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                boolean match = true;

                for (int j = 0; j < m; j++) {
                    comparisons++;
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    positions.add(i);
                }
            }

            if (i < n - m) {
                textHash = (BASE * (textHash - text.charAt(i) * h) + text.charAt(i + m)) % MOD;

                if (textHash < 0) {
                    textHash += MOD;
                }
            }
        }

        return new SearchResult(!positions.isEmpty(), positions, comparisons);
    }
}