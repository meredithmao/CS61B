package byog.Core;

public class ParseResult {
    private String wasdString;
    private String[] seed;

    ParseResult() {
    }

    ParseResult(String wasdString, String[] seed) {
        this.wasdString = wasdString;
        this.seed = seed;
    }

    public String getWasdString() {
        return wasdString;
    }

    public String[] getSeed() {
        return seed;
    }
}
