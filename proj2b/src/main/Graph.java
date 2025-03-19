package main;

import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * 利用邻接表构建String有向图
 */
public class Graph {
    // 底层邻接表
    List<Integer>[] lists;
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
        // 读取类型1 -- 将映射存储到map中
        In in = new In(file1);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] spiltLine = nextLine.split(",");
            int id = Integer.parseInt(spiltLine[0]);
            String synset = spiltLine[1];
            map.put(id, synset);
        }

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


    /**
     * add an edge front-back
     */
    public void addEdge(int front, int back) {
        // 找到front在邻接表的位置
        // 然后再相应list中添加back
        lists[front].add(back);
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

    public ArrayList<Integer> findListsWhereWordIn(int id) {
       return null;
    }
}
