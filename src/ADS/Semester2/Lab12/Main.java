package ADS.Semester2.Lab12;

import java.util.Arrays;
import java.util.List;

public class Main {
    void main() {
        System.out.println("=== Тестирование Лабораторной №12: Дискретная задача о рюкзаке ===");

        System.out.println("\n[Тест 1] Классический набор");
        List<Item> items1 = Arrays.asList(
                new Item("Ноутбук", 3, 2000),
                new Item("Гитара", 1, 1500),
                new Item("Палатка", 2, 1000),
                new Item("Фонарик", 1, 500)
        );
        int capacity1 = 4;
        runTest(capacity1, items1);

        System.out.println("\n[Тест 2] Предметы с одинаковым весом, но разной ценностью");
        List<Item> items2 = Arrays.asList(
                new Item("Камень", 2, 10),
                new Item("Серебро", 2, 100),
                new Item("Золото", 2, 1000)
        );
        int capacity2 = 4;
        runTest(capacity2, items2);
    }

    private static void runTest(int capacity, List<Item> items) {
        System.out.println("Доступная вместимость: " + capacity);
        System.out.println("Доступные предметы:");
        for (Item item : items) {
            System.out.println(" - " + item);
        }

        KnapsackResult result = KnapsackSolver.solve(capacity, items);

        System.out.println(">>> РЕЗУЛЬТАТ:");
        System.out.println("Максимальная ценность: " + result.getTotalValue());
        System.out.println("Взятые предметы:");
        int totalWeight = 0;
        for (Item item : result.getSelectedItems()) {
            System.out.println(" + " + item);
            totalWeight += item.getWeight();
        }
        System.out.println("Итоговый занятый вес: " + totalWeight + " / " + capacity);
        System.out.println("---------------------------------------------------------");
    }
}