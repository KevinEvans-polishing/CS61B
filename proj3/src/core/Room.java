package core;

public class Room {
    int x;
    int y;
    int width;
    int height;

    public Room(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int centerX() {
        return x + width / 2;
    }

    public int centerY() {
        return y + height / 2;
    }
    @Override
    public String toString() {
        return "(" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ')';
    }
}
