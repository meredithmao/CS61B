package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private final MinPQ<SearchNode> searchNodes;
    private List<WorldState> solution = new ArrayList<>();

    public Solver(WorldState initial) {
        this.searchNodes = new MinPQ<>(priority);

        SearchNode rootNode = new SearchNode(initial, 0, null);
        searchNodes.insert(rootNode);

        SearchNode nodeToDelete;

        while (!searchNodes.min().currentState.isGoal()) {
            nodeToDelete = searchNodes.delMin();
            for (WorldState neighbor : nodeToDelete.getCurrentState().neighbors()) {
                if (nodeToDelete.getPreviousNode() == null
                        || !(nodeToDelete.getPreviousNode().getCurrentState().equals(neighbor))) {
                    searchNodes.insert(new SearchNode(neighbor,
                            nodeToDelete.getMovesMade() + 1, nodeToDelete));
                }
            }
        }

        nodeToDelete = searchNodes.delMin();
        this.solution.add(nodeToDelete.getCurrentState());
        while ((nodeToDelete = nodeToDelete.getPreviousNode()) != null) {
            this.solution.add(0, nodeToDelete.getCurrentState());
        }
    }

//    private boolean sameState(SearchNode prevNode, WorldState state) {
//        SearchNode parentNode = prevNode;
//        while((parentNode = parentNode.getPreviousNode()) != null) {
//            if(parentNode.getCurrentState().equals(state)) {
//                return true;
//            }
//        }
//        return false;
//    }

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
        private int distance;
        private int priorityValue;

        //constructor for SearchNode Object
        SearchNode(WorldState initial, int numberOfMoves, SearchNode prevNode) {
            this.currentState = initial;
            this.movesMade = numberOfMoves;
            this.previousNode = prevNode;
            this.distance = initial.estimatedDistanceToGoal();
            this.priorityValue = this.movesMade + this.distance;
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

        public int getPriorityValue() {
            return priorityValue;
        }
    }

    private class PriorityNode implements Comparator<SearchNode> {
        public int compare(SearchNode node1, SearchNode node2) {
            int difference = node1.getPriorityValue() - node2.getPriorityValue();
            return Integer.compare(difference, 0);
        }
    }
    private final Comparator<SearchNode> priority = new PriorityNode();
}
