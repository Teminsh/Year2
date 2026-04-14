package ADS.Semester2.Lab9;

import java.util.List;
import java.util.Scanner;

/*
Задача коммивояжера - поиск кратчайшего возможного маршрута,
который проходит через заданный набор городов и возвращается в начальный город.
Маршруты заданы матрицей связности.
*/
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        printBanner();
        boolean running = true;
        while (running) {
            printMenu();
            switch (readInt()) {
                case 1 -> runTests();
                case 2 -> runManual();
                case 0 -> { System.out.println("Выход."); running = false; }
                default -> System.out.println("  [!] Неверный пункт меню.");
            }
        }
    }

    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║          Задача Коммивояжёра  (TSP)              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    private static void printMenu() {
        System.out.println("\n──────────────────────────────────────");
        System.out.println("  1. Запустить встроенные тесты");
        System.out.println("  2. Ввести матрицу вручную");
        System.out.println("  0. Выход");
        System.out.print("  Выберите: ");
    }

    private static void runTests() {

        System.out.println("\n╔═══ Тест 1: 4 города (полный граф) ═══╗");
        int[][] t1 = {
                {  0, 10, 15, 20 },
                { 10,  0, 35, 25 },
                { 15, 35,  0, 30 },
                { 20, 25, 30,  0 }
        };
        printMatrix(t1);
        printResult(t1);
        System.out.println("  ► Ожидаемая длина: 80  (0→2→3→1→0 или 0→1→3→2→0)");

        System.out.println("\n╔═══ Тест 2: 5 городов ═══╗");
        int[][] t2 = {
                { 0, 3, 1, 5, 8 },
                { 3, 0, 6, 7, 9 },
                { 1, 6, 0, 4, 2 },
                { 5, 7, 4, 0, 3 },
                { 8, 9, 2, 3, 0 }
        };
        printMatrix(t2);
        printResult(t2);
        System.out.println("  ► Ожидаемая длина: 16  (0→2→4→3→1→0)");

        System.out.println("\n╔═══ Тест 3: нет гамильтонова цикла ═══╗");
        int[][] t3 = {
                { 0, 1, 0 },
                { 0, 0, 1 },
                { 0, 0, 0 }
        };
        printMatrix(t3);
        printResult(t3);
        System.out.println("  ► Ожидаемо: решение не существует");
    }

    private static void runManual() {
        System.out.print("\nВведите число городов: ");
        int n = readInt();
        if (n <= 0) { System.out.println("  [!] Недопустимое число городов."); return; }
        int[][] matrix = new int[n][n];
        System.out.println("Введите матрицу " + n + "×" + n + " построчно (0 = нет ребра):");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                matrix[i][j] = readInt();
        printMatrix(matrix);
        printResult(matrix);
    }

    private static void printResult(int[][] matrix) {
        TSPSolver solver = new TSPSolver(matrix);
        if (solver.hasSolution()) {
            List<Integer> path = solver.getBestPath();
            System.out.print("  Маршрут: ");
            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));
                if (i < path.size() - 1) System.out.print(" → ");
            }
            System.out.println();
            System.out.println("  Длина маршрута: " + solver.getMinCost());
        } else {
            System.out.println("  Гамильтонов цикл не существует — решения нет.");
        }
    }

    private static void printMatrix(int[][] m) {
        int n = m.length;
        System.out.print("      ");
        for (int j = 0; j < n; j++) System.out.printf("%4d", j);
        System.out.println();
        System.out.print("     +");
        System.out.println("----".repeat(n));
        for (int i = 0; i < n; i++) {
            System.out.printf("  %2d |", i);
            for (int j = 0; j < n; j++) System.out.printf("%4d", m[i][j]);
            System.out.println();
        }
    }

    private static int readInt() {
        while (!sc.hasNextInt()) sc.next();
        return sc.nextInt();
    }
}

