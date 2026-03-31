package ADS.Semester2.Lab2;

public class Segment {
    public Point p1, p2;

    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line toLine() {
        return new Line(p1, p2);
    }

    public boolean contains(Point p) {
        double minX = Math.min(p1.x, p2.x) - 1e-9;
        double maxX = Math.max(p1.x, p2.x) + 1e-9;
        double minY = Math.min(p1.y, p2.y) - 1e-9;
        double maxY = Math.max(p1.y, p2.y) + 1e-9;
        return p.x >= minX && p.x <= maxX && p.y >= minY && p.y <= maxY;
    }
}