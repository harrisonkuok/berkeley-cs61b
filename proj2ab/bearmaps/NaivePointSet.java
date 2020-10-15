package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    List<Point> pointList;

    public NaivePointSet(List<Point> points) {
        pointList = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point goalPoint = new Point(x, y);
        Point nearestPoint = pointList.get(0);
        double nearestDistance = Point.distance(nearestPoint, goalPoint);
        Point testPoint;
        for (int i = 1; i < pointList.size(); i += 1) {
            testPoint = pointList.get(i);
            if (Point.distance(testPoint, goalPoint) <= nearestDistance) {
                nearestDistance = Point.distance(testPoint, goalPoint);
                nearestPoint = testPoint;
            }
        }
        return nearestPoint;
    }
}
