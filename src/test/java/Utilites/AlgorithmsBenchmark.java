package Utilites;


import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.openjdk.jmh.annotations.*;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(iterations=3)
@Warmup(iterations=2)
@Fork(value=1)
@State(Scope.Benchmark)

public class AlgorithmsBenchmark {
}
