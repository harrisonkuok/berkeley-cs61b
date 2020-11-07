package bearmaps.proj2c;

import bearmaps.proj2ab.DoubleMapPQ;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    private DoubleMapPQ<Vertex> fringe;
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private int explored;
    private double timeSpent;
    private HashMap<Vertex, Double> distTo;
    private HashMap<Vertex, Vertex> edgeTo;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();

        solution = new ArrayList<>();
        explored = 0;

        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        fringe = new DoubleMapPQ<>();
        Vertex p;
        List<WeightedEdge<Vertex>> neighborEdges;

        distTo.put(start, 0.0);
        edgeTo.put(start, null);
        fringe.add(start, 0);

        while (fringe.size() > 0 && !fringe.getSmallest().equals(end)) {
            p = fringe.removeSmallest();
            neighborEdges = input.neighbors(p);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e, input, end);
            }
            explored += 1;
            if (sw.elapsedTime() > timeout) {
                timeSpent = sw.elapsedTime();
                outcome = SolverOutcome.TIMEOUT;
                return;
            }
        }

        if (fringe.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            timeSpent = sw.elapsedTime();
            return;
        }

        for (Vertex v = end; v != null; v = edgeTo.get(v)) {
            solution.add(v);
        }
        reverseSolution();

        explored += 1;
        solutionWeight = distTo.get(end);
        outcome = SolverOutcome.SOLVED;
        timeSpent = sw.elapsedTime();
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return explored;
    }

    @Override
    public double explorationTime() {
        return timeSpent;
    }

    private void relax(WeightedEdge<Vertex> e, AStarGraph<Vertex> input, Vertex end) {
        Vertex p = e.from();
        Vertex q = e.to();
        Double w = e.weight();
        if (!distTo.containsKey(q) || distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                fringe.add(q, distTo.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }

    private void reverseSolution() {
        List<Vertex> temp = new ArrayList<>();
        for (int i = solution.size() - 1; i >= 0; i -= 1) {
            temp.add(solution.get(i));
        }
        solution = temp;
    }
}
