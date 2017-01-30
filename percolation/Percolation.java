import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // create n-by-n grid, with all sites blocked
    private int totalOpendSite;
    private int size;
    private int topIndex;
    private int bottomIndex;

    private WeightedQuickUnionUF table;
    private boolean[] tableSites;  // blocked or opened
    private boolean[] tableFull;  // 0: unchecked, 1: is full, 2: not full

    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException();
        }
        size = n;
        totalOpendSite = 0;
        int length = n * n + 2;
        topIndex = length - 2;
        bottomIndex = length - 1;

        tableSites = new boolean[length];
        tableFull = new boolean[length];

        for (int i = 0; i < length; i++) {
            tableSites[i] = false;
            tableFull[i] = false;
        }
        table = new WeightedQuickUnionUF(length);
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.size || col > this.size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        totalOpendSite += 1;
        int index = calculateIndex(row, col);
        tableSites[index] = true;

        // union with top lines
        if (row == 1) {
            table.union(index, topIndex);
            if (size == 1) {
                table.union(index, bottomIndex);
            }
            if (row != size && isOpen(row + 1, col)) {
                table.union(index, calculateIndex(row + 1, col));
            }
            return;
        }

        // union with bottom lines
        if (row == size) {
            table.union(index, bottomIndex);
            if (isOpen(row - 1, col)) {
                table.union(index, calculateIndex(row - 1, col));
            }
            return;
        }


        // union top
        if (isOpen(row - 1, col)) {
            table.union(index, calculateIndex(row - 1, col));
        }
        // right if it has right
        if (col < size && isOpen(row, col + 1)) {
            table.union(index, calculateIndex(row, col + 1));
        }
        // left if it has left
        if (col > 1 && isOpen(row, col - 1)) {
            table.union(index, calculateIndex(row, col - 1));
        }
        // union bottom if it's not bottom
        if (isOpen(row + 1, col)) {
            table.union(index, calculateIndex(row + 1, col));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.size || col > this.size) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int index = calculateIndex(row, col);
        return tableSites[index];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > this.size || col > this.size) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int index = calculateIndex(row, col);
        return table.connected(index, topIndex);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return totalOpendSite;
    }

    // does the system percolate?
    public boolean percolates() {
        return table.connected(topIndex, bottomIndex);
    }

    private int calculateIndex(int row, int col) {  // row and col is 1 based
        return size * (row-1) + (col-1);
    }

}
