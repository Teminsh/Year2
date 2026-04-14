package ADS.Semester2.Lab8;

import java.util.Arrays;
import java.util.Scanner;

/*
Проблема размена монет:
поиск количества способов внести сдачу на заданную сумму денег,
используя заданный набор номиналов монет.
*/

public class Main
{
    static void main()
    {
        run();
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void run() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     Лаб. 8 — Проблема размена монет    ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("Задача: найти количество способов внести сдачу");
        System.out.println("на заданную сумму заданным набором номиналов монет.\n");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Ваш выбор: ");
            System.out.println();
            switch (choice) {
                case 1 -> runAutoTests();
                case 2 -> runManualCount();
                case 3 -> runManualWithCombinations();
                case 0 -> running = false;
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
            System.out.println();
        }
        System.out.println("До свидания!");
    }

    private static void printMenu() {
        System.out.println("──────────────────────────────────────────");
        System.out.println(" 1. Автотесты");
        System.out.println(" 2. Ввод вручную — только количество");
        System.out.println(" 3. Ввод вручную — количество + комбинации");
        System.out.println(" 0. Выход");
        System.out.println("──────────────────────────────────────────");
    }

    private static void runAutoTests() {
        System.out.println("=== Автоматическое тестирование ===\n");

        Object[][] tests = {
                {"Базовый: сумма=4, монеты=[1,2,3]",
                        4,  new int[]{1, 2, 3},  4L},
                {"Классика: сумма=5, монеты=[1,2,5]",
                        5,  new int[]{1, 2, 5},  4L},
                {"Сумма=10, монеты=[1,5,10]",
                        10, new int[]{1, 5, 10}, 4L},
                {"Граница: сумма=0 → 1 способ (взять ничего)",
                        0,  new int[]{1, 2, 3},  1L},
                {"Граница: нечётная сумма с чётными монетами → 0",
                        3,  new int[]{2},         0L},
                {"Монеты США [1,5,10,25], сумма=100",
                        100, new int[]{1, 5, 10, 25}, 242L},
        };

        int passed = 0;
        for (Object[] t : tests) {
            String desc    = (String) t[0];
            int    amount  = (int)    t[1];
            int[]  coins   = (int[])  t[2];
            long   expected= (long)   t[3];

            long result = CoinChange.countWays(amount, coins);
            boolean ok = (result == expected);
            if (ok) passed++;

            System.out.printf("  %-50s | ожид.: %-5d | факт: %-5d | %s%n",
                    desc, expected, result, ok ? "✓ PASSED" : "✗ FAILED");
        }

        System.out.printf("%nРезультат: %d/%d тестов пройдено%n", passed, tests.length);

        System.out.println("\n─── Детальный разбор ───");
        CoinChange.printAllCombinations(4,  new int[]{1, 2, 3});
        System.out.println();
        CoinChange.printAllCombinations(5,  new int[]{1, 2, 5});
    }

    private static void runManualCount() {
        System.out.println("=== Подсчёт способов (ручной ввод) ===");
        int   amount = readInt("Введите сумму: ");
        int[] coins  = readCoins();
        long  result = CoinChange.countWays(amount, coins);
        System.out.printf("Количество способов набрать %d из %s: %d%n",
                amount, Arrays.toString(coins), result);
    }

    private static void runManualWithCombinations() {
        System.out.println("=== Все комбинации (ручной ввод) ===");
        System.out.println("Совет: используйте сумму ≤ 20, иначе вывод очень длинный.");
        int   amount = readInt("Введите сумму: ");
        int[] coins  = readCoins();
        long  result = CoinChange.countWays(amount, coins);
        CoinChange.printAllCombinations(amount, coins);
        System.out.printf("Итого способов: %d%n", result);
    }

    private static int[] readCoins() {
        System.out.print("Введите номиналы монет через пробел (пример: 1 2 5): ");
        String   line  = scanner.nextLine().trim();
        String[] parts = line.split("\\s+");
        int[]    coins = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            coins[i] = Integer.parseInt(parts[i]);
        }
        return coins;
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Введите целое число: ");
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}