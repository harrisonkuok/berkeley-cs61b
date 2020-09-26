import java.util.ArrayList;
import java.util.List;

/**
 * Class that collects timing information about AList construction.
 */
public class TimeAList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
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

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        List<Integer> ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        AList<Integer> test = new AList<>();
        int numOps;

        Stopwatch sw;
        double timeInSeconds;

        for (int N = 1000; N <= 128000; N *= 2) {
            ns.add(N);
        }

        for (int index = 0; index < ns.size(); index += 1) {
            test = new AList<>();
            numOps = ns.get(index);

            sw = new Stopwatch();
            for (int i = 0; i < numOps; i += 1) {
                test.addLast(1);
            }
            timeInSeconds = sw.elapsedTime();

            times.add(timeInSeconds);
            opCounts.add(numOps);
        }
        printTimingTable(ns, times, opCounts);
    }
}

