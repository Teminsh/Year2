package ADS.Semester2.Lab4;

/*
4.	Реализовать алгоритм Кнута-Морриса-Пратта для поиска по образцу
*/

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        KMPSearcher kmpSearcher = new KMPSearcher();
        PrefixFunction prefixFunction = new PrefixFunction();

        int total = 5;
        int passed = 0;

        if (runTest(kmpSearcher, "ababcabcabababd", "ababd", List.of(10))) passed++;
        if (runTest(kmpSearcher, "aaaaa", "aa", List.of(0, 1, 2, 3))) passed++;
        if (runTest(kmpSearcher, "hello world", "world", List.of(6))) passed++;
        if (runTest(kmpSearcher, "abcdef", "gh", List.of())) passed++;
        if (runTest(kmpSearcher, "abcabcabcabc", "abcabc", List.of(0, 3, 6))) passed++;

        System.out.println("Префикс-функция для образца \"ababaca\": "
                + Arrays.toString(prefixFunction.buildLps("ababaca")));

        System.out.println("\nИТОГ: пройдено " + passed + " из " + total + " тестов.");
    }

    private static boolean runTest(KMPSearcher kmpSearcher, String text, String pattern, List<Integer> expected) {
        List<Integer> actual = kmpSearcher.searchAll(text, pattern);
        boolean ok = actual.equals(expected);

        System.out.println("Текст:     \"" + text + "\"");
        System.out.println("Образец:   \"" + pattern + "\"");
        System.out.println("Ожидали:   " + expected);
        System.out.println("Получили:  " + actual);
        System.out.println("Результат: " + (ok ? "OK" : "ERROR"));
        System.out.println("--------------------------------------");

        return ok;
    }
}