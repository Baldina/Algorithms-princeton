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
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

    private WeightedQuickUnionUF ufObject;
    private int N = 0;
    private int N2 = 0;
    private boolean[][] grid_site;
    //private boolean isPercolates;
    private int openSites = 0;
    //creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        //StdOut.println("Percolation");
        //StdOut.println(n);
        if (n < 0) throw new IllegalArgumentException();
        N = n;
        N2 = n * n;
        grid_site = new boolean[N][N];
        // N2 - top virtual site
        // N2+1 - bottom virtual site
        ufObject = new WeightedQuickUnionUF(N2 + 2);
       // isPercolates = false;
        // Union virtual sites with certen rows
        for (int i = 0; i < n; i++){
            ufObject.union(i, N2);
            ufObject.union((n - 1) * n + i, N2 + 1);
        }
    }
    // row and coll from 0
    private boolean IsGetFull(int row, int col)
    {
        boolean connectedTop = false;
        boolean connectedBottom = false;
        for (int i = 0; i < N; i++)
        {
            connectedTop = connected_sites(row * N + col, i);
            if (connectedTop) {
                //StdOut.printf("connectedTop with index = %d row = %d col = %d \n", i, row,col);
                break;
            }
        }
        for (int i = 0; i < N; i+=1)
        {
            int bottom_ind = (N - 1) * N + i;
            connectedBottom = connected_sites(row * N + col, bottom_ind);
            if (connectedBottom) {
                //StdOut.printf("connectedButtom with index = %d row = %d col = %d \n", i, row,col);
                break;
            }
        }
        //StdOut.printf("row = %d ", row);
        // StdOut.printf("col = %d ", col);
        // StdOut.println(connectedTop);
        // StdOut.println(connectedBottom);
        return (connectedTop && connectedBottom);
    }

    private void union(int prow, int pcol, int row, int col)
    {
        if (row < 0 || row >= N ||
                col < 0 || col >= N)  return;
        if (!grid_site[row][col]) return;
        ufObject.union(prow * N + pcol, row * N + col);
    }




    private int getIndex(int row, int col)
    {
        return row * N + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        row--;
        col--;
        if (row < 0 || row >= N ||
             col < 0 || col >= N)  throw new IllegalArgumentException();
        if (!grid_site[row][col]) {
            int index = getIndex(row, col);
            // open
            grid_site[row][col] = true;
            // union
            union(row, col, row, col - 1);
            union(row, col, row - 1, col);
            union(row, col, row, col + 1);
            union(row, col, row + 1, col);
            openSites++;
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        row--;
        col--;
        if (row < 0 || row >= N ||
                col < 0 || col >= N)  throw new IllegalArgumentException();

        return grid_site[row][col];
    }

    private boolean connected_sites(int p, int q)
    {
        return ufObject.find(p) == ufObject.find(q);
    }
    // is the site (row, col) full?

    public boolean isFull(int row, int col)
    {
        row--;
        col--;
        if (row < 0 || row >= N ||
                col < 0 || col >= N)  throw new IllegalArgumentException();
        //full_site[row][col] = IsGetFull(row, col);
        //return full_site[row][col];
        //int index = getIndex(row, col);
        //return connected_sites(index, N2) && connected_sites(index, N2 + 1);
        return IsGetFull(row, col);
    }

    //returns the number of open sites
    public int numberOfOpenSites()
    {
        return openSites;
    }

    //does the system percolate?
    public boolean percolates()
    {
        return connected_sites(N2, N2 + 1);
    }

    //test client (optional)
    public static void main(String[] args)
    {
    }

}