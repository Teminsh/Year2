package ADS.Semester2.Lab6;

/*
6.	Реализовать алгоритм Рабина для поиска по образцу
*/

public class Main {
    public static void main(String[] args) {
        RabinKarpSearcher searcher = new RabinKarpSearcher();

        runTest(searcher,
                "abracadabra",
                "cada",
                "Тест 1: одно совпадение");

        runTest(searcher,
                "aaaaaa",
                "aa",
                "Тест 2: несколько совпадений");

        runTest(searcher,
                "hello world",
                "java",
                "Тест 3: совпадений нет");

        runTest(searcher,
                "алгоритм рабина карпа работает быстро",
                "карпа",
                "Тест 4: поиск русского слова");

        runTest(searcher,
                "123451234512345",
                "345",
                "Тест 5: поиск по числовой строке");
    }

    private static void runTest(RabinKarpSearcher searcher, String text, String pattern, String testName) {
        System.out.println("====================================");
        System.out.println(testName);
        System.out.println("Текст:    " + text);
        System.out.println("Образец:  " + pattern);

        SearchResult result = searcher.search(text, pattern);

        System.out.println(result);
        System.out.println();
    }
}