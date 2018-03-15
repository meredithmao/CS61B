package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double [] threshold;
    private int numberOfExperiements;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.threshold = new double [T];
        this.numberOfExperiements = T;

        for (int i = 0; i < T; i++) {
            while (!pf.make(N).percolates()) {
                pf.make(N).open(StdRandom.uniform(0, N), StdRandom.uniform(0, N));
            }
            this.threshold[i] = (double) pf.make(N).numberOfOpenSites() / (N * N);
        }
    }
    public double mean() {
        return StdStats.mean(threshold);
    }
    public double stddev() {
        return StdStats.stddev(threshold);
    }
    public double confidenceLow() {
        return mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(numberOfExperiements);
    }
    public double confidenceHigh() {
        return mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(numberOfExperiements);

    }

}
