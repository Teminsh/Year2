package ADS.Semester2.Lab1;

import java.util.ArrayList;
import java.util.List;

public class ConvexHull {

    public static long cross(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    public static long dist2(Point a, Point b) {
        long dx = a.x - b.x;
        long dy = a.y - b.y;
        return dx * dx + dy * dy;
    }

    public static List<Point> uniquePoints(List<Point> pts) {
        pts.sort((a, b) -> {
            if (a.x != b.x) return Long.compare(a.x, b.x);
            return Long.compare(a.y, b.y);
        });

        List<Point> res = new ArrayList<>();
        for (Point p : pts) {
            if (res.isEmpty() || res.getLast().x != p.x || res.getLast().y != p.y) {
                res.add(p);
            }
        }
        return res;
    }

    public static List<Point> grahamScan(List<Point> points) {
        if (points.size() < 3) return new ArrayList<>();

        Point pivot = points.getFirst();
        for (Point p : points) {
            if (p.y < pivot.y || (p.y == pivot.y && p.x < pivot.x)) {
                pivot = p;
            }
        }

        Point finalPivot = pivot;

        points.sort((p1, p2) ->
        {
            if (p1 == finalPivot) return -1;
            if (p2 == finalPivot) return 1;

            long cr = cross(finalPivot, p1, p2);
            if (cr == 0) {
                return Long.compare(dist2(finalPivot, p1), dist2(finalPivot, p2));
            }
            return cr > 0 ? -1 : 1;
        });

        List<Point> stack = new ArrayList<>();

        for (Point p : points) {
            while (stack.size() >= 2 &&
                    cross(stack.get(stack.size() - 2), stack.getLast(), p) <= 0) {
                stack.removeLast();
            }
            stack.add(p);
        }

        if (stack.size() < 3) return new ArrayList<>();
        return stack;
    }
}