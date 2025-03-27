package core;

import dataStructure.UnionFind;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HallwayGenerator {

     public static class Edge {
        int index1;
        int index2;
        double weight;

        public Edge(int index1, int index2, double weight) {
            this.index1 = index1;
            this.weight = weight;
            this.index2 = index2;
        }
    }
    // 返回最小生成树
    public static List<Edge> createHallway(List<Room> rooms) {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                double distance = distance(rooms.get(i), rooms.get(j));
                edges.add(new Edge(i, j, distance));
            }
        }

        edges.sort(Comparator.comparingDouble(e -> e.weight));

        // 用并查集构建最小生成树
        UnionFind uf = new UnionFind(rooms.size());
        List<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            if (!uf.connected(edge.index1, edge.index2)) {
                uf.union(edge.index1, edge.index2);
                mst.add(edge);
                if (mst.size() == rooms.size() - 1) {
                    break;
                }
            }

        }
        return mst;
    }

    private static double distance(Room room1, Room room2) {
        int x1 = room1.centerX();
        int y1 = room1.centerY();
        int x2 = room2.centerX();
        int y2 = room2.centerY();

        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }
}
