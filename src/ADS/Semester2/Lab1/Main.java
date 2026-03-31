package ADS.Semester2.Lab1;

/*

Нахождение выпуклой оболочки множества точек
В данной задаче требуется ввести N точек своими координатами (x,y).
Затем требуется определить, существует ли выпуклая оболочка заданного множества точек.
При этом можно использовать:
1.	или алгоритм Грэхема, или алгоритм Джарвиса, или метод «разделяй и властвуй»

*/

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static void main() throws Exception {
        boolean AUTO_TESTS = true;

        if (AUTO_TESTS) {
            runAutoTests();
        } else {
            runSingle(System.in);
        }
    }

    private static void runSingle(InputStream input) throws Exception {
        FastScanner fs = new FastScanner(input);
        StringBuilder out = new StringBuilder();

        int n = fs.nextInt();
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            long x = fs.nextLong();
            long y = fs.nextLong();
            points.add(new Point(x, y));
        }

        points = ConvexHull.uniquePoints(points);
        List<Point> hull = ConvexHull.grahamScan(points);

        if (hull.isEmpty()) {
            out.append("Выпуклая оболочка не существует\n");
        } else {
            out.append("Выпуклая оболочка существует\n");
            out.append(hull.size()).append('\n');
            for (Point p : hull) {
                out.append(p.x).append(' ').append(p.y).append('\n');
            }
        }

        System.out.print(out);
    }

    private static void runAutoTests() throws Exception {
        String[] tests = {
                "5\n0 0\n0 2\n2 2\n2 0\n1 1\n",
                "4\n0 0\n1 1\n2 2\n3 3\n",
                "7\n0 0\n0 0\n2 0\n2 2\n0 2\n1 1\n2 1\n",
                "1\n5 5\n",
                "6\n-1 -1\n-2 0\n0 2\n2 1\n1 -2\n-3 -1\n"
        };

        for (int i = 0; i < tests.length; i++) {
            System.out.println("TEST " + (i + 1));
            System.out.println("INPUT:");
            System.out.print(tests[i]);

            System.out.println("OUTPUT:");
            runSingle(new ByteArrayInputStream(tests[i].getBytes(StandardCharsets.UTF_8)));
            System.out.println("-----");
        }
    }
}