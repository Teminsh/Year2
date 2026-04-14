package ADS.Semester2.Lab13;

import java.util.Arrays;
import java.util.List;

/*
Решить задачу о раскладке по ящикам
*/

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Лабораторная работа 13: NP-полные задачи (Расклад по ящикам) ===\n");

        System.out.println("--- Тест 1: Базовый набор предметов ---");
        List<Integer> items1 = Arrays.asList(4, 8, 1, 4, 2, 1);
        int capacity1 = 10;
        runTestCase(items1, capacity1);

        System.out.println("--- Тест 2: Идеальное заполнение (без пустого места) ---");
        List<Integer> items2 = Arrays.asList(5, 5, 5, 5, 5, 5);
        int capacity2 = 10;
        runTestCase(items2, capacity2);

        System.out.println("--- Тест 3: Смешанные и неудобные веса ---");
        List<Integer> items3 = Arrays.asList(9, 8, 2, 2, 5, 4);
        int capacity3 = 10;
        runTestCase(items3, capacity3);
    }

    private static void runTestCase(List<Integer> items, int binCapacity) {
        System.out.println("Предметы (веса): " + items);
        System.out.println("Вместимость 1 ящика: " + binCapacity);

        long startTime = System.nanoTime();
        List<Bin> result = BinPackingSolver.solveFirstFitDecreasing(items, binCapacity);
        long endTime = System.nanoTime();

        System.out.println("Использовано ящиков: " + result.size());
        for (int i = 0; i < result.size(); i++) {
            System.out.println("  " + (i + 1) + "-й " + result.get(i));
        }
        System.out.println("Время выполнения алгоритма: " + (endTime - startTime) + " нс\n");
    }
}