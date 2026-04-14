package ADS.Semester2.Lab5;

/*
5.	Реализовать алгоритм Бойера-Мура для поиска по образцу
*/

public class Main {

    public static void main(String[] args) {
        BoyerMooreSearch boyerMooreSearch = new BoyerMooreSearch();

        runTest(boyerMooreSearch, "ABAAABCDABCABC", "ABC");
        runTest(boyerMooreSearch, "AABAACAADAABAABA", "AABA");
        runTest(boyerMooreSearch, "HELLOWORLD", "JAVA");
        runTest(boyerMooreSearch, "PATTERN", "PATTERN");
        runTest(boyerMooreSearch, "SHORT", "LONGPATTERN");
        runTest(boyerMooreSearch, "BANANA", "ANA");
    }

    private static void runTest(BoyerMooreSearch searcher, String text, String pattern) {
        SearchResult result = searcher.search(text, pattern);

        System.out.println("========================================");
        System.out.println("Текст:    " + text);
        System.out.println("Образец:  " + pattern);

        if (result.isFound()) {
            System.out.println("Совпадения найдены на позициях: " + result.getPositions());
        } else {
            System.out.println("Совпадений не найдено.");
        }

        System.out.println("Количество сравнений: " + result.getComparisons());
        System.out.println("========================================");
        System.out.println();
    }
}