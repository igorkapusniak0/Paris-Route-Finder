package Utilites;


import Models.GraphNode;
import Models.Pixel;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.*;
import java.util.LinkedList;
import java.util.function.Function;

public class Algorithms {

    public static List<GraphNode> findPathDepthFirst(GraphNode start, GraphNode target) {
        Stack<GraphNode> stack = new Stack<>();
        Map<GraphNode, GraphNode> cameFrom = new HashMap<>(); // To reconstruct the path later
        Set<GraphNode> visited = new HashSet<>();

        // Start by pushing the start node onto the stack
        stack.push(start);

        while (!stack.isEmpty()) {
            GraphNode current = stack.pop();

            // If the target is found, reconstruct the path from start to target
            if (current.equals(target)) {
                List<GraphNode> path = new ArrayList<>();
                for (GraphNode at = target; at != null; at = cameFrom.get(at)) {
                    path.add(at);
                }
                return path;
            }

            // If current node has not been visited, visit it
            if (!visited.contains(current)) {
                visited.add(current);

                // Explore neighbours
                for (GraphNode neighbour : current.adjList) {
                    if (!visited.contains(neighbour) && !stack.contains(neighbour)) {
                        stack.push(neighbour);
                        cameFrom.put(neighbour, current); // Remember where we came from
                    }
                }
            }
        }
        return null; // Return null if no path is found
    }


    public static List<GraphNode> BFSAlgorithm(GraphNode startPixel, GraphNode endPixel) {
        Queue<GraphNode> queue = new LinkedList<>();
        Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
        Set<GraphNode> visited = new HashSet<>();

        queue.add(startPixel);
        visited.add(startPixel);
        cameFrom.put(startPixel, null);

        while (!queue.isEmpty()) {
            GraphNode current = queue.remove();
            if (current.equals(endPixel)) {
                List<GraphNode> path = new LinkedList<>();
                for (GraphNode at = endPixel; at != null; at = cameFrom.get(at)) {
                    path.add(0, at);
                }
                return path;
            }

            for (GraphNode neighbour : current.adjList) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    cameFrom.put(neighbour, current);
                }
            }
        }
        return new LinkedList<>();
    }
//    public static <T extends GraphNode<T>> List<T> BFSAlgorithm2(T startNode, T endNode) {
//        Queue<T> queue = new LinkedList<>();
//        Map<T, T> cameFrom = new HashMap<>();
//        Set<T> visited = new HashSet<>();
//
//        queue.add(startNode);
//        visited.add(startNode);
//        cameFrom.put(startNode, null);
//
//        while (!queue.isEmpty()) {
//            T current = queue.remove();
//            if (current.equals(endNode)) {
//                List<T> path = new LinkedList<>();
//                for (T at = endNode; at != null; at = cameFrom.get(at)) {
//                    path.add(0, at);
//                }
//                return path;
//            }
//
//            for (T neighbour : current.getNeighbours()) {
//                if (!visited.contains(neighbour)) {
//                    visited.add(neighbour);
//                    queue.add(neighbour);
//                    cameFrom.put(neighbour, current);
//                }
//            }
//        }
//        return new LinkedList<>();
//    }






}
