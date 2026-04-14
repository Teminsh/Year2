package ADS.Semester2.Lab5;

import java.util.ArrayList;
import java.util.List;

public class BoyerMooreSearch {

    public SearchResult search(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        int comparisons = 0;

        if (pattern == null || text == null) {
            throw new IllegalArgumentException("Текст и образец не должны быть null");
        }

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            for (int i = 0; i <= n; i++) {
                positions.add(i);
            }
            return new SearchResult(positions, comparisons);
        }

        if (m > n) {
            return new SearchResult(positions, comparisons);
        }

        BadCharacterTable badCharacterTable = new BadCharacterTable(pattern);
        int[] goodSuffixShift = GoodSuffixTable.build(pattern);

        int shift = 0;

        while (shift <= n - m) {
            int j = m - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                comparisons++;
                j--;
            }

            if (j < 0) {
                positions.add(shift);
                shift += goodSuffixShift[0];
            } else {
                comparisons++;
                char badChar = text.charAt(shift + j);

                int badCharShift = j - badCharacterTable.getLastOccurrence(badChar);
                int goodSuffixValue = goodSuffixShift[j + 1];

                shift += Math.max(1, Math.max(badCharShift, goodSuffixValue));
            }
        }

        return new SearchResult(positions, comparisons);
    }
}
