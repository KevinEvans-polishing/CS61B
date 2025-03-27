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
