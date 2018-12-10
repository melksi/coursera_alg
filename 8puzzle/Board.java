/* *****************************************************************************
 *  Name: Melnikova Oxana
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] bloks;
    private final int dimension;
    private int manhattan = -1;


    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    {
        if (blocks == null) throw new java.lang.IllegalArgumentException();
        this.bloks = blocks.clone();

        dimension = blocks.length;
        manhattan = manhattan();

    }

    public int dimension()                 // board dimension n
    {
        return dimension;

    }

    public int hamming()                   // number of blocks out of place
    {

        int value;
        int sum = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (bloks[i][j] != 0) {
                    value = i * dimension + j + 1;
                    if (value != bloks[i][j]) sum += 1;
                }
            }
        }

        return sum;
    }

    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int validI, validJ;
        int sum = 0;
        if (manhattan != -1) return manhattan;


        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (bloks[i][j] != 0) {
                    validI = (bloks[i][j] - 1) / dimension;
                    validJ = (bloks[i][j] - 1) % dimension;
                    sum += Math.abs(validI - i) + Math.abs(validJ - j);
                }
            }
        }
        manhattan = sum;
        return sum;
    }

    public boolean isGoal()                // is this board the goal board?
    {
        if (manhattan == 0) return true;
        return false;
    }

    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (j != dimension - 1) {
                    if (bloks[i][j] != 0 && bloks[i][j + 1] != 0) {
                        return swapedBoard(i, j, i, j + 1);
                    }
                }
            }
        }
        return swapedBoard(0, 0, 0, 0);
    }


    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (bloks.length != that.dimension()) return false;


        for (int i = 0; i < bloks.length; i++) {
            for (int j = 0; j < bloks[i].length; j++) {
                if (bloks[i][j] != that.bloks[i][j]) return false;
            }
        }
        return true;

    }

    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> neighb = new Stack<Board>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (bloks[i][j] == 0) {
                    if (i != 0) {
                        neighb.push(swapedBoard(i, j, i - 1, j));
                    }
                    if (i != dimension - 1) {
                        neighb.push(swapedBoard(i, j, i + 1, j));
                    }
                    if (j != 0) {
                        neighb.push(swapedBoard(i, j, i, j - 1));
                    }
                    if (j != dimension - 1) {
                        neighb.push(swapedBoard(i, j, i, j + 1));
                    }
                    return neighb;
                }
            }
        }
        return neighb;
    }


    private int[][] createCopy(int[][] arr) {
        int[][] result = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                result[i][j] = arr[i][j];
            }
        }
        return result;
    }

    private Board swapedBoard(int i, int j, int newi, int newj) {
        int[][] newblocks;
        Board newBoard;
        newblocks = createCopy(bloks);
        int a = bloks[newi][newj];
        newblocks[i][j] = a;
        a = bloks[i][j];
        newblocks[newi][newj] = a;
        newBoard = new Board(newblocks);
        return newBoard;
    }


    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", bloks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

}
