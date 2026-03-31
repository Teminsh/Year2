package ADS.Semester2.Lab2;

public class Triangle {
    public Point a, b, c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private double sign(Point p1, Point p2, Point p3) {
        return (p1.x - p3.x) * (p2.y - p3.y)
                - (p2.x - p3.x) * (p1.y - p3.y);
    }

    public boolean contains(Point p) {
        double d1 = sign(p, a, b);
        double d2 = sign(p, b, c);
        double d3 = sign(p, c, a);
        boolean hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);
        return !(hasNeg && hasPos);
    }

    public boolean containsTriangle(Triangle t) {
        return contains(t.a) && contains(t.b) && contains(t.c);
    }

    public double area() {
        return Math.abs((b.x - a.x) * (c.y - a.y)
                - (c.x - a.x) * (b.y - a.y)) / 2.0;
    }

    @Override
    public String toString() {
        return "Треугольник[" + a + ", " + b + ", " + c + "]";
    }
}