package ADS.Semester2.Lab11;

import java.util.Scanner;

/*
Решить задачу о раскраске графа
*/

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════╗");
            System.out.println("║  Лаб. 11 — Раскраска графа       ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║  1. Автоматическое тестирование  ║");
            System.out.println("║  2. Ввод графа вручную           ║");
            System.out.println("║  0. Выход                        ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("Выбор: ");
            switch (sc.nextInt()) {
                case 1 -> runTests();
                case 2 -> runManual(sc);
                case 0 -> { System.out.println("Выход."); return; }
                default -> System.out.println("Неверный выбор.\n");
            }
        }
    }

    private static void runTests() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║       АВТОМАТИЧЕСКОЕ ТЕСТИРОВАНИЕ        ║");
        System.out.println("╚══════════════════════════════════════════╝\n");

        int passed = 0, total = 0;

        total++;
        System.out.println("── Тест 1: Треугольник K3 (ожидается χ=3) ──");
        Graph g1 = new Graph(3);
        g1.addEdge(0, 1); g1.addEdge(1, 2); g1.addEdge(0, 2);
        g1.printAdjacencyMatrix();
        GraphColoring gc1 = new GraphColoring(g1);
        gc1.printResult();
        if (gc1.getChromaticNumber() == 3) { System.out.println("ТЕСТ 1: ✓ ПРОЙДЕН\n"); passed++; }
        else System.out.println("ТЕСТ 1: ✗ ПРОВАЛЕН\n");

        total++;
        System.out.println("── Тест 2: Чётный цикл C4 (ожидается χ=2) ──");
        Graph g2 = new Graph(4);
        g2.addEdge(0, 1); g2.addEdge(1, 2); g2.addEdge(2, 3); g2.addEdge(3, 0);
        g2.printAdjacencyMatrix();
        GraphColoring gc2 = new GraphColoring(g2);
        gc2.printResult();
        if (gc2.getChromaticNumber() == 2) { System.out.println("ТЕСТ 2: ✓ ПРОЙДЕН\n"); passed++; }
        else System.out.println("ТЕСТ 2: ✗ ПРОВАЛЕН\n");

        total++;
        System.out.println("── Тест 3: Полный граф K4 (ожидается χ=4) ──");
        Graph g3 = new Graph(4);
        for (int i = 0; i < 4; i++)
            for (int j = i + 1; j < 4; j++) g3.addEdge(i, j);
        g3.printAdjacencyMatrix();
        GraphColoring gc3 = new GraphColoring(g3);
        gc3.printResult();
        if (gc3.getChromaticNumber() == 4) { System.out.println("ТЕСТ 3: ✓ ПРОЙДЕН\n"); passed++; }
        else System.out.println("ТЕСТ 3: ✗ ПРОВАЛЕН\n");

        total++;
        System.out.println("── Тест 4: Граф без рёбер, 5 вершин (ожидается χ=1) ──");
        Graph g4 = new Graph(5);
        g4.printAdjacencyMatrix();
        GraphColoring gc4 = new GraphColoring(g4);
        gc4.printResult();
        if (gc4.getChromaticNumber() == 1) { System.out.println("ТЕСТ 4: ✓ ПРОЙДЕН\n"); passed++; }
        else System.out.println("ТЕСТ 4: ✗ ПРОВАЛЕН\n");

        total++;
        System.out.println("── Тест 5: Нечётный цикл C5 (ожидается χ=3) ──");
        Graph g5 = new Graph(5);
        g5.addEdge(0,1); g5.addEdge(1,2); g5.addEdge(2,3);
        g5.addEdge(3,4); g5.addEdge(4,0);
        g5.printAdjacencyMatrix();
        GraphColoring gc5 = new GraphColoring(g5);
        gc5.printResult();
        if (gc5.getChromaticNumber() == 3) { System.out.println("ТЕСТ 5: ✓ ПРОЙДЕН\n"); passed++; }
        else System.out.println("ТЕСТ 5: ✗ ПРОВАЛЕН\n");

        total++;
        System.out.println("── Тест 6: Двудольный K2,3 (ожидается χ=2) ──");
        Graph g6 = new Graph(5);
        g6.addEdge(0,2); g6.addEdge(0,3); g6.addEdge(0,4);
        g6.addEdge(1,2); g6.addEdge(1,3); g6.addEdge(1,4);
        g6.printAdjacencyMatrix();
        GraphColoring gc6 = new GraphColoring(g6);
        gc6.printResult();
        if (gc6.getChromaticNumber() == 2) { System.out.println("ТЕСТ 6: ✓ ПРОЙДЕН\n"); passed++; }
        else System.out.println("ТЕСТ 6: ✗ ПРОВАЛЕН\n");

        System.out.printf("═══════════════════════════════════%n");
        System.out.printf("Итог: %d/%d тестов пройдено%n%n", passed, total);
    }

    private static void runManual(Scanner sc) {
        System.out.print("Введите количество вершин: ");
        int n = sc.nextInt();
        System.out.print("Введите количество рёбер: ");
        int m = sc.nextInt();
        Graph g = new Graph(n);
        System.out.println("Введите рёбра (u v), вершины нумеруются с 0:");
        for (int i = 0; i < m; i++) {
            System.out.printf("  Ребро %d: ", i + 1);
            int u = sc.nextInt(), v = sc.nextInt();
            g.addEdge(u, v);
        }
        System.out.println("\nМатрица смежности:");
        g.printAdjacencyMatrix();
        System.out.println();
        new GraphColoring(g).printResult();
    }
}
