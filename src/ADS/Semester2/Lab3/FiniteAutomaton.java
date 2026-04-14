package ADS.Semester2.Lab3;

import java.util.LinkedHashSet;
import java.util.Set;

public class FiniteAutomaton {
    private final String pattern;
    private final char[] alphabet;
    private final int[][] transitionTable;

    public FiniteAutomaton(String pattern, String text) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Образец не должен быть пустым.");
        }

        this.pattern = pattern;
        this.alphabet = buildAlphabet(pattern, text);
        this.transitionTable = buildTransitionTable();
    }

    private char[] buildAlphabet(String pattern, String text) {
        Set<Character> set = new LinkedHashSet<>();

        for (char c : pattern.toCharArray()) {
            set.add(c);
        }

        for (char c : text.toCharArray()) {
            set.add(c);
        }

        char[] result = new char[set.size()];
        int i = 0;
        for (char c : set) {
            result[i++] = c;
        }
        return result;
    }

    private int[][] buildTransitionTable() {
        int m = pattern.length();
        int[][] table = new int[m + 1][alphabet.length];

        for (int state = 0; state <= m; state++) {
            for (int i = 0; i < alphabet.length; i++) {
                char currentChar = alphabet[i];
                table[state][i] = getNextState(state, currentChar);
            }
        }

        return table;
    }

    private int getNextState(int state, char currentChar) {
        int m = pattern.length();

        if (state < m && currentChar == pattern.charAt(state)) {
            return state + 1;
        }

        for (int nextState = Math.min(m, state + 1); nextState > 0; nextState--) {
            if (pattern.charAt(nextState - 1) == currentChar) {
                boolean match = true;

                for (int i = 0; i < nextState - 1; i++) {
                    if (pattern.charAt(i) != pattern.charAt(state - nextState + 1 + i)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    return nextState;
                }
            }
        }

        return 0;
    }

    public int[][] getTransitionTable() {
        return transitionTable;
    }

    public char[] getAlphabet() {
        return alphabet;
    }

    public String getPattern() {
        return pattern;
    }

    public int getNextStateByChar(int currentState, char c) {
        int index = getCharIndex(c);
        if (index == -1) {
            return 0;
        }
        return transitionTable[currentState][index];
    }

    private int getCharIndex(char c) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public void printTransitionTable() {
        System.out.println("Таблица переходов конечного автомата:");
        System.out.print("Сост/Симв\t");
        for (char c : alphabet) {
            System.out.print(c + "\t");
        }
        System.out.println();

        for (int state = 0; state < transitionTable.length; state++) {
            System.out.print(state + "\t\t");
            for (int j = 0; j < transitionTable[state].length; j++) {
                System.out.print(transitionTable[state][j] + "\t");
            }
            System.out.println();
        }
    }
}