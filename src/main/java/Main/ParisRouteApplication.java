package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//import static Utilites.Algorithms.dijkstra;

public class ParisRouteApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ParisRouteApplication.class.getResource("ParisRoute.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1480, 830);
        stage.setTitle("Paris Route Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

//        int graph[][] = new int[][]{{0, 4, 0, 0, 0, 0, 0, 8, 0},
//                {4, 0, 8, 0, 0, 0, 0, 11, 0},
//                {0, 8, 0, 7, 0, 4, 0, 0, 2},
//                {0, 0, 7, 0, 9, 14, 0, 0, 0},
//                {0, 0, 0, 9, 0, 10, 0, 0, 0},
//                {0, 0, 4, 14, 10, 0, 2, 0, 0},
//                {0, 0, 0, 0, 0, 2, 0, 1, 6},
//                {8, 11, 0, 0, 0, 0, 1, 0, 7},
//                {0, 0, 2, 0, 0, 0, 6, 7, 0}
//        };
//        dijkstra(graph, 0);
    }
    }
