/* *****************************************************************************
 *  Name:    Oxana Melnikova
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96; // const
    private final int trials; // trials
    private final double[] fractions; // array of trials results

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials <= 0) throw new
                java.lang.IllegalArgumentException("n cannot be 0 or negative");
        this.trials = trials;
        fractions = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) percolation.open(row, col);
            }
            double f = (double) (percolation.numberOfOpenSites()) / (double) (n * n);
            fractions[i] = f;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    // test client (described below)
    public static void main(String[] args) {
        if (args.length > 1) {
            int n = Integer.parseInt(args[0]);
            int t = Integer.parseInt(args[1]);
            PercolationStats percStats = new PercolationStats(n, t);

            System.out.println(String.format("mean =%20f", percStats.mean()));
            System.out.println(String.format("stddev =%20f", percStats.stddev()));
            System.out.println(
                    String.format("95%% confidence interval =[%2f, %2f]", percStats.confidenceLo(),
                                  percStats.confidenceHi()));

        }
    }
}
