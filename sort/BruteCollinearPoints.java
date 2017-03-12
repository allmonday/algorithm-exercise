/**
 * Created by tangmin on 17/2/16.
 */
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        checkDuplicated(points);
        ArrayList<LineSegment> founded = new ArrayList<>();

        Point[] pointBack = Arrays.copyOf(points, points.length);
        Arrays.sort(pointBack);

        for (int p = 0; p < pointBack.length - 3; p++) {
            for (int q = p + 1; q < pointBack.length - 2; q++) {
                for (int r = q + 1; r < pointBack.length - 1; r++) {
                    for (int s = r + 1; s < pointBack.length; s++) {
                        if (pointBack[p].slopeTo(pointBack[q]) == pointBack[p].slopeTo(pointBack[r]) &&
                                pointBack[p].slopeTo(pointBack[q]) == pointBack[p].slopeTo(pointBack[s])) {
                            founded.add(new LineSegment(pointBack[p], pointBack[s]));
                        }
                    }
                }
            }
        }

        segments = founded.toArray(new LineSegment[founded.size()]);
    }

    public int numberOfSegments() { // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments());
    }

    private void checkDuplicated(Point[] points) {
        for (int i = 0; i < points.length-1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)  {
                    throw new IllegalArgumentException("duplicated entries");
                }
            }
        }
    }
}
