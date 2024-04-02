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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations=3)
@Warmup(iterations=2)
@Fork(value=1)
@State(Scope.Benchmark)
public class APIBenchmark {



    private Image testImage;
    {
        try {
            testImage = new Image(new FileInputStream("src/main/resources/Image/full-image630.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Graph graph;
    private ArrayList<GraphNode> POIs;



    @Benchmark
    public void benchmarkConvertToBlackAndWhite() {
        API.convertToBlackAndWhite(testImage);
    }

    @Benchmark
    public void benchmarkConvertToPixels() {
        API.convertToPixels(testImage, graph);
    }

    @Benchmark
    public void benchmarkGraphConnections() {
        API.graphConnections(testImage, graph);
    }

    @Benchmark
    public void benchmarkReadInDatabase() {
        API.readInDatabase();
    }

    @Benchmark
    public void benchmarkPOILinks() {
        API.poiLinks(POIs);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(APIBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
