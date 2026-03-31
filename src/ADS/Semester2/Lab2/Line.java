package ADS.Semester2.Lab2;

public class Line {
    public double a, b, c;

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Line(Point p1, Point p2) {
        this.a = p2.y - p1.y;
        this.b = p1.x - p2.x;
        this.c = p2.x * p1.y - p1.x * p2.y;
    }

    @Override
    public String toString() {
        return String.format("%.2fx + %.2fy + %.2f = 0", a, b, c);
    }
}