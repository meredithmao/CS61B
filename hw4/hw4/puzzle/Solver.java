package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private final WorldState initialWorld;
    private MinPQ<SearchNode> searchNodes;
    private List<WorldState> solution = new ArrayList<>();

    public Solver(WorldState initial) {
        this.initialWorld = initial;
        this.searchNodes = new MinPQ<>(new priorityNode());

        SearchNode rootNode = new SearchNode(initial, 0, null);
        searchNodes.insert(rootNode);

        SearchNode nodeToDelete;

        while (!searchNodes.min().currentState.isGoal()) {
            nodeToDelete = searchNodes.delMin();
            for (WorldState neighbor : initial.neighbors()) {
                if(!sameState(nodeToDelete, neighbor)) {
                    searchNodes.insert(new SearchNode(neighbor, nodeToDelete.getMovesMade() + 1, nodeToDelete));
                }
            }
        }

        nodeToDelete = searchNodes.delMin();
        this.solution.add(nodeToDelete.getCurrentState());
        while ((nodeToDelete = nodeToDelete.getPreviousNode()) != null) {
            this.solution.add(0, nodeToDelete.getCurrentState()) ;
        }
    }

    private boolean sameState(SearchNode prevNode, WorldState state) {
        SearchNode parentNode = prevNode;
        while((parentNode = parentNode.getPreviousNode()) != null) {
            if(parentNode.getCurrentState() == state) {
                return true;
            }
        }
        return false;
    }

    public int moves() {
        return this.solution.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return this.solution;
    }

    private class SearchNode {

        private WorldState currentState;
        private int movesMade;
        private SearchNode previousNode;

        //constructor for SearchNode Object
        public SearchNode(WorldState initial, int numberOfMoves, SearchNode prevNode) {
            this.currentState = initial;
            this.movesMade = numberOfMoves;
            this.previousNode = prevNode;
        }

        public WorldState getCurrentState() {
            return currentState;
        }

        public int getMovesMade() {
            return movesMade;
        }

        public SearchNode getPreviousNode() {
            return previousNode;
        }
    }

    private class priorityNode implements Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            int priority1 = node1.getMovesMade() + node1.currentState.estimatedDistanceToGoal();
            int priority2 = node2.getMovesMade() + node2.currentState.estimatedDistanceToGoal();

            int difference = priority1 - priority2;
            if (difference > 0) {
                return 1;
            } else if (difference < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
