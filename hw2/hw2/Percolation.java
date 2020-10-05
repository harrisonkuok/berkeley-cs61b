package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean percolate;
    private int numOfOpenSites;
    private int length;
    private boolean[][] grid;
    private WeightedQuickUnionUF connections;
    private WeightedQuickUnionUF connectionsAfterP;

    public Percolation(int N) {
        percolate = false;
        numOfOpenSites = 0;
        length = N;
        grid = new boolean[N][N];
        connections = new WeightedQuickUnionUF(N * N + 2);
        connectionsAfterP = new WeightedQuickUnionUF(N * N + 1);

        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j+= 1) {
                grid[i][j] = false;
            }
        }
        for (int i = 0; i < N; i += 1) {
            connections.union(i, N * N);
            connections.union(N * N - 1 - i, N * N + 1);
            connectionsAfterP.union(i, N * N);
        }
    }

    private int xyTo1D(int row, int col) {
        return length * row + col;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int boxNum = xyTo1D(row, col);
        grid[row][col] = true;
        numOfOpenSites += 1;

        if (row > 0) {
            if (isOpen(row - 1, col)) {
                connections.union(boxNum, xyTo1D(row - 1, col));
                connectionsAfterP.union(boxNum, xyTo1D(row - 1, col));
            }
        }
        if (row < length - 1) {
            if (isOpen(row + 1, col)) {
                connections.union(boxNum, xyTo1D(row + 1, col));
                connectionsAfterP.union(boxNum, xyTo1D(row + 1, col));
            }
        }
        if (col > 0) {
            if (isOpen(row, col - 1)) {
                connections.union(boxNum, xyTo1D(row, col - 1));
                connectionsAfterP.union(boxNum, xyTo1D(row, col - 1));
            }
        }
        if  (col < length - 1) {
            if (isOpen(row, col + 1)) {
                connections.union(boxNum, xyTo1D(row, col + 1));
                connectionsAfterP.union(boxNum, xyTo1D(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row > 0 && row < length - 1) {
            if (!percolate) {
                return connections.connected(xyTo1D(row, col), length * length);
            }
            return connectionsAfterP.connected(xyTo1D(row, col), length * length);
        }
        if (!percolate) {
            return isOpen(row, col) && connections.connected(xyTo1D(row, col), length * length);
        }
        return isOpen(row, col) && connectionsAfterP.connected(xyTo1D(row, col), length * length);
    }

    public int numberOfOpenSites() {
        return numOfOpenSites;
    }

    public boolean percolates() {
        if (length == 1) {
            return isOpen(0, 0);
        }
        if (!percolate) {
            percolate = connections.connected(length * length, length * length + 1);
        }
        return percolate;
    }

    public static void main(String[] args) {

    }

}
