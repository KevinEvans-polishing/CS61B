package main;

import edu.princeton.cs.algs4.In;

public class FileReader {

    public static Graph readFile(String file1, String file2) {
        Graph graph = new Graph();
        // 读取类型1 -- 将映射存储到node中
        In in = new In(file1);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] spiltLine = nextLine.split(",");
            int id = Integer.parseInt(spiltLine[0]);
            String synset = spiltLine[1];
            graph.createNode(id, synset);
        }
        // 读取类型2 -- 将对应的id关系存储到Graph中
        in = new In(file2);
        while (in.hasNextLine()) {
            String nextLine = in.readLine();
            String[] spiltLine = nextLine.split(",");
            int front = Integer.parseInt(spiltLine[0]);
            for (int i = 1; i < spiltLine.length; i++) {
                int back = Integer.parseInt(spiltLine[i]);
                graph.addEdge(front, back);
            }
        }
        return graph;
    }
}
