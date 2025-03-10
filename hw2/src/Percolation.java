import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private WeightedQuickUnionUF wq;
    private int[][] grid;
    private int size;
    private int openSiteNumber;

    /**
     * create N-by-N grid, with all sites initially blocked
     * @param N the size of the grid
     */
    public Percolation(int N) {
        size = N;
        grid = new int[size][size];
        wq = new WeightedQuickUnionUF(N + 2);
        openSiteNumber = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = 0;
            }
        }

        for (int i = 0; i < size; i++) {
            wq.union(i, N);
            wq.union(size * size - 1 - i, N + 1);
        }
    }

    /**
     * open the site (row, col) if it is not open already
     * @param row the according row
     * @param col the according column
     */
    public void open(int row, int col) {
        if ((row < 0 || row > size - 1)
        || (col < 0 || col > size - 1)) {
            throw new IllegalArgumentException("row and col needed to be non-negative and" +
                    "smaller the maximum of the index");
        }
        int presentSite = grid[row][col];
        if (presentSite == 0) {
            presentSite = 1;
            openSiteNumber++;
        }
        if (getNearBy(row, col) != -1) {
            int nearIndex = getNearBy(row, col);
            int thisIndex = xyTo1D(row, col);
            wq.union(thisIndex, nearIndex);
        }
    }

    /**
     * is the site (row, col) open?
     * @param row the according row
     * @param col the according column
     * @return the status of the site
     */
    public boolean isOpen(int row, int col) {
        if ((row < 0 || row > size - 1)
                || (col < 0 || col > size - 1)) {
            throw new IllegalArgumentException("row and col needed to be non-negative and" +
                    "smaller the maximum of the index");
        }
        return grid[row][col] == 1;
    }

    /**
     * is the site (row, col) full?
     * @param row the according row
     * @param col the according column
     * @return the full status of the size
     */
    public boolean isFull(int row, int col) {
        if ((row < 0 || row > size - 1)
                || (col < 0 || col > size - 1)) {
            throw new IllegalArgumentException("row and col needed to be non-negative and" +
                    "smaller the maximum of the index");
        }
        return wq.connected(size * size - 2, grid[row][col]);
    }

    /**
     * number of open sites
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return openSiteNumber;
    }

    /**
     * does the system percolate?
     * @return where the system percolate
     */
    public boolean percolates() {
        return wq.connected(size * size - 1, size * size - 2);
    }

    /**
     * find out whether it has nearby open site
     * @param row the according row
     * @param col the according col
     * @return the 1D index of the nearBy, if none return -1
     */
    public int getNearBy(int row, int col) {
        if ((row < 0 || row > size - 1)
                || (col < 0 || col > size - 1)) {
            throw new IllegalArgumentException("row and col needed to be non-negative and" +
                    "smaller the maximum of the index");
        }
        int presentSite = grid[row][col];
        if (presentSite == 0) {
            return -1;
        }
        // new idea: using two-dimension array to
        // represent the off-set volume
        int[][] off = {
                {-1, 0}, {0, 1},
                {1, 0}, {0, -1}
        };

        for (int[] o : off) {
            int newRow = row + o[0];
            int newCol = col + o[1];

            if (newRow >= 0 && newRow < size
                    && newCol >= 0 && newCol < size) {
                if (grid[newRow][newCol] == 1) {
                    return xyTo1D(newRow, newCol);
                }
            }
        }
        return -1;
    }

    public int xyTo1D(int x, int y) {
        if ((x < 0 || x > size - 1)
                || (y < 0 || y > size - 1)) {
            throw new IllegalArgumentException("row and col needed to be non-negative and" +
                    "smaller the maximum of the index");
        }
        return size * x + y;
    }
}
