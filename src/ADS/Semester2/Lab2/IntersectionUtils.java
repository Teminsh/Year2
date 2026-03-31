package ADS.Semester2.Lab2;

import java.util.ArrayList;
import java.util.List;

public class IntersectionUtils {
    private static final double EPS = 1e-9;

    public static List<Point> lineLine(Line l1, Line l2) {
        List<Point> result = new ArrayList<>();
        double d = l1.a * l2.b - l2.a * l1.b;
        if (Math.abs(d) < EPS) return result;
        double x = (-l1.c * l2.b + l2.c * l1.b) / d;
        double y = (-l1.a * l2.c + l2.a * l1.c) / d;
        result.add(new Point(x, y));
        return result;
    }

    public static List<Point> lineSegment(Line l, Segment s) {
        List<Point> result = new ArrayList<>();
        for (Point p : lineLine(l, s.toLine()))
            if (s.contains(p)) result.add(p);
        return result;
    }

    public static List<Point> segmentSegment(Segment s1, Segment s2) {
        List<Point> result = new ArrayList<>();
        for (Point p : lineLine(s1.toLine(), s2.toLine()))
            if (s1.contains(p) && s2.contains(p)) result.add(p);
        return result;
    }

    public static List<Point> lineCircle(Line l, Circle c) {
        List<Point> result = new ArrayList<>();
        double a = l.a, b = l.b, cc = l.c;
        double cx = c.center.x, cy = c.center.y, r = c.radius;
        double denom = a * a + b * b;

        double distSq = Math.pow(a * cx + b * cy + cc, 2) / denom;
        double rSq = r * r;
        if (distSq > rSq + EPS) return result;

        double x0 = cx - a * (a * cx + b * cy + cc) / denom;
        double y0 = cy - b * (a * cx + b * cy + cc) / denom;
        if (Math.abs(distSq - rSq) < EPS) {
            result.add(new Point(x0, y0));
        } else {
            double dt = Math.sqrt((rSq - distSq) / denom);
            result.add(new Point(x0 + b * dt, y0 - a * dt));
            result.add(new Point(x0 - b * dt, y0 + a * dt));
        }
        return result;
    }

    public static List<Point> segmentCircle(Segment s, Circle c) {
        List<Point> result = new ArrayList<>();
        for (Point p : lineCircle(s.toLine(), c))
            if (s.contains(p)) result.add(p);
        return result;
    }

    public static List<Point> circleCircle(Circle c1, Circle c2) {
        List<Point> result = new ArrayList<>();
        double dx = c2.center.x - c1.center.x;
        double dy = c2.center.y - c1.center.y;
        double d = Math.sqrt(dx * dx + dy * dy);
        if (d < EPS
                || d > c1.radius + c2.radius + EPS
                || d < Math.abs(c1.radius - c2.radius) - EPS)
            return result;
        double a = (c1.radius * c1.radius - c2.radius * c2.radius + d * d) / (2 * d);
        double h = Math.sqrt(Math.max(0, c1.radius * c1.radius - a * a));
        double mx = c1.center.x + a * dx / d;
        double my = c1.center.y + a * dy / d;
        if (Math.abs(h) < EPS) {
            result.add(new Point(mx, my));
        } else {
            result.add(new Point(mx + h * dy / d, my - h * dx / d));
            result.add(new Point(mx - h * dy / d, my + h * dx / d));
        }
        return result;
    }
}