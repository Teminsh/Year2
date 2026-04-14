package ADS.Semester2.Lab10;

import java.util.List;

/*
Задача о бросании яиц: Дано 100-этажное здание. Если яйцо сбросить с высоты N-го этажа (или с большей высоты),
оно разобьется. Если его бросить с любого меньшего этажа, оно не разобьется.
У вас есть два яйца. Найдите N за минимальное количество бросков.
*/

public class Main {
    public static void main(String[] args) {
        EggDropSolver solver = new EggDropSolver();
        int floors = 100;

        int minAttempts = solver.minAttempts(floors);
        List<Integer> strategy = solver.buildStrategy(floors);

        System.out.println("Лабораторная работа 10. Задача о бросании яиц");
        System.out.println("Количество этажей: " + floors);
        System.out.println("Минимальное количество бросков в худшем случае: " + minAttempts);
        System.out.println("Этажи для бросков первым яйцом: " + strategy);
        System.out.println();

        System.out.println("Демонстрационные тесты:");
        int[] demoTests = {1, 14, 37, 68, 100};

        for (int n : demoTests) {
            SearchResult result = solver.findCriticalFloor(floors, n, false);
            System.out.println("N = " + n
                    + " -> найдено: " + result.getCriticalFloor()
                    + ", бросков: " + result.getThrowsCount());
        }

        System.out.println();
        System.out.println("Подробный разбор одного теста (N = 37):");
        SearchResult detailed = solver.findCriticalFloor(floors, 37, true);
        System.out.print(detailed.getLog());
        System.out.println("Итог: N = " + detailed.getCriticalFloor()
                + ", всего бросков = " + detailed.getThrowsCount());

        System.out.println();
        System.out.println("Полная автоматическая проверка для всех N от 1 до 100:");

        boolean allTestsPassed = true;
        int worstCaseThrows = 0;
        int worstCaseFloor = -1;

        for (int n = 1; n <= floors; n++) {
            SearchResult result = solver.findCriticalFloor(floors, n, false);

            if (result.getCriticalFloor() != n || result.getThrowsCount() > minAttempts) {
                allTestsPassed = false;
                System.out.println("Ошибка! Для N = " + n
                        + " найдено: " + result.getCriticalFloor()
                        + ", бросков: " + result.getThrowsCount());
                break;
            }

            if (result.getThrowsCount() > worstCaseThrows) {
                worstCaseThrows = result.getThrowsCount();
                worstCaseFloor = n;
            }
        }

        if (allTestsPassed) {
            System.out.println("Все тесты пройдены успешно.");
            System.out.println("Максимум бросков на тестах: " + worstCaseThrows);
            System.out.println("Один из худших случаев: N = " + worstCaseFloor);
        } else {
            System.out.println("Тестирование выявило ошибку.");
        }
    }
}
