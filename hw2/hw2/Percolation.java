package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private boolean [][] gridOpen;
    private int numberOpen;
    private WeightedQuickUnionUF c;
    private WeightedQuickUnionUF backwash;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.gridOpen = new boolean[N][N];
        this.N = N;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.gridOpen[i][j] = false;
            }
        }
        this.numberOpen = 0;
        this.c = new WeightedQuickUnionUF(N * N + 2);
        this.backwash = new WeightedQuickUnionUF(N * N + 1);

    }

    public void open(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        if (gridOpen[row][col]) {
            return;
        } else {
            gridOpen[row][col] = true;
            numberOpen += 1;
            if (row == 0) {
                c.union(xyTo1D(row, col), N * N);
                backwash.union(xyTo1D(row, col), N * N);
            }
            if (row == N - 1) {
                c.union(xyTo1D(row, col), N * N + 1);
            }
        }

        if (col + 1 < N && isOpen(row, col + 1)) {
            c.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            backwash.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            c.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            backwash.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
        if (row + 1 < N && isOpen(row + 1, col)) {
            c.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            backwash.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            c.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            backwash.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        return this.gridOpen[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        } else {
            return backwash.connected(N * N, xyTo1D(row, col));
        }
    }

    private int xyTo1D(int row, int col) {
        if (row < 0 || row > N - 1 || col < 0 || col > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        return (row * N) + col;
    }

    public int numberOfOpenSites() {
        return numberOpen;
    }

    public boolean percolates() {
        return c.connected(N * N, N * N + 1);
    }

    public static void main(String[] args) {

    }

}
