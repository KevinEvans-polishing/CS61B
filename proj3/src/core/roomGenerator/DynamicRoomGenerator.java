package core.roomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DynamicRoomGenerator {
    // 生成房间
    public static List<Room> generateRooms(double width, double height, double minDist, int maxAttempts,
                                           int minRoomSize, int maxRoomSize, long seed) {
        List<Point> points = PoissonDiskSampling.generateSamples(width, height, minDist, maxAttempts, minRoomSize, seed);
        List<Room> rooms = new ArrayList<>();

        for (Point p : points) {
            int roomWidth = minRoomSize;
            int roomHeight = minRoomSize;

            // 试图向四个方向扩展房间
            while (roomWidth < maxRoomSize && isSpaceAvailable(p, rooms, roomWidth + 1, roomHeight, width, height)) {
                roomWidth++;
            }
            while (roomHeight < maxRoomSize && isSpaceAvailable(p, rooms, roomWidth, roomHeight + 1, width, height)) {
                roomHeight++;
            }

            rooms.add(new Room(p.x, p.y, roomWidth, roomHeight));
        }

        return rooms;
    }

    // 检查房间扩展是否可行
    private static boolean isSpaceAvailable(Point p, List<Room> rooms, int newWidth, int newHeight, double width, double height) {
        if (p.x + newWidth > width || p.y + newHeight > height) return false;

        for (Room r : rooms) {
            if (p.x < r.x + r.width && p.x + newWidth > r.x &&
                    p.y < r.y + r.height && p.y + newHeight > r.y) {
                return false; // 碰撞了
            }
        }
        return true;
    }

    // 主函数
    public static void main(String[] args) {
        List<Room> rooms = generateRooms(60, 30, 5, 2, 4, 15, 123);
        for (Room r : rooms) {
            System.out.println(r);
        }
        System.out.println("Total rooms generated: " + rooms.size());
    }
}