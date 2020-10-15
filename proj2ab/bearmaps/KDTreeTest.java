package bearmaps;

import java.util.ArrayList;
import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    private static Random r = new Random(500);

    private List<Point> randomPoints(int N) {
        List<Point> pointList = new ArrayList<>();
        for (int i = 0; i < N; i += 1) {
            pointList.add(new Point(r.nextDouble(), r.nextDouble()));
        }
        return pointList;
    }

    private void randomizedTest(int pointNum, int queryNum) {
        List<Point> points = randomPoints(pointNum);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree test = new KDTree(points);

        List<Point> queries = randomPoints(queryNum);

        for (Point p : queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = test.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }

    private void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    @Test
    public void randomizedTest() {
        randomizedTest(1000, 200);
    }

    @Test
    public void randomizedTest2() {
        randomizedTest(10000, 2000);
    }

    @Test
    public void constructionTimeTest() {
        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Point> points;
        KDTree test;

        Stopwatch sw;
        double timeInSeconds;

        for (int N = 31250; N <= 1000000; N *= 2) {
            ns.add(N);
            opCounts.add(N);
        }

        for (int N : ns) {
            points = randomPoints(N);
            test = null;

            sw = new Stopwatch();
            test = new KDTree(points);

            timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }
        printTimingTable(ns, times, opCounts);
    }

    @Test
    public void nearestTimeTest() {
        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Point> points;
        List<Point> randomPoints;
        KDTree test;

        Stopwatch sw;
        double timeInSeconds;

        for (int N = 31250; N <= 1000000; N *= 2) {
            ns.add(N);
            opCounts.add(1000000);
        }

        for (int N : ns) {
            points = randomPoints(N);
            randomPoints = randomPoints(1000000);
            test = new KDTree(points);

            sw = new Stopwatch();

            for (Point p : randomPoints) {
                test.nearest(p.getX(), p.getY());
            }

            timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }
        printTimingTable(ns, times, opCounts);
    }

    @Test
    public void naiveNearestTimeTest() {
        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Point> points;
        List<Point> randomPoints;
        NaivePointSet test;

        Stopwatch sw;
        double timeInSeconds;

        for (int N = 31250; N <= 31250; N *= 2) {
            ns.add(N);
            opCounts.add(1000000);
        }

        for (int N : ns) {
            points = randomPoints(N);
            randomPoints = randomPoints(1000000);
            test = new NaivePointSet(points);

            sw = new Stopwatch();

            for (Point p : randomPoints) {
                test.nearest(p.getX(), p.getY());
            }

            timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }
        printTimingTable(ns, times, opCounts);
    }
}
