package ADS.Semester2.Lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KMPSearcher {

    private final PrefixFunction prefixFunction = new PrefixFunction();

    public List<Integer> searchAll(String text, String pattern) {
        validate(text, pattern);

        if (pattern.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> result = new ArrayList<>();
        int[] lps = prefixFunction.buildLps(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    result.add(i - j);
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return result;
    }

    public int searchFirst(String text, String pattern) {
        List<Integer> matches = searchAll(text, pattern);
        return matches.isEmpty() ? -1 : matches.get(0);
    }

    private void validate(String text, String pattern) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не должен быть null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("Образец не должен быть null");
        }
    }
}