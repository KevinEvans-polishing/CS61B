package main;

import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/**
 * 利用邻接表构建String有向图
 */
public class Graph {
    // 底层邻接表
    List<Integer>[] lists = new List[4];
    // 顶点个数
    private int verticesNumber;
    // edge个数
    private int edgeNumber;
    // 保存节点映射
    Map<Integer, String> map = new HashMap<>();
    /**
     * construct a Graph with v vertices
     */
    public Graph(int verticesNumber) {
        this.verticesNumber = verticesNumber;
        lists = new List[verticesNumber];
        for (int i = 0; i < verticesNumber; i++) {
            lists[i] = new ArrayList<>();
        }
        edgeNumber = 0;
    }

    public Graph(String file1, String file2) {
        edgeNumber = 0;
        // 读取类型1 -- 将映射存储到map中
        In in = new In(file1);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] spiltLine = nextLine.split(",");
            int id = Integer.parseInt(spiltLine[0]);
            String synset = spiltLine[1];
            map.put(id, synset);
        }
        verticesNumber = map.size();
        // 读取类型2 -- 将对应的id关系存储到Graph中
        in = new In(file2);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] spiltLine = nextLine.split(",");
            int front = Integer.parseInt(spiltLine[0]);
            for (int i = 1; i < spiltLine.length; i++) {
                int back = Integer.parseInt(spiltLine[i]);
                addEdge(front, back);
            }
        }
    }


    public Graph() {
        verticesNumber = 4;
        for (int i = 0; i < 4; i++) {
            lists[i] = new ArrayList<>();
        }
        edgeNumber = 0;
    }

    /**
     * add an edge front-back
     */
    public void addEdge(int front, int back) {
        // 找到front在邻接表的位置
        // 然后再相应list中添加back
        lists[front].add(back);
        edgeNumber++;
    }

    /**
     * vertices adjacent to v
     */
    Iterable<Integer> adj(int v) {
        return null;
    }

    /**
     * numbers of vertices
     */
    int V() {
        return verticesNumber;
    }

    /**
     * numbers of edges
     */
    int E() {
        return edgeNumber;
    }

    public ArrayList<Integer> findListsWhereWordIn(String word) {
        // 找到目标word对应索引
        ArrayList<Integer> integers = new ArrayList<>();
        for (int id : map.keySet()) {
            String node = map.get(id);
            if (node.contains(word)) {
                integers.add(id);
            }
        }
        return integers;
    }

    public Set<Integer> getEverythingFromTheIndexList(int index) {
        HashSet<Integer> integers = new HashSet<>();
        integers.add(index);
        for (int i = 0; i < lists[index].size(); i++) {
            int n = lists[index].get(i);
            if (lists[n] != null && !lists[n].isEmpty()) {
                integers.addAll(getEverythingFromTheIndexList(n));
            } else {
                integers.add(n);
            }
        }
        return integers;
    }

    public Set<String> backToStringSet(Set<Integer> integers) {
        TreeSet<String> strings = new TreeSet<>();
        for (int i : integers) {
            String s = map.get(i);
            strings.add(s);
        }
        return strings;
    }

    @Test
    public void getTest() {
        Graph graph = new Graph();
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);

        graph.getEverythingFromTheIndexList(0);
    }
}
