import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    private Node hm;
    private Map<Character, BitSequence> table = new HashMap<>();
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<Node>();
        //figure out the frequency for each character
        double freq;
        for (char c : frequencyTable.keySet()) {
            freq = frequencyTable.get(c);
            pq.insert(new Node(c, freq, null, null));
        }

        while (pq.size() > 1) {
            Node smaller = pq.delMin();
            Node larger = pq.delMin();
            Node parent = new Node('\0', smaller.frequency + larger.frequency, smaller, larger);
            pq.insert(parent);
        }

        this.hm = pq.delMin();

    }
    private static class Node implements Serializable, Comparable<Node> {
        private final char ch;
        private final double frequency;
        private final Node smaller, larger;

        Node(char ch, double frequency, Node smaller, Node larger) {
            this.ch = ch;
            this.frequency = frequency;
            this.smaller = smaller;
            this.larger = larger;
        }

        private boolean isLeaf() {
            return (smaller == null) && (larger == null);
        }

        @Override
        public int compareTo(Node o) {
            return (int) (this.frequency - o.frequency);
        }
    }
    public Match longestPrefixMatch(BitSequence querySequence) {
        Node imAt = hm;
        for (int query = 0; query < querySequence.length(); query++) {
            if (imAt.isLeaf()) {
                return new Match(querySequence.firstNBits(query), imAt.ch);
            } else {
                int c = querySequence.bitAt(query);
                if (c == 0) {
                    imAt = imAt.smaller;
                } else if (c == 1) {
                    imAt = imAt.larger;
                }
//                m = new Match(querySequence, imAt.ch);
            }
        }
        return new Match(querySequence, imAt.ch);
    }
    public Map<Character, BitSequence> buildLookupTable() {
        builtTable(table, hm, "");
        return table;
    }
    private void builtTable(Map<Character, BitSequence> lookUptable, Node n, String s) {
        if (!n.isLeaf()) {
            builtTable(lookUptable, n.smaller, s + "0");
            builtTable(lookUptable, n.larger, s + "1");
        } else {
            table.put(n.ch, new BitSequence(s));
        }
    }
}
