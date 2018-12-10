/* *****************************************************************************
 *  Name:Melnikova Oxana
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


public class Solver {
    private final boolean result;
    private Move lastmove = null;

    private class Move implements Comparable<Move> {
        private final int moves;
        private final Board node;
        private final Move predsessor;
        private final int manhattan;


        public Move(Board node) {
            moves = 0;
            predsessor = null;
            this.node = node;
            manhattan = node.manhattan();
        }

        public Move(Board node, Move pr) {
            moves = pr.moves + 1;
            predsessor = pr;
            this.node = node;
            manhattan = node.manhattan();
        }

        public int compareTo(Move that) {
            // if (this.node.equals(that.node)) return 0;
            return (this.manhattan - that.manhattan) + (this.moves - that.moves);
        }

    }


    public Solver(
            Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null) throw new java.lang.IllegalArgumentException();

        MinPQ<Move> pq;
        MinPQ<Move> pqTwin;

        Move move = new Move(initial);
        Board twin = initial.twin();
        Move moveTwin = new Move(twin);

        pqTwin = new MinPQ<Move>();
        pqTwin.insert(moveTwin);

        pq = new MinPQ<Move>();
        pq.insert(move);

        if (initial.isGoal()) {
            result = true;
            lastmove = move;
            return;
            
        }

        for (Board board : initial.neighbors()) {
            pq.insert(new Move(board, move));
        }

        for (Board board : twin.neighbors()) {
            pqTwin.insert(new Move(board, moveTwin));
        }

        while (!move.node.isGoal() && !moveTwin.node.isGoal()) {
            move = pq.delMin();
            moveTwin = pqTwin.delMin();

            for (Board board : move.node.neighbors()) {
                if (move.predsessor != null) {
                    boolean presence = false;
                    for (Board neighbBoard : move.predsessor.node.neighbors()) {
                        if (board.equals(neighbBoard)) {
                            presence = true;
                            break;
                        }
                    }
                    if (!board.equals(move.predsessor.node) && !presence)
                        pq.insert(new Move(board, move));
                }
                else pq.insert(new Move(board, move));
            }

            for (Board board : moveTwin.node.neighbors()) {
                if (moveTwin.predsessor != null) {
                    boolean presence = false;
                    for (Board neighbBoard : moveTwin.predsessor.node.neighbors()) {
                        if (board.equals(neighbBoard)) {
                            presence = true;
                            break;
                        }
                    }
                    if (!board.equals(moveTwin.predsessor.node) && !presence)
                        pqTwin.insert(new Move(board, moveTwin));
                }
                else pqTwin.insert(new Move(board, moveTwin));
            }


        }

        if (move.node.isGoal()) {
            result = true;
            lastmove = move;
        }
        else result = false;

    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return result;
    }

    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (isSolvable()) return lastmove.moves;
        return -1;
    }

    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (!isSolvable()) return null;
        Stack<Board> res = new Stack<Board>();
        Move next = lastmove;
        res.push(next.node);
        while (next.predsessor != null) {
            res.push(next.predsessor.node);
            next = next.predsessor;
        }
        return res;
    }

    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
