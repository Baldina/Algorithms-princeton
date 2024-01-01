/* *****************************************************************************
 *  Name:    Ada Lovelace
 *  NetID:   alovelace
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double sample_mean;
    private double standart_dev;
    private double low_end;
    private double high_end;
    private int trials_num;
    private int grid_size;
    private double sites;
    private int[] open_sites;
    private void PercolationStatsInit(int n, int trials) {
        trials_num = trials;
        grid_size = n;
        sites = n * n;
        int i = 0;
        open_sites = new int[trials];
        for (i = 0; i < trials; i++) {

            Percolation percolation = new Percolation(n);
            open_sites[i] = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    open_sites[i]++;
                }
            }
            //StdOut.println(open_sites[i]);
        }
    }
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        PercolationStatsInit(n, trials);
    }

    // sample mean of percolation threshold
    public double mean()
    {
        double summ = 0;
        for (int i = 0; i < trials_num; i++){
            summ = summ + open_sites[i];
        }
        sample_mean = summ / (sites * trials_num);
        return sample_mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        double summ1 = 0;
        for (int j = 0; j < trials_num; j++){
            //StdOut.println(open_sites[j]);
            //StdOut.println(sites);
            //StdOut.println(open_sites[j] / sites - sample_mean);
            summ1 = summ1 + ((open_sites[j] / sites - sample_mean) * (open_sites[j] / sites - sample_mean));
            //StdOut.println(summ1);
        }
        standart_dev = Math.sqrt(summ1 / trials_num);
        return standart_dev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        low_end = sample_mean - (1.96 * standart_dev / Math.sqrt(trials_num));
        //StdOut.println(low_end);
        return low_end;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        high_end = sample_mean + (1.96 * standart_dev / Math.sqrt(trials_num));
//        StdOut.println(high_end);
        return high_end;
    }

    // test client (see below)
    public static void main(String[] args)
    {
        PercolationStats per = new PercolationStats(100, 100);
        //per.PercolationStatsInit(100, 100);
        StdOut.println(per.mean());
        StdOut.println(per.stddev());
        StdOut.println(per.confidenceLo());
        StdOut.println(per.confidenceHi());
    }

}