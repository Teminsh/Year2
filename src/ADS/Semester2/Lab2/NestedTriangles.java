package ADS.Semester2.Lab2;

import java.util.ArrayList;
import java.util.List;

public class NestedTriangles {

    private static List<Triangle> buildTriangles(Point[] points) {
        List<Triangle> list = new ArrayList<>();
        int n = points.length;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                for (int k = j + 1; k < n; k++) {
                    Triangle t = new Triangle(points[i], points[j], points[k]);
                    if (t.area() > 1e-9) list.add(t);
                }
        return list;
    }

    public static boolean hasNested(Point[] points) {
        List<Triangle> triangles = buildTriangles(points);
        for (int i = 0; i < triangles.size(); i++)
            for (int j = 0; j < triangles.size(); j++) {
                if (i == j) continue;
                if (triangles.get(i).containsTriangle(triangles.get(j)))
                    return true;
            }
        return false;
    }

    public static List<String> findAllNested(Point[] points) {
        List<Triangle> triangles = buildTriangles(points);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < triangles.size(); i++)
            for (int j = 0; j < triangles.size(); j++) {
                if (i == j) continue;
                if (triangles.get(i).containsTriangle(triangles.get(j)))
                    results.add(triangles.get(j) + "\n     вложен в " + triangles.get(i));
            }
        return results;
    }
}