package ADS.Semester2.Lab7;

/*
Задача о самом большом подмассиве:
поиск непрерывного подмассива в одномерном массиве чисел с наибольшей суммой.
*/

import java.util.Scanner;

public class Main {

    private static final MaxSubarraySolver solver = new MaxSubarraySolver();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   Лаб. 7 — Задача о максимальном подмассиве  ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        while (running) {
            printMenu();
            System.out.print("Выбор: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> runPredefinedTests();
                case "2" -> runCustomInput(scanner);
                case "0" -> running = false;
                default  -> System.out.println("[!] Неверный ввод. Попробуйте снова.\n");
            }
        }

        System.out.println("Выход.");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n" +
                           "┌───────────────────────────────────────┐");
        System.out.println("│  1. Запустить предустановленные тесты │");
        System.out.println("│  2. Ввести свой массив                │");
        System.out.println("│  0. Выход                             │");
        System.out.println("└───────────────────────────────────────┘");
    }

    private static void runPredefinedTests() {
        System.out.println("\n======= ПРЕДУСТАНОВЛЕННЫЕ ТЕСТЫ =======");

        runTest(1, "Классический",
                new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}, 6);

        runTest(2, "Все положительные",
                new int[]{1, 2, 3, 4, 5}, 15);

        runTest(3, "Все отрицательные",
                new int[]{-3, -1, -4, -1, -5}, -1);

        runTest(4, "Один элемент",
                new int[]{42}, 42);

        runTest(5, "Максимум в начале",
                new int[]{5, 4, -100, 1, 2}, 9);

        runTest(6, "Максимум в конце",
                new int[]{-5, 1, 2, 3, 100}, 106);

        System.out.println("=======================================");
    }

    private static void runTest(int num, String name, int[] array, int expected) {
        SubarrayResult result = solver.solve(array);
        boolean passed = result.getMaxSum() == expected;

        System.out.printf("%nТест %d: %s%n", num, name);
        System.out.print("Массив  : ");
        printArray(array);
        System.out.println(result);
        System.out.printf("Ожидалось: %d  |  %s%n",
                expected, passed ? "✓ PASSED" : "✗ FAILED");
    }

    private static void runCustomInput(Scanner scanner) {
        System.out.println("\nВведите элементы через пробел (целые числа):");
        System.out.print("> ");
        String line = scanner.nextLine().trim();

        if (line.isEmpty()) { System.out.println("[!] Пустой ввод."); return; }

        try {
            String[] tokens = line.split("\\s+");
            int[] array = new int[tokens.length];
            for (int i = 0; i < tokens.length; i++)
                array[i] = Integer.parseInt(tokens[i]);

            System.out.println();
            System.out.println(solver.solve(array));
        } catch (NumberFormatException e) {
            System.out.println("[!] Ошибка: только целые числа через пробел.");
        }
    }

    private static void printArray(int[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
