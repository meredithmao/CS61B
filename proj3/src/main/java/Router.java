

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
//        try {
//            GraphDB.Node nodeStart = g.nodeMap.get(g.closest(stlon, stlat));
//            GraphDB.Node nodeFinal = g.nodeMap.get(g.closest(destlon, destlat));
//            Map<Long, Double> disTo = new HashMap<>();
//            Map<Long, Long> parent = new HashMap<>();
//            Comparator c = (o1, o2) -> {
//                double check = (disTo.get(o1) - disTo.get(o2));
//                return (int) (check * 23847850);
//            };
//            PriorityQueue<Long> priorityQueue = new PriorityQueue<>(c);
//            //added all id's to my priority Queue
//            for (long id : g.nodeMap.keySet()) {
//                disTo.put(id, Double.POSITIVE_INFINITY);
//                priorityQueue.add(id);
//            }
//            disTo.put(nodeStart.id, 0.0);
//            priorityQueue.remove(nodeStart.id);
//            priorityQueue.add(nodeStart.id);
//            while (!priorityQueue.isEmpty()) {
//                long v = priorityQueue.poll();
//                for (long w : g.nodeMap.get(v).adjacentNodes) {
//                    //distance from where you're at to the parent
//                    double distance = g.distance(v, w) + disTo.get(v);
//                    if (distance < disTo.get(w)) {
//                        disTo.put(w, distance);
//                        priorityQueue.remove(w);
//                        priorityQueue.add(w);
//                        parent.put(w, v);
//                    }
//                    //key: the node value: the parent
//                }
//            }
//            LinkedList<Long> finalList = new LinkedList<>();
//            long currentParent = nodeFinal.id;
//            while (currentParent != nodeStart.id) {
//                finalList.addFirst(currentParent);
//                currentParent = parent.get(currentParent);
//            }
//            finalList.addFirst(nodeStart.id);
//            return finalList;
//        } catch (NullPointerException E) {
//            return new LinkedList<>();
//        }
        Map<Long, Double> bestKnownDistance = new HashMap<>();
        Map<Long, Long> parent = new HashMap<>();
        Map<Long, Double> hScore = new HashMap<>();

        GraphDB.Node nodeStart = g.nodeMap.get(g.closest(stlon, stlat));
        GraphDB.Node nodeFinal = g.nodeMap.get(g.closest(destlon, destlat));

        Comparator<Long> check = Comparator.comparingDouble(hScore::get);

        PriorityQueue<Long> fringe = new PriorityQueue<>(check);
        fringe.add(nodeStart.id);
        bestKnownDistance.put(nodeStart.id, 0.0);
        hScore.put(nodeStart.id, g.distance(nodeStart.id, nodeFinal.id));

        while (!fringe.isEmpty()) {
            Long v = fringe.poll();
            if (v == null) {
                break;
            }
            if (v == nodeFinal.id) {
                break;
            } else {
                for (Long w : g.nodeMap.get(v).adjacentNodes) {
                    double current = bestKnownDistance.getOrDefault(w, Double.MAX_VALUE);
                    double possible = bestKnownDistance.get(v) + g.distance(v, w);
                    if (possible < current) {
                        fringe.remove(w);
                        double hDistance = g.distance(w, nodeFinal.id);
                        bestKnownDistance.put(w, possible);
                        hScore.put(w, bestKnownDistance.get(w) + hDistance);
                        fringe.add(w);
                        //key is the child and value is the parent
                        parent.put(w, v);
                    }
                }
            }
        }
        LinkedList<Long> finalList = new LinkedList<>();
        long currentParent = nodeFinal.id;
        while (currentParent != nodeStart.id) {
            finalList.addFirst(currentParent);
            currentParent = parent.get(currentParent);
        }
        finalList.addFirst(nodeStart.id);
        return finalList;

    }
    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null; // FIXME
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
