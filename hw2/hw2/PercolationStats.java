package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private int times;
    private double[] percolatesTimes;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        times = T;
        percolatesTimes = new double[T];

        Percolation grid;
        double percolatesTime;
        int randRow;
        int randCol;

        for (int i = 0; i < T; i += 1) {
            grid = pf.make(N);
            percolatesTime = 0;
            for (int j = 0; !grid.percolates(); j += 1) {
                randRow = StdRandom.uniform(N);
                randCol = StdRandom.uniform(N);
                grid.open(randRow, randCol);
            }
            percolatesTime = grid.numberOfOpenSites() / (double) (N * N);
            percolatesTimes[i] = percolatesTime;
        }
    }

    public double mean() {
        return StdStats.mean(percolatesTimes);
    }

    public double stddev() {
        return StdStats.stddev(percolatesTimes);
    }

    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(times));
    }

    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(times));
    }

}


