package core;

public class Point {
    final int x ,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distTo(Point point) {
        return Math.sqrt((x - point.x) * (x - point.x) + (y - point.y) * (y - point.y));
    }
}
