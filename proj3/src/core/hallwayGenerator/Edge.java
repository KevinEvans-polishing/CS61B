package core.hallwayGenerator;

public class Edge {
    public int index1;
    public int index2;
    double weight;

    public Edge(int index1, int index2, double weight) {
        this.index1 = index1;
        this.weight = weight;
        this.index2 = index2;
    }
}
