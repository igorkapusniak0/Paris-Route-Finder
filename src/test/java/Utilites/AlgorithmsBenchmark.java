package Utilites;


import Models.GraphNode;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations=3)
@Warmup(iterations=2)
@Fork(value=1)
@State(Scope.Benchmark)

public class AlgorithmsBenchmark {

    private GraphNode startNode;
    private GraphNode endNode;
    private List<GraphNode> poiList;
    private Set<GraphNode> avoidSet;

    @Setup
    public void setup() {
        startNode = new GraphNode("Start", 0, 0, null);
        endNode = new GraphNode("End", 100, 100, null);

        List<GraphNode> additionalNodes = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            GraphNode newNode = new GraphNode("Node" + i, i * 7, i * 11, null);
            additionalNodes.add(newNode);
        }

        poiList = new ArrayList<>(Arrays.asList(startNode, endNode));
        poiList.addAll(additionalNodes);

        Random random = new Random();
        for (GraphNode node : poiList) {
            int connections = random.nextInt(3) + 1;

            for (int i = 0; i < connections; i++) {
                GraphNode targetNode = poiList.get(random.nextInt(poiList.size()));

                if (!node.equals(targetNode)) {
                    node.connectToNodeUndirected(targetNode);
                }
            }
        }

        avoidSet = new HashSet<>();
    }

    @Benchmark
    public void benchmarkBFS() {
        Algorithms.BFSAlgorithm(startNode, endNode, avoidSet);
    }

    @Benchmark
    public void benchmarkBFSWithPOIs() {
        Algorithms.BFSWithPOIs(startNode, endNode, new LinkedList<>(poiList), avoidSet);
    }

    @Benchmark
    public void benchmarkDFSAllPaths() {
        Algorithms.DFSAlgorithmAllPaths(startNode, endNode, Integer.MAX_VALUE, avoidSet);
    }

    @Benchmark
    public void benchmarkDFS() {
        Algorithms.DFSAlgorithm(startNode, endNode, avoidSet);
    }

    @Benchmark
    public void benchmarkDijkstra() {
        startNode.listToHashMap();
        Algorithms.dijkstraAlgorithm(startNode, endNode, avoidSet);
    }

    @Benchmark
    public void benchmarkDijkstraWithPOIs() {
        startNode.listToHashMap();
        Algorithms.dijkstraWithPOIs(startNode, endNode, new LinkedList<>(poiList), avoidSet);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AlgorithmsBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
