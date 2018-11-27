/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

public class Board {
    private MinPQ<>

    private int moves = 0;

    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        if (blocks == null) throw new java.lang.IllegalArgumentException();

    }

    public int dimension()                 // board dimension n
    {
        return tiles.length;

    }

    public int hamming()                   // number of blocks out of place
    {
        for (int i = 0; i < tiles.length)

    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {

    }

    public boolean isGoal()                // is this board the goal board?
    {

    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {

    }

    public boolean equals(Object y)        // does this board equal y?
    {

    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {

    }

    public String toString()               // string representation of this board (in the output format specified below)
    {

    }

    public static void main(String[] args) // unit tests (not graded)
    {

    }
}
