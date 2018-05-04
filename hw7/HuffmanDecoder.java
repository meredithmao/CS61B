public class HuffmanDecoder {
    public static void main(String[] args) {
        ObjectReader oR = new ObjectReader(args[0]);
        Object x = oR.readObject();
        Object y = oR.readObject();
        Object z = oR.readObject();
        BinaryTrie bT = (BinaryTrie) x;
        int numberSymbols = (Integer) y;
        BitSequence bits = (BitSequence) z;
        char[] original = new char[numberSymbols];
        for (int i = 0; i < numberSymbols; i++) {
            Match m = bT.longestPrefixMatch(bits);
            original[i] = m.getSymbol();
            bits = bits.allButFirstNBits(m.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], original);
    }
}
