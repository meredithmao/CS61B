import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {

    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> table = new HashMap<>();
        char previous = inputSymbols[0];
        char current;
        int count = 0;

        for (char c : inputSymbols) {
            current = c;
            if (current == previous) {
                count++;
            } else {
                table.put(previous, count);
                previous = current;
                count = 1;
            }
        }
        return table;
    }
    public static void main(String[] args) {
        char[] input = FileUtils.readFile(args[0]);
        Map<Character, Integer> frequencyTable = buildFrequencyTable(input);
        BinaryTrie bt = new BinaryTrie(frequencyTable);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(bt);
        Map<Character, BitSequence> lookupTable = bt.buildLookupTable();
        List<BitSequence> bS = new ArrayList<>();
        for (char c : input) {
            BitSequence bits = lookupTable.get(c);
            bS.add(bits);
        }
        BitSequence hugeBit = BitSequence.assemble(bS);
        ow.writeObject(hugeBit);
    }
}
