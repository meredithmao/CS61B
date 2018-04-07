package lab11.graphs;

import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private LinkedList<Integer> queue;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        queue = new LinkedList<>();

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        //Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        announce();
        if (s == t) {
            return;
        }

        queue.addFirst(s);
        while(!queue.isEmpty()){
            Integer num = queue.removeLast();
            marked[num] = true;
            announce();
            if(num == t) return;
            for (int i : maze.adj(num)) {
                if (!marked[i]) {
                    edgeTo[i] = num;
                    announce();
                    queue.addFirst(i);
                }
                distTo[i] = distTo[num] + 1;
            }
        }
    }

    @Override
    public void solve() {
        bfs();
    }
}

