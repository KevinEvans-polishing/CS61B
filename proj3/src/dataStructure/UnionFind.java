package dataStructure;

public class UnionFind {
    private int size;
    private int[] items;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        size = N;
        items = new int[size];
        for (int i = 0; i < size; i++) {
            items[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int i = find(v);
        return -items[i];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (v < 0 || v > size) {
            throw new IllegalArgumentException("argument needs to be non-negative and no large" +
                    "than the max number in the array");
        }

        if (items[v] < 0) {
            return -items[v];
        } else {
            v = items[v];
            return v;
        }
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        while (items[v1] >= 0) {
            v1 = items[v1];
        }
        while (items[v2] >= 0) {
            v2 = items[v2];
        }

        return v1 == v2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v > size) {
            throw new IllegalArgumentException("argument needs to be non-negative and no large"
                    + "than the max number in the array");
        }

        // find the root
        int root = v;
        while (items[root] >= 0) {
            root = items[root];
        }

        // path-compression
        while (v != root && items[v] != root) {
            int parent = parent(v);
            items[v] = root;
            v = parent;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (connected(v1, v2)) {
            return;
        }
        int i = find(v1);
        int j = find(v2);

        int num1 = -items[i];
        int num2 = -items[j];

        if (num1 > num2) {
            items[j] = i;
            items[i] -= num2;
        } else {
            items[i] = j;
            items[j] -= num1;
        }
    }
}
