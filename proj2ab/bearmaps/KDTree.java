package bearmaps;

import java.util.List;

public class KDTree implements PointSet {

    private Node root;

    private class Node {
        private Point point;
        private Node first, second;
        private boolean horizontal;

        Node(Point p, boolean h) {
            point = p;
            horizontal = h;
        }

        public boolean divHorizontal() {
            return horizontal;
        }

        public double getX() {
            return point.getX();
        }

        public double getY() {
            return point.getY();
        }
    }

    private int comparePointsValue(Node last, Point p1) {
        if (last.divHorizontal()) {
            if (last.getX() <= p1.getX()) {
                return 1;
            } else {
                return -1;
            }
        } else {
            if (last.getY() >= p1.getY()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public KDTree(List<Point> points) {
        Node last;
        boolean added;
        for (Point point : points) {
            last = root;
            if (last == null) {
                root = new Node(point, true);
            } else {
                added = false;
                while (!added) {
                    if (comparePointsValue(last, point) > 0) {
                        if (last.point.equals(point)) {
                            last = new Node(point, last.divHorizontal());
                            added = true;
                        } else if (last.second == null) {
                            last.second = new Node(point, !last.divHorizontal());
                            added = true;
                        } else {
                            last = last.second;
                        }
                    } else {
                        if (last.point.equals(point)) {
                            last = new Node(point, last.divHorizontal());
                            added = true;
                        } else if (last.first == null) {
                            last.first = new Node(point, !last.divHorizontal());
                            added = true;
                        } else {
                            last = last.first;
                        }
                    }
                }
            }
        }
    }
    
    private boolean whichSideIsNearer(Node n, Point goal) {
        if (n.divHorizontal()) {
            return n.getX() >= goal.getX();
        } else {
            return n.getY() <= goal.getY();
        }
    }

    private Node nearestHelper(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        double bestDistance = Point.distance(best.point, goal);
        Node nearerSide;
        Node furtherSide;

        if (Point.distance(n.point, goal) <= bestDistance) {
            best = n;
        }

        if (whichSideIsNearer(n, goal)) {
            nearerSide = n.first;
            furtherSide = n.second;
        } else {
            nearerSide = n.second;
            furtherSide = n.first;
        }

        best = nearestHelper(nearerSide, goal, best);
        bestDistance = Point.distance(best.point, goal);

        if ((n.divHorizontal()
                && Point.distance(new Point(n.getX(), goal.getY()), goal)
                <= bestDistance)
                || (!n.divHorizontal()
                && Point.distance(new Point(goal.getX(), n.getY()), goal)
                <= bestDistance)) {
            best = nearestHelper(furtherSide, goal, best);
        }
        return best;
    }

    @Override
    public Point nearest(double x, double y) {
        return nearestHelper(root, new Point(x, y), root).point;
    }

}
