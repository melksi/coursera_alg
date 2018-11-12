/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> mysegments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {

        Arrays.sort(points);
        checkInitial(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                double ijSlope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length - 1; k++) {
                    double jkSlope = points[j].slopeTo(points[k]);
                    if (ijSlope == jkSlope) {
                        for (int f = k + 1; f < points.length; f++) {
                            double kfSlope = points[k].slopeTo(points[f]);
                            if (jkSlope == kfSlope) {
                                mysegments.add(new LineSegment(points[i], points[f]));
                            }
                        }
                    }
                }
            }

        }
    }

    private void checkInitial(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
            if (i != 0) {
                if (points[i - 1].equals(points[i])) throw new java.lang.IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return mysegments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
        return mysegments.toArray(new LineSegment[mysegments.size()]);
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}

