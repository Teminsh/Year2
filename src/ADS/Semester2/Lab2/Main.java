package ADS.Semester2.Lab2;

/*

Записать алгоритмы нахождения точек пересечения двух прямых,
прямой и отрезка, двух отрезков, прямой и окружности, отрезка и окружности,
двух окружностей. Данные алгоритмы используются при решении следующей задачи:
2.	Дано N точек координатами (X,Y).
Выяснить, есть ли в этом множестве точек координаты вложенных друг в друга треугольников.

*/

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║   Лаб. работа 2 — Алгоритмы пересечений + вложение   ║");
        System.out.println("╚══════════════════════════════════════════════════════╝\n");

        testLineLine();
        testLineSegment();
        testSegmentSegment();
        testLineCircle();
        testSegmentCircle();
        testCircleCircle();
        testNestedTriangles();
    }

    static void testLineLine() {
        System.out.println("▶ ТЕСТ 1: Пересечение двух прямых");
        Line l1 = new Line(1, -1, 0);
        Line l2 = new Line(1,  1, -4);
        List<Point> pts = IntersectionUtils.lineLine(l1, l2);
        System.out.println("  l1: x - y = 0 (y=x)");
        System.out.println("  l2: x + y - 4 = 0");
        System.out.println("  Ожидаем: (2.00, 2.00)");
        System.out.println("  Результат: " + pts);

        Line l3 = new Line(1, 0, 0);   // x = 0
        Line l4 = new Line(1, 0, -3);  // x = 3
        System.out.println("  Параллельные прямые x=0 и x=3: " + IntersectionUtils.lineLine(l3, l4) + " (пусто)\n");
    }

    static void testLineSegment() {
        System.out.println("▶ ТЕСТ 2: Пересечение прямой и отрезка");
        Line l = new Line(0, 1, -1);                         // y = 1
        Segment s = new Segment(new Point(0, 0), new Point(3, 3));
        System.out.println("  Прямая y=1, отрезок (0,0)-(3,3)");
        System.out.println("  Ожидаем: (1.00, 1.00)");
        System.out.println("  Результат: " + IntersectionUtils.lineSegment(l, s));

        Segment s2 = new Segment(new Point(2, 2), new Point(4, 4));
        System.out.println("  Прямая y=1, отрезок (2,2)-(4,4): "
                + IntersectionUtils.lineSegment(l, s2) + " (пусто)\n");
    }

    static void testSegmentSegment() {
        System.out.println("▶ ТЕСТ 3: Пересечение двух отрезков");
        Segment s1 = new Segment(new Point(0, 0), new Point(2, 2));
        Segment s2 = new Segment(new Point(0, 2), new Point(2, 0));
        System.out.println("  s1: (0,0)-(2,2), s2: (0,2)-(2,0)");
        System.out.println("  Ожидаем: (1.00, 1.00)");
        System.out.println("  Результат: " + IntersectionUtils.segmentSegment(s1, s2));

        Segment s3 = new Segment(new Point(5, 0), new Point(5, 2));
        System.out.println("  s1 и отрезок (5,0)-(5,2): "
                + IntersectionUtils.segmentSegment(s1, s3) + " (пусто)\n");
    }

    static void testLineCircle() {
        System.out.println("▶ ТЕСТ 4: Пересечение прямой и окружности");
        Circle c = new Circle(new Point(0, 0), 3);

        Line lSec = new Line(0, 1, 0);
        System.out.println("  Прямая y=0, окружность (0,0) r=3");
        System.out.println("  Ожидаем: (-3,0) и (3,0)");
        System.out.println("  Результат: " + IntersectionUtils.lineCircle(lSec, c));

        Line lTan = new Line(0, 1, -3);
        System.out.println("  Касательная y=3: " + IntersectionUtils.lineCircle(lTan, c));

        Line lNone = new Line(0, 1, -5);
        System.out.println("  Прямая y=5: " + IntersectionUtils.lineCircle(lNone, c) + " (пусто)\n");
    }

    static void testSegmentCircle() {
        System.out.println("▶ ТЕСТ 5: Пересечение отрезка и окружности");
        Segment s = new Segment(new Point(-5, 0), new Point(5, 0));
        Circle c = new Circle(new Point(0, 0), 3);
        System.out.println("  Отрезок (-5,0)-(5,0), окружность (0,0) r=3");
        System.out.println("  Ожидаем: (-3,0) и (3,0)");
        System.out.println("  Результат: " + IntersectionUtils.segmentCircle(s, c));

        Segment sShort = new Segment(new Point(-1, 0), new Point(1, 0));
        System.out.println("  Короткий отрезок (-1,0)-(1,0): "
                + IntersectionUtils.segmentCircle(sShort, c) + " (пусто)\n");
    }

    static void testCircleCircle() {
        System.out.println("▶ ТЕСТ 6: Пересечение двух окружностей");
        Circle c1 = new Circle(new Point(0, 0), 5);
        Circle c2 = new Circle(new Point(4, 0), 5);
        System.out.println("  c1: (0,0) r=5, c2: (4,0) r=5");
        System.out.println("  Результат: " + IntersectionUtils.circleCircle(c1, c2));

        Circle c3 = new Circle(new Point(0, 0), 3);
        Circle c4 = new Circle(new Point(3, 0), 3);
        System.out.println("  Касание c3(0,0)r=3 и c4(6,0)r=3: "
                + IntersectionUtils.circleCircle(c3, new Circle(new Point(6, 0), 3)));

        Circle c5 = new Circle(new Point(100, 0), 1);
        System.out.println("  Не пересекаются: "
                + IntersectionUtils.circleCircle(c1, c5) + " (пусто)\n");
    }

    static void testNestedTriangles() {
        System.out.println("▶ ТЕСТ 7: Вложенные треугольники");

        Point[] set1 = {
                new Point(0, 0), new Point(10, 0), new Point(5, 10),
                new Point(3, 2), new Point(7, 2),  new Point(5, 6)
        };
        List<String> found1 = NestedTriangles.findAllNested(set1);
        System.out.println("  Набор 1 (ожидаем вложение): "
                + (found1.isEmpty() ? "НЕТ" : "ДА — найдено " + found1.size() + " пар(а)"));
        for (String s : found1) System.out.println("    • " + s);

        Point[] set2 = {
                new Point(0, 0), new Point(4, 0), new Point(2, 3),
                new Point(8, 8), new Point(12, 8), new Point(10, 12)
        };
        List<String> found2 = NestedTriangles.findAllNested(set2);
        System.out.println("\n  Набор 2 (не вложены): "
                + (found2.isEmpty() ? "НЕТ ✓" : "ДА"));

        Point[] set3 = { new Point(0, 0), new Point(1, 1) };
        System.out.println("  Набор 3 (< 3 точек): "
                + (NestedTriangles.findAllNested(set3).isEmpty() ? "НЕТ ✓" : "ДА"));
    }
}