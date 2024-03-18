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

    public static List<int[]> findPathDepthFirst(Pixel start, Pixel target) {
        Stack<Pixel> stack = new Stack<>();
        Map<Pixel, Pixel> cameFrom = new HashMap<>(); // To reconstruct the path later
        Set<Pixel> visited = new HashSet<>();

        // Start by pushing the start node onto the stack
        stack.push(start);

        while (!stack.isEmpty()) {
            Pixel current = stack.pop();

            // If the target is found, reconstruct the path from start to target
            if (current.equals(target)) {
                List<int[]> path = new ArrayList<>();
                for (Pixel at = target; at != null; at = cameFrom.get(at)) {
                    path.add(at.getCoordinates());
                }
                return path;
            }

            // If current node has not been visited, visit it
            if (!visited.contains(current)) {
                visited.add(current);

                // Explore neighbours
                for (Pixel neighbour : current.adjList) {
                    if (!visited.contains(neighbour) && !stack.contains(neighbour)) {
                        stack.push(neighbour);
                        cameFrom.put(neighbour, current); // Remember where we came from
                    }
                }
            }
        }
        return null; // Return null if no path is found
    }


    public static List<int[]> BFSAlgorithm(Pixel startPixel, Pixel endPixel) {
        Queue<Pixel> queue = new LinkedList<>();
        Map<Pixel, Pixel> cameFrom = new HashMap<>();
        Set<Pixel> visited = new HashSet<>();

        queue.add(startPixel);
        visited.add(startPixel);
        cameFrom.put(startPixel, null);

        while (!queue.isEmpty()) {
            Pixel current = queue.remove();
            if (current.equals(endPixel)) {
                List<int[]> path = new LinkedList<>();
                for (Pixel at = endPixel; at != null; at = cameFrom.get(at)) {
                    path.add(0, at.getCoordinates());
                }
                return path;
            }

            for (Pixel neighbour : current.adjList) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    cameFrom.put(neighbour, current);
                }
            }
        }
        return new LinkedList<>();
    }





}
