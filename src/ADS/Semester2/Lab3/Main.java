package ADS.Semester2.Lab3;

/*
3.	Реализовать алгоритм поиска по образцу с помощью конечного автомата
*/

import java.util.List;

public class Main {
    static void main() {
        PatternMatcher matcher = new PatternMatcher();

        System.out.println("========== ТЕСТ 1: несколько вхождений ==========");
        String text1 = "ABABABACABA";
        String pattern1 = "ABA";
        matcher.printSearchResults(text1, pattern1);

        System.out.println("\n========== ТЕСТ 2: одно вхождение ==========");
        String text2 = "HELLO WORLD";
        String pattern2 = "WORLD";
        matcher.printSearchResults(text2, pattern2);

        System.out.println("\n========== ТЕСТ 3: совпадений нет ==========");
        String text3 = "ABCDEFG";
        String pattern3 = "HIJ";
        matcher.printSearchResults(text3, pattern3);

        System.out.println("\n========== ТЕСТ 4: перекрывающиеся вхождения ==========");
        String text4 = "AAAAA";
        String pattern4 = "AAA";
        matcher.printSearchResults(text4, pattern4);

        System.out.println("\n========== КРАТКАЯ ПРОВЕРКА ЧЕРЕЗ СПИСОК ==========");
        List<PatternSearchResult> results = matcher.search("AABAACAADAABAABA", "AABA");
        for (PatternSearchResult result : results) {
            System.out.println(result);
        }
    }
}