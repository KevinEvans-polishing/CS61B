package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static core.Main.SEED;

public class PoissonDiskSampling {
    private static final Random random = new Random(SEED);

    // 生成 Poisson Disk 采样点
    public static List<Point> generateSamples(double width, double height,
                                              double minDist, int maxAttempts,
                                              int minRoomSize) {
        double cellSize = minDist / Math.sqrt(2); // 计算网格单元大小
        int gridWidth = (int) Math.ceil(width / cellSize);
        int gridHeight = (int) Math.ceil(height / cellSize);

        Point[][] grid = new Point[gridWidth][gridHeight];
        List<Point> activeList = new ArrayList<>();
        List<Point> samples = new ArrayList<>();

        // 1. 选择一个随机的初始点
        Point initialPoint = new Point((int)(random.nextDouble() * width), (int)(random.nextDouble() * height));
        activeList.add(initialPoint);
        samples.add(initialPoint);
        grid[(int) (initialPoint.x / cellSize)][(int) (initialPoint.y / cellSize)] = initialPoint;

        // 2. 处理候选点
        while (!activeList.isEmpty()) {
            int index = random.nextInt(activeList.size());
            Point point = activeList.get(index);
            boolean found = false;

            // 生成 maxAttempts 个候选点
            for (int i = 0; i < maxAttempts; i++) {
                double angle = random.nextDouble() * 2 * Math.PI;
                double radius = minDist * (1 + random.nextDouble()); // 生成 minDist ~ 2 * minDist 之间的距离
                double newX = point.x + radius * Math.cos(angle);
                double newY = point.y + radius * Math.sin(angle);
                Point newPoint = new Point((int)newX, (int)newY);

                if (isValid(newPoint, width, height, grid, gridWidth, gridHeight, cellSize, minDist, minRoomSize)) {
                    activeList.add(newPoint);
                    samples.add(newPoint);
                    grid[(int) (newX / cellSize)][(int) (newY / cellSize)] = newPoint;
                    found = true;
                }
            }

            // 如果当前点无法再生成新点，则从候选列表中移除
            if (!found) {
                activeList.remove(index);
            }
        }

        return samples;
    }

    // 检查新点是否有效
    public static boolean isValid(Point point, double width, double height, Point[][] grid,
                                  int gridWidth, int gridHeight, double cellSize, double minDist, int minRoomSize) {
        if (point.x < 0 || point.x >= width - minRoomSize || point.y < 0 || point.y >= height - minRoomSize) {
            return false; // 超出边界
        }

        int gridX = (int) (point.x / cellSize);
        int gridY = (int) (point.y / cellSize);

        // 搜索 3×3 邻域，确保最小距离
        for (int i = Math.max(0, gridX - 1); i <= Math.min(gridX + 1, gridWidth - 1); i++) {
            for (int j = Math.max(0, gridY - 1); j <= Math.min(gridY + 1, gridHeight - 1); j++) {
                if (grid[i][j] != null && point.distTo(grid[i][j]) < minDist) {
                    return false; // 发现太近的点，拒绝该点
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Point> points = generateSamples(60, 30, 10, 30, 10);
        for (Point p : points) {
            System.out.println(p);
        }
        System.out.println("Total points generated: " + points.size());
    }
}