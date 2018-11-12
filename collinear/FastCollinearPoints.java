/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> mysegments = new ArrayList<>();

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
    {
        Arrays.sort(points);
        Point[] copyPoints;
        checkInitial(points);
        Point p;
        boolean badslope = false;
        copyPoints = Arrays.copyOfRange(points, 0, points.length);

        for (int i = 0; i < points.length - 3; i++) {
            Arrays.sort(copyPoints);
            MergeX.sort(copyPoints, points[i].slopeOrder());
            int slopeCnt = 1;
            p = points[i];
            double slope = p.slopeTo(copyPoints[0]);
            for (int j = 1; j < copyPoints.length; j++) {
                Point pj = copyPoints[j];
                double nextslope = p.slopeTo(pj);
                if (slope == nextslope && !badslope) {
                    if (p.compareTo(pj) < 0) {
                        slopeCnt += 1;

                    }
                    else {
                        badslope = true;
                        slopeCnt = 1;
                    }
                }
                else {
                    badslope = false;
                    slope = nextslope;
                    if (slopeCnt >= 3) {
                        mysegments.add(new LineSegment(p,
                                                       copyPoints[j - 1]));
                        slopeCnt = 1;
                    }
                    else {
                        slopeCnt = 1;
                    }
                }
            }
            if (slopeCnt >= 3) {
                mysegments
                        .add(new LineSegment(p,
                                             copyPoints[copyPoints.length - 2]));

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

    private void checkInitial(Point[] points) {
        if (points == null) throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
            if (i != 0) {
                if (points[i - 1].equals(points[i])) throw new java.lang.IllegalArgumentException();
            }
        }
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
