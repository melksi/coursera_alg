/* *****************************************************************************
 *  Name: Melnikova
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MergeX;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> mysegments = new ArrayList<>();

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
    {
        checkInitial1(points);
        Arrays.sort(points);
        checkInitial2(points);


        Point[] copyPoints;

        Point p, pj;
        boolean badslope;
        copyPoints = Arrays.copyOfRange(points, 0, points.length);

        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(copyPoints);
            MergeX.sort(copyPoints, points[i].slopeOrder());
            int slopeCnt = 1;
            p = points[i];
            badslope = false;
            double slope = p.slopeTo(copyPoints[0]);
            if (p.compareTo(copyPoints[0]) > 0) {
                badslope = true;
            }
            double nextslope;
            for (int j = 1; j < copyPoints.length; j++) {
                pj = copyPoints[j];
                nextslope = p.slopeTo(pj);
                if (badslope && slope != nextslope) {
                    badslope = false;
                    slopeCnt = 1;
                }

                if (p.compareTo(pj) > 0) {
                    badslope = true;
                }


                if (slope == nextslope) {
                    if (!badslope) slopeCnt += 1;
                }
                else {
                    slope = nextslope;
                    if (slopeCnt >= 3) {
                        mysegments.add(new LineSegment(p, copyPoints[j - 1]));

                    }
                    slopeCnt = 1;
                }
            }
            if (slopeCnt >= 3) {
                mysegments.add(new LineSegment(p, copyPoints[copyPoints.length - 1]));

            }

        }
    }


    public int numberOfSegments()        // the number of line segments
    {
        return mysegments.size();
    }

    public LineSegment[] segments()                // the line segments
    {
        return mysegments.toArray(new LineSegment[0]);
    }

    private void checkInitial1(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
        }
    }

    private void checkInitial2(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (i != 0) {
                if (points[i - 1].equals(points[i])) throw new java.lang.IllegalArgumentException();
            }
        }
    }

    // public static void main(String[] args) {
    //     In in = new In(args[0]);
    //     int n = in.readInt();
    //     Point[] points = new Point[n];
    //
    //     for (int i = 0; i < n; i++) {
    //         int x = in.readInt();
    //         int y = in.readInt();
    //         points[i] = new Point(x, y);
    //     }
    //
    //     // draw the points
    //     StdDraw.enableDoubleBuffering();
    //     StdDraw.setXscale(0, 32768);
    //     StdDraw.setYscale(0, 32768);
    //
    //     for (Point p : points) {
    //         p.draw();
    //     }
    //     StdDraw.show();
    //
    //     // print and draw the line segments
    //     FastCollinearPoints collinear = new FastCollinearPoints(points);
    //     for (LineSegment segment : collinear.segments()) {
    //         StdOut.println(segment);
    //         segment.draw();
    //     }
    //     StdDraw.show();
    // }
}
