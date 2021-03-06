package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private int size;
    private int[][] board;

    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i > size - 1 || i < 0 || j < 0 || j > size - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return board[i][j];
    }

    public int size() {
        return this.size;
    }

    // @Source Josh Hug
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int hamValue = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.board[i][j] != (size * i + j + 1) && this.board[i][j] != 0) {
                    hamValue++;
                }
            }
        }
        return hamValue;
    }

    public int manhattan() {
        int manValue = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int currentNumber = this.board[i][j];
                if (currentNumber == 0) {
                    continue;
                } else {
                    int horizontal = (currentNumber - 1) / size;
                    int vertical = (currentNumber - 1) % size;
                    manValue += ((Math.abs(i - horizontal) + Math.abs(j - vertical)));
                }
            }
        }
        return manValue;
    }
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        } else if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board object = (Board) y;

        if (object.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] != object.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public int hashCode() {
        return 0;
    }
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
