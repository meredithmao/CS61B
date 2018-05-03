public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader oR = new ObjectReader(args[0]);
        BinaryTrie bT = (BinaryTrie) oR.readObject();
        int numberSymbols = (Integer) oR.readObject();
        BitSequence bits = (BitSequence) oR.readObject();
        char[] original = new char[numberSymbols];
        for (int i = 0; i < numberSymbols; i++) {
            Match m = bT.longestPrefixMatch(bits);
            original[i] = m.getSymbol();
            bits = bits.allButFirstNBits(m.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], original);
    }
}
