package Utilites;


import Models.Pixel;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class Algorithms {



//    public static void breadthFirstSearch(Graph graph, int startX, int startY, ImageView imageView) {
//        // A set to keep track of visited nodes (pixels) using their coordinates as a unique identifier
//        HashSet<String> visited = new HashSet<>();
//
//        // Queue to support BFS traversal
//        Queue<Pixel<?>> queue = new ArrayDeque<>();
//
//        // Starting point
//        Pixel<?> startPixel = graph.pixelGraph[startY][startX];
//        if (startPixel == null) return; // If start pixel is null, exit
//
//        queue.add(startPixel);
//        visited.add(startY + "," + startX); // Mark the starting pixel as visited
//
//        while (!queue.isEmpty()) {
//            Pixel<?> currentPixel = queue.remove();
//            //System.out.println("Visited: " + currentPixel); // Process current pixel
//
//            // Loop through all adjacent pixels
//            for (Pixel<?> adjPixel : currentPixel.adjList) {
//                int adjX = 200;
//                int adjY = 200;
//
//
//
//                if (!visited.contains(adjY + "," + adjX)) {
//                    queue.add(adjPixel);
//                    visited.add(adjY + "," + adjX);
//                }
//                Pane pane = (Pane) imageView.getParent();
//                Circle circle = new Circle();
//                circle.setCenterX(adjX);
//                circle.setCenterY(adjY);
//                circle.setRadius(1);
//                circle.setFill(Color.PINK);
//                ((Pane) imageView.getParent()).getChildren().add(circle);
//            }
//        }
//    }


    public static <T> void breadthFirstSearch(Graph graph, int startX, int startY, ImageView imageView) {
        HashSet<String> visited = new HashSet<>();
        Queue<Pixel<T>> queue = new ArrayDeque<>();

        Pixel<T> startPixel = graph.pixelGraph[startY][startX];
        if (startPixel == null) return; // If the starting pixel is null, exit.

        queue.add(startPixel);
        visited.add(startPixel.getCoordinates()); // Mark the starting pixel as visited.

        while (!queue.isEmpty()) {
            Pixel<T> currentPixel = queue.remove();
            int currentX = currentPixel.x;
            int currentY = currentPixel.y;

            // Draw a circle at the current pixel's position.
            Platform.runLater(() -> {
                Pane pane = (Pane) imageView.getParent();
                Circle circle = new Circle(currentX, currentY, 1, Color.PINK);
                pane.getChildren().add(circle);
            });

            // Visit all adjacent pixels.
            for (Pixel<T> adjPixel : currentPixel.adjList) {
                if (!visited.contains(adjPixel.getCoordinates())) {
                    queue.add(adjPixel);
                    visited.add(adjPixel.getCoordinates());
                }
            }
        }
    }




}
