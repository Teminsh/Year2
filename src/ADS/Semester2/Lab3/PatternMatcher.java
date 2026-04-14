package ADS.Semester2.Lab3;

import java.util.ArrayList;
import java.util.List;

public class PatternMatcher {

    public List<PatternSearchResult> search(String text, String pattern) {
        if (text == null) {
            throw new IllegalArgumentException("Текст не должен быть null.");
        }

        FiniteAutomaton automaton = new FiniteAutomaton(pattern, text);
        List<PatternSearchResult> results = new ArrayList<>();

        int state = 0;
        int patternLength = pattern.length();

        for (int i = 0; i < text.length(); i++) {
            state = automaton.getNextStateByChar(state, text.charAt(i));

            if (state == patternLength) {
                int startIndex = i - patternLength + 1;
                results.add(new PatternSearchResult(
                        startIndex,
                        text.substring(startIndex, startIndex + patternLength)
                ));
            }
        }

        return results;
    }

    public void printSearchResults(String text, String pattern) {
        FiniteAutomaton automaton = new FiniteAutomaton(pattern, text);
        automaton.printTransitionTable();

        List<PatternSearchResult> results = search(text, pattern);

        System.out.println("\nТекст: " + text);
        System.out.println("Образец: " + pattern);

        if (results.isEmpty()) {
            System.out.println("Совпадений не найдено.");
        } else {
            System.out.println("Найденные совпадения:");
            for (PatternSearchResult result : results) {
                System.out.println(result);
            }
        }
    }
}