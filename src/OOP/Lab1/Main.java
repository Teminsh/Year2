package OOP.Lab1;

import java.util.List;

public class Main
{
    static void main(String[] args) {
        System.out.println("=== ADDITION ===\n");

        AngleRange range1 = new AngleRange(0f, 10f, true, true);
        AngleRange range2 = new AngleRange(20f, 30f, true, true);
        printAdd(range1, range2);

        AngleRange range3 = new AngleRange(0f, 10f, true, false);
        AngleRange range4 = new AngleRange(10f, 20f, false, true);
        printAdd(range3, range4);

        AngleRange range5 = new AngleRange(0f, 10f, true, true);
        AngleRange range6 = new AngleRange(10f, 20f, true, true);
        printAdd(range5, range6);

        AngleRange range7 = new AngleRange(10f, 30f, true, false);
        AngleRange range8 = new AngleRange(20f, 40f, true, true);
        printAdd(range7, range8);

        AngleRange range9 = new AngleRange(10f, 50f, true, true);
        AngleRange range10 = new AngleRange(20f, 30f, true, true);
        printAdd(range9, range10);

        AngleRange range11 = new AngleRange(15f, 25f, true, true);
        AngleRange range12 = new AngleRange(15f, 25f, true, true);
        printAdd(range11, range12);

        AngleRange range13 = new AngleRange(15f, 25f, false, false);
        AngleRange range14 = new AngleRange(15f, 25f, false, false);
        printAdd(range13, range14);

        AngleRange range15 = new AngleRange(15f, 25f, true, false);
        AngleRange range16 = new AngleRange(15f, 25f, false, true);
        printAdd(range15, range16);

        AngleRange range17 = new AngleRange(0f, 10f, true, true);
        AngleRange range18 = new AngleRange(10f, 20f, false, true);
        printAdd(range17, range18);

        AngleRange range19 = new AngleRange(5f, 15f, false, true);
        AngleRange range20 = new AngleRange(10f, 20f, true, false);
        printAdd(range19, range20);

        System.out.println("\n=== SUBTRACTION ===\n");

        AngleRange range21 = new AngleRange(20f, 30f, true, true);
        AngleRange range22 = new AngleRange(0f, 10f, true, true);
        printSub(range21, range22);

        AngleRange range23 = new AngleRange(0f, 10f, true, true);
        AngleRange range24 = new AngleRange(20f, 30f, true, true);
        printSub(range23, range24);

        AngleRange range25 = new AngleRange(10f, 20f, true, true);
        AngleRange range26 = new AngleRange(5f, 25f, true, true);
        printSub(range25, range26);

        AngleRange range27 = new AngleRange(10f, 30f, true, true);
        AngleRange range28 = new AngleRange(5f, 20f, true, true);
        printSub(range27, range28);

        AngleRange range29 = new AngleRange(10f, 30f, true, true);
        AngleRange range30 = new AngleRange(20f, 40f, true, true);
        printSub(range29, range30);

        AngleRange range31 = new AngleRange(10f, 50f, true, true);
        AngleRange range32 = new AngleRange(20f, 30f, true, true);
        printSub(range31, range32);

        AngleRange range33 = new AngleRange(15f, 25f, true, true);
        AngleRange range34 = new AngleRange(15f, 25f, true, true);
        printSub(range33, range34);

        AngleRange range35 = new AngleRange(15f, 25f, true, true);
        AngleRange range36 = new AngleRange(15f, 25f, false, false);
        printSub(range35, range36);

        AngleRange range37 = new AngleRange(10f, 20f, true, true);
        AngleRange range38 = new AngleRange(20f, 30f, true, true);
        printSub(range37, range38);

        AngleRange range39 = new AngleRange(10f, 20f, true, false);
        AngleRange range40 = new AngleRange(20f, 30f, false, true);
        printSub(range39, range40);

        AngleRange range41 = new AngleRange(10f, 30f, false, true);
        AngleRange range42 = new AngleRange(5f, 20f, true, false);
        printSub(range41, range42);

        AngleRange range43 = new AngleRange(10f, 40f, true, true);
        AngleRange range44 = new AngleRange(20f, 30f, false, false);
        printSub(range43, range44);

        System.out.println("\n=== CONTAINS ===\n");

        AngleRange range45 = new AngleRange(10f, 30f, true, true);
        Angle angle1 = Angle.fromRadians(20f);
        printContains(range45, angle1);

        AngleRange range46 = new AngleRange(10f, 30f, true, true);
        Angle angle2 = Angle.fromRadians(10f);
        printContains(range46, angle2);

        AngleRange range47 = new AngleRange(10f, 30f, true, true);
        Angle angle3 = Angle.fromRadians(30f);
        printContains(range47, angle3);

        AngleRange range48 = new AngleRange(10f, 30f, false, true);
        Angle angle4 = Angle.fromRadians(10f);
        printContains(range48, angle4);

        AngleRange range49 = new AngleRange(10f, 30f, true, false);
        Angle angle5 = Angle.fromRadians(30f);
        printContains(range49, angle5);

        AngleRange range50 = new AngleRange(10f, 30f, true, true);
        Angle angle6 = Angle.fromRadians(5f);
        printContains(range50, angle6);

        AngleRange range51 = new AngleRange(10f, 30f, true, true);
        Angle angle7 = Angle.fromRadians(35f);
        printContains(range51, angle7);

        AngleRange range52 = new AngleRange(10f, 30f, false, false);
        Angle angle8 = Angle.fromRadians(20f);
        printContains(range52, angle8);

        AngleRange range53 = new AngleRange(10f, 30f, true, false);
        Angle angle9 = Angle.fromRadians(10f);
        printContains(range53, angle9);

        AngleRange range54 = new AngleRange(10f, 10.1f, true, true);
        Angle angle10 = Angle.fromRadians(10.05f);
        printContains(range54, angle10);

        AngleRange range55 = new AngleRange(0f, (float)Math.PI, true, true);
        Angle angle11 = Angle.fromDegrees(90);
        printContains(range55, angle11);

        AngleRange range56 = new AngleRange(0, 180);
        Angle angle12 = Angle.fromDegrees(90);
        printContains(range56, angle12);

        System.out.println("\n=== IN ===\n");

        AngleRange range57 = new AngleRange(15f, 25f, true, true);
        AngleRange range58 = new AngleRange(10f, 30f, true, true);
        printIn(range57, range58);

        AngleRange range59 = new AngleRange(10f, 30f, true, true);
        AngleRange range60 = new AngleRange(10f, 30f, true, true);
        printIn(range59, range60);

        AngleRange range61 = new AngleRange(15f, 35f, true, true);
        AngleRange range62 = new AngleRange(10f, 30f, true, true);
        printIn(range61, range62);

        AngleRange range63 = new AngleRange(5f, 25f, true, true);
        AngleRange range64 = new AngleRange(10f, 30f, true, true);
        printIn(range63, range64);

        AngleRange range65 = new AngleRange(0f, 5f, true, true);
        AngleRange range66 = new AngleRange(10f, 30f, true, true);
        printIn(range65, range66);

        AngleRange range67 = new AngleRange(35f, 40f, true, true);
        AngleRange range68 = new AngleRange(10f, 30f, true, true);
        printIn(range67, range68);

        AngleRange range69 = new AngleRange(10f, 20f, true, true);
        AngleRange range70 = new AngleRange(10f, 30f, true, true);
        printIn(range69, range70);

        AngleRange range71 = new AngleRange(20f, 30f, true, true);
        AngleRange range72 = new AngleRange(10f, 30f, true, true);
        printIn(range71, range72);

        AngleRange range73 = new AngleRange(15f, 25f, false, false);
        AngleRange range74 = new AngleRange(10f, 30f, true, true);
        printIn(range73, range74);

        AngleRange range75 = new AngleRange(10f, 30f, true, true);
        AngleRange range76 = new AngleRange(10f, 30f, false, false);
        printIn(range75, range76);

        AngleRange range77 = new AngleRange(10f, 20f, true, true);
        AngleRange range78 = new AngleRange(10f, 30f, false, true);
        printIn(range77, range78);

        AngleRange range79 = new AngleRange(20f, 30f, true, true);
        AngleRange range80 = new AngleRange(10f, 30f, true, false);
        printIn(range79, range80);

        AngleRange range81 = new AngleRange(15f, 25f, false, false);
        AngleRange range82 = new AngleRange(10f, 30f, false, false);
        printIn(range81, range82);

        AngleRange range83 = new AngleRange(10f, 25f, true, true);
        AngleRange range84 = new AngleRange(20f, 35f, true, true);
        printIn(range83, range84);
    }

    private static void printAdd(AngleRange r1, AngleRange r2) {
        List<AngleRange> result = r1.add(r2);
        System.out.println(r1 + " + " + r2 + " = " + formatList(result));
    }

    private static void printSub(AngleRange r1, AngleRange r2) {
        List<AngleRange> result = r1.subtract(r2);
        System.out.println(r1 + " - " + r2 + " = " + formatList(result));
    }

    private static void printContains(AngleRange r, Angle a) {
        System.out.println(r + " contains " + a.toFloat() + " = " + r.contains(a));
    }

    private static void printIn(AngleRange r1, AngleRange r2) {
        System.out.println(r1 + " in " + r2 + " = " + r1.in(r2));
    }

    private static String formatList(List<AngleRange> ranges) {
        if (ranges.isEmpty()) return "∅";
        if (ranges.size() == 1) return ranges.get(0).toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ranges.size(); i++) {
            sb.append(ranges.get(i));
            if (i < ranges.size() - 1) sb.append(" ∪ ");
        }
        return sb.toString();
    }
}