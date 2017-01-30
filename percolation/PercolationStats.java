import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by tangmin on 17/1/29.
 */
public class PercolationStats {
    private double[] dataList;
    // perform trials independent experiments on an n-by-n grid
    private int trials;
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        dataList = new double[trials];
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        int row;
        int col;
        Percolation p;
        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);
            while (true) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
                if (p.percolates()) {
                    dataList[i] = ((double) p.numberOfOpenSites())/ (n * n);
                    break;
                }
            }
        }
    }
    // sample mean of percolation threshold
    public double mean() {
        double mean = StdStats.mean(dataList);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double stddev = StdStats.stddev(dataList);
        return stddev;
    }
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        double stddev = this.stddev();
        double mean = this.mean();
        double sqrtT = Math.sqrt(this.trials);
        return (mean - (1.96 * stddev) / sqrtT);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double stddev = this.stddev();
        double mean = this.mean();
        double sqrtT = Math.sqrt(this.trials);
        return (mean + (1.96 * stddev) / sqrtT);
    }
    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, trials);
        double mean = p.mean();
        double stddev = p.stddev();
        double lo = p.confidenceLo();
        double hi = p.confidenceHi();

        StdOut.printf("mean = %f\n", mean);
        StdOut.printf("stddev = %f\n", stddev);
        StdOut.printf("95%% confidence interval = [%f, %f]", lo, hi);
    }

}
