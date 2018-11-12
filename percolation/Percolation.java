/* *****************************************************************************
 *  Name:   Oxana Melnikova
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] openedSet; // set of opened cells
    private final int size; // grid size
    private final WeightedQuickUnionUF uf; // union class
    private final int first; // cells bound


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) throw new java.lang.IllegalArgumentException("n cannot be 0 or negative");
        size = n;
        uf = new WeightedQuickUnionUF(n * n + 1 + n);
        first = 0;
        openedSet = new boolean[n * n];
        for (int j = 1; j < n + 1; j++) {
            uf.union(first, j);
            uf.union(n * (n - 1) + j, n * n + j);
        }
    }


    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row > size) || (col > size) || (row < 1) || (col < 1)) {
            throw new java.lang.IllegalArgumentException("row, " + "col must be from 0 to n");
        }
        int cell = cellIndex(row, col);
        openedSet[cell - 1] = true;
        int neighbour1 = 0, neighbour2, neighbour3 = 0, neighbour4 = 0;
        boolean union3 = false, union4 = false, union1 = false, union2 = false;

        if (row != 1) {
            neighbour1 = cellIndex(row - 1, col);
            if (isOpen(row - 1, col)) union1 = true;
        }

        neighbour2 = cellIndex(row + 1, col);
        if (row == size) union2 = true;
        else {
            if (isOpen(row + 1, col)) union2 = true;
        }

        if (col != 1) {
            neighbour3 = cellIndex(row, col - 1);
            if (isOpen(row, col - 1)) union3 = true;
        }


        if (col != size) {
            neighbour4 = cellIndex(row, col + 1);
            if (isOpen(row, col + 1)) union4 = true;
        }

        if (union1) uf.union(cell, neighbour1);
        if (union2) uf.union(cell, neighbour2);
        if (union3) uf.union(cell, neighbour3);
        if (union4) uf.union(cell, neighbour4);

    }

    // return cell index in array by the rowm and col numbers
    private int cellIndex(int row, int col) {
        return (row - 1) * size + col;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row > size) || (col > size) || (row < 1) || (col < 1)) {
            throw new java.lang.IllegalArgumentException("row, col must be from 0 to n");
        }
        int cell = cellIndex(row, col);

        return openedSet[cell - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row > size) || (col > size) || (row < 1) || (col < 1))
            throw new java.lang.IllegalArgumentException("row, col must be 0 to n");

        int cell = cellIndex(row, col);
        boolean res = (uf.connected(first, cell)) && isOpen(row, col);
        return res;
    }

    // number of open sites
    public int numberOfOpenSites() {
        int sum = 0;
        for (int i = 0; i < size * size; i++) {
            if (openedSet[i]) sum++;
        }
        return sum;
    }

    // does the system percolate?
    public boolean percolates() {
        boolean res = false;
        for (int i = 1; i < size + 1; i++) {
            if (uf.connected(first, cellIndex(size + 1, i))) {
                res = true;
            }
        }

        return res;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(1);

        System.out.println(p.isFull(1, 1));
        System.out.println(p.cellIndex(1, 1));
        System.out.println(p.isOpen(1, 1));
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.percolates());
        p.open(1, 1);
        System.out.println(p.isOpen(1, 1));
    }
}
