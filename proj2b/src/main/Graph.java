package main;

import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/**
 * 利用邻接表构建String有向图
 */
public class Graph {
    // 内部映射节点
    private class Node {
        private int id;
        private String string;
        private boolean isMarked;

        public Node(int id, String string) {
            this.id = id;
            this.string = string;
            isMarked = false;
        }

        public void mark() {
            isMarked = true;
        }

        public void unMark() {
            isMarked = false;
        }

        public boolean isMarked() {
            return isMarked;
        }
    }

    // 底层邻接表
    List<Node>[] lists;
    // 顶点个数
    private int verticesNumber;
    // edge个数
    private int edgeNumber;
    // 保存节点映射
    /**
     * construct a Graph with v vertices
     */
    public Graph() {
        verticesNumber = 0;
        lists = null;
        edgeNumber = 0;
    }

    public void createNode(int id, String string) {
        verticesNumber++;
        List<Node>[] newLists = new List[verticesNumber];
        for (int i = 0; i < verticesNumber; i++) {
            newLists[i] = new ArrayList<>();
        }
        if (lists != null) {
            for (int i = 0; i < lists.length; i++) {
                newLists[i] = lists[i];
            }
        }
        lists = newLists;
        var newNode = new Node(id, string);
        lists[id].add(newNode);
    }

    /**
     * add an edge front-back
     */
    public void addEdge(int front, int back) {
        // 找到front在邻接表的位置
        // 然后再相应list中添加back
        lists[front].add(lists[back].getFirst());
        edgeNumber++;
    }

    public ArrayList<Integer> findListsWhereWordIn(String word) {
        // 找到目标word对应索引
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < verticesNumber; i++) {
            Node node = lists[i].getFirst();
            if (node.string.contains(word)) {
                integers.add(i);
            }
        }
        return integers;
    }

    public Set<Integer> getEverythingFromTheIndexList(int index) {
        HashSet<Integer> integers = new HashSet<>();
        integers.add(index);
        for (int i = 1; i < lists[index].size(); i++) {
            int n = lists[index].get(i).id;
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
            String s = lists[i].getFirst().string;
            strings.add(s);
        }
        return strings;
    }

    public TreeSet<Integer> bfs(int index) {
        unMarkEveryNode();
        TreeSet<Integer> integers = new TreeSet<>();
        ArrayDeque<Node> fringe = new ArrayDeque<>();
        fringe.add(lists[index].getFirst());
        lists[index].getFirst().mark();
        integers.add(index);

        while (!fringe.isEmpty()) {
            Node thisNode = fringe.poll();
            for (Node node : adj(thisNode.id)) {
                if (!node.isMarked()) {
                    node.mark();
                    integers.add(node.id);
                    fringe.add(node);
                }
            }
        }
        return integers;
    }

    public Iterable<Node> adj(int index) {
        ArrayList<Node> copy = new ArrayList<>();
        for (Node node : lists[index]) {
            copy.add(node);
        }
        copy.removeFirst();
        return copy;
    }

    public void unMarkEveryNode() {
        for (int i = 0; i < verticesNumber; i++) {
            lists[i].getFirst().unMark();
        }
    }

    @Test
    public void getTest() {
        Graph graph = new Graph();
        graph.createNode(0, "hello");
        graph.createNode(1, "hi");
        graph.createNode(2, "bitch");
        graph.createNode(3, "fuck");
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);

        assertThat(graph.bfs(0)).containsExactly(0, 1, 2, 3).inOrder();
    }
}
