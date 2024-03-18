package Utilites;


import Models.Pixel;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;
import java.util.LinkedList;

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

    public static List<int[]> BFSAlgorithm(Pixel startPixel, Pixel endPixel) {
        Queue<Pixel> queue = new LinkedList<>();
        Map<Pixel, Pixel> cameFrom = new HashMap<>(); // Tracks the path
        Set<Pixel> visited = new HashSet<>();

        queue.add(startPixel);
        visited.add(startPixel);
        cameFrom.put(startPixel, null); // Start pixel has no parent

        while (!queue.isEmpty()) {
            Pixel current = queue.remove();
            if (current.equals(endPixel)) {
                return reconstructPath(cameFrom, endPixel); // Found the end pixel, reconstruct and return the path
            }

            for (Pixel neighbour : current.adjList) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    cameFrom.put(neighbour, current); // Track the path
                }
            }
        }
        return new LinkedList<>(); // Return an empty list if no path is found
    }

    private static List<int[]> reconstructPath(Map<Pixel, Pixel> cameFrom, Pixel endPixel) {
        List<int[]> path = new LinkedList<>();
        for (Pixel at = endPixel; at != null; at = cameFrom.get(at)) {
            path.add(0, at.getCoordinates()); // Add to the beginning of the list
        }
        return path;
    }

    public static <T> List<List<Pixel>> findAllPathsDepthFirst(Pixel from, List<Pixel> encountered, Pixel lookingfor){
        List<List<Pixel>> result=null, temp2;
        if(from.equals(lookingfor)) { //Found it
            List<Pixel> temp=new ArrayList<>(); //Create new single solution path list
            temp.add(from); //Add current node to the new single path list
            result=new ArrayList<>(); //Create new "list of lists" to store path permutations
            result.add(temp); //Add the new single path list to the path permutations list
            return result; //Return the path permutations list
        }
        if(encountered==null) encountered=new ArrayList<>(); //First node so create new (empty) encountered list
        encountered.add(from); //Add current node to encountered list
        for(Pixel adjNode : from.adjList){
            if(!encountered.contains(adjNode)) {
                temp2=findAllPathsDepthFirst(adjNode,new ArrayList<>(encountered),lookingfor); //Use clone of encountered list
//for recursive call!
                if(temp2!=null) { //Result of the recursive call contains one or more paths to the solution node
                    for(List<Pixel> x : temp2) //For each partial path list returned
                        x.add(0,from); //Add the current node to the front of each path list
                    if(result==null) result=temp2; //If this is the first set of solution paths found use it as the result
                    else result.addAll(temp2); //Otherwise append them to the previously found paths
                }
            }
        }
        return result;
    }










    /*public static <T> void breadthFirstSearch(Graph graph, int startX, int startY, ImageView imageView) {
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
    }*/




}
