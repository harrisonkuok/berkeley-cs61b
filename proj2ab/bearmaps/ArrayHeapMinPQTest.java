package bearmaps;

import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    private static Random r = new Random(500);

    @Test
    public void addTest() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();

        test.add("b", 4.0);
        test.add("g", 5.0);
        test.add("e", 2.0);
        test.add("k", 1.0);
        test.add("v", 3.0);
        test.add("p", 6.0);
        test.add("y", 7.0);

        assertEquals("k", test.getSmallest());
        assertEquals("k", test.removeSmallest());
        assertEquals("e", test.getSmallest());
        assertEquals("e", test.removeSmallest());
        assertEquals("v", test.getSmallest());
        assertEquals("v", test.removeSmallest());
        assertEquals("b", test.getSmallest());
        assertEquals("b", test.removeSmallest());
        assertEquals("g", test.getSmallest());
        assertEquals("g", test.removeSmallest());
        assertEquals("p", test.getSmallest());
        assertEquals("p", test.removeSmallest());
        assertEquals("y", test.getSmallest());
        assertEquals("y", test.removeSmallest());
    }

    @Test
    public void containTest() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();

        test.add("b", 4.0);
        test.add("g", 5.0);
        test.add("e", 2.0);
        test.add("k", 1.0);
        test.add("v", 3.0);
        test.add("p", 6.0);
        test.add("y", 7.0);

        assertTrue(test.contains("b"));
        assertTrue(test.contains("g"));
        assertTrue(test.contains("e"));

        test.removeSmallest();
        assertFalse(test.contains("k"));
    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();

        test.add("b", 4.0);
        test.add("g", 5.0);
        test.add("e", 2.0);
        test.add("k", 1.0);
        test.add("v", 3.0);
        test.add("p", 6.0);
        test.add("y", 7.0);

        test.changePriority("k", 8);
        assertEquals("e", test.getSmallest());
        test.changePriority("v", 9);
        assertEquals("e", test.removeSmallest());
        assertEquals("b", test.removeSmallest());
        assertEquals("g", test.removeSmallest());
        assertEquals("p", test.removeSmallest());
        assertEquals("y", test.removeSmallest());
        assertEquals("k", test.removeSmallest());
        assertEquals("v", test.removeSmallest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {

        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.add("a", 0.0);
        test.add("a", 0.0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() {

        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.getSmallest();
    }

    @Test
    public void randomizedAddTest() {
        randomizedTestHelper(10000, false);
    }

    @Test
    public void randomizedChangePriorityTest() {
        randomizedTestHelper(10000, true);
    }

    @Test
    public void changePriorityTimeTest() {
        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Double> priorities;
        List<Double> newPriorities;
        ArrayHeapMinPQ<Integer> test;

        Stopwatch sw;
        double timeInSeconds;

        for (int N = 31250; N <= 1000000; N *= 2) {
            ns.add(N);
            opCounts.add(30000);
        }

        for (int N : ns) {
            priorities = randomizedNode(N);
            newPriorities = randomizedNode(30000);
            test = new ArrayHeapMinPQ<>();

            for (int i = 1; i < N; i += 1) {
                test.add(i, priorities.get(i - 1));
            }

            sw = new Stopwatch();

            for (int i = 0; i < 30000; i += 1) {
                test.changePriority(r.nextInt(30000 - 1) + 1, newPriorities.get(i));
            }

            timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }

        printTimingTable(ns, times, opCounts);
    }

    @Test
    public void addTimeTest() {
        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        List<Double> priorities;
        ArrayHeapMinPQ<Integer> test;

        Stopwatch sw;
        double timeInSeconds;

        for (int N = 31250; N <= 10000000; N *= 2) {
            ns.add(N);
            opCounts.add(N);
        }

        for (int N : ns) {
            priorities = randomizedNode(N);
            test = new ArrayHeapMinPQ<>();

            sw = new Stopwatch();

            for (int i = 1; i < N; i += 1) {
                test.add(i, priorities.get(i - 1));
            }

            timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
        }

        printTimingTable(ns, times, opCounts);
    }

    private List<Double> randomizedNode(int N) {
        List<Double> priorities = new ArrayList<>();
        for (int i = 0; i < N; i += 1) {
            priorities.add(r.nextDouble());
        }
        return priorities;
    }

    private void randomizedTestHelper(int numNode, boolean changePriority) {
        List<Double> priorities = randomizedNode(numNode);
        NaiveMinPQ<Integer> nmpq = new NaiveMinPQ<>();
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 1; i <= numNode; i += 1) {
            nmpq.add(i, priorities.get(i - 1));
            test.add(i, priorities.get(i - 1));
        }

        if (changePriority) {
            List<Double> newPriorities = randomizedNode(numNode);
            for (int i = 1; i <= numNode; i += 1) {
                nmpq.changePriority(i, newPriorities.get(i - 1));
                test.changePriority(i, newPriorities.get(i - 1));
            }
        }

        for (int i = 0; i < numNode; i += 1) {
            assertEquals(nmpq.removeSmallest(), test.removeSmallest());
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



}


