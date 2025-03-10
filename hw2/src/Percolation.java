import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // fullUF is only for the isFull, to prevent the backwash
    private WeightedQuickUnionUF fullUF;
    // for the percolates()
    private WeightedQuickUnionUF percolationUF;
    // the side length of the square
    private int n;
    // to track isOpen
    private boolean[] isOpen;
    // to track the number of the open sizes
    private int openSites;

    public int xyTo1D(int x, int y) {
        return x * n + y;
    }

    public Percolation(int N) {
        fullUF = new WeightedQuickUnionUF(N * N + 1);
        percolationUF = new WeightedQuickUnionUF(N * N + 2);
        n = N;
        isOpen = new boolean[N * N];
        openSites = 0;

        for (int i = 0; i < isOpen.length; i++) {
            isOpen[i] = false;
        }

        for (int i = 0; i < n; i++) {
            fullUF.union(i, n * n);
            percolationUF.union(i, n * n);
            percolationUF.union(i + (n - 1) * n, N * N + 1);
        }
    }

    public void open(int row, int col) {
        if (row < 0 || row > n - 1
        || col < 0 || col > n - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (!isOpen[xyTo1D(row, col)]) {
            isOpen[xyTo1D(row, col)] = true;
            openSites++;
            connectOpenSite(row, col);
        }
    }

    public void connectOpenSite(int x, int y) {
        int[][] off_set = {
                {-1, 0}, {0, 1},
                {1, 0}, {0, -1}
        };

        for (int[] dir : off_set) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX > 0 && newX < n && newY > 0 && newY < n
            && isOpen[xyTo1D(newX, newY)])  {
                fullUF.union(xyTo1D(newX, newY), xyTo1D(x, y));
                percolationUF.union(xyTo1D(newX, newY), xyTo1D(x, y));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row > n - 1
                || col < 0 || col > n - 1) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row > n - 1
                || col < 0 || col > n - 1) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen(row, col)
                && fullUF.connected(n * n, xyTo1D(row, col))
                && percolationUF.connected(n * n, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (openSites == 0) return false;
        return percolationUF.connected(n * n, n * n + 1);
    }
}
