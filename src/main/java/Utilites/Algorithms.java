package Utilites;


import Models.POI;
import Models.Pixel;

import java.util.*;
import java.util.LinkedList;

public class Algorithms {

    public static List<int[]> DFSAlgorithm(Pixel start, Pixel target) {
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
    public static List<POI> BFSAlgorithm2(POI startNode, POI endNode) {
        Queue<POI> queue = new LinkedList<>();
        Map<POI, POI> cameFrom = new HashMap<>();
        Set<POI> visited = new HashSet<>();

        queue.add(startNode);
        visited.add(startNode);
        cameFrom.put(startNode, null);

        while (!queue.isEmpty()) {
            POI current = queue.remove();
            if (current.equals(endNode)) {
                List<POI> path = new LinkedList<>();
                for (POI at = endNode; at != null; at = cameFrom.get(at)) {
                    path.add(0, at);
                }
                return path;
            }

            for (Map.Entry<Double, POI> neighbour : current.getPOIs().entrySet()) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour.getValue());
                    queue.add(neighbour.getValue());
                    cameFrom.put(neighbour.getValue(), current);
                }
            }
        }
        return new LinkedList<>();
    }
    public static List<POI> DFSAlgorithm2(POI start, POI target) {
        Stack<POI> stack = new Stack<>();
        Map<POI, POI> cameFrom = new HashMap<>(); // To reconstruct the path later
        Set<POI> visited = new HashSet<>();

        // Start by pushing the start node onto the stack
        stack.push(start);

        while (!stack.isEmpty()) {
            POI current = stack.pop();

            // If the target is found, reconstruct the path from start to target
            if (current.equals(target)) {
                List<POI> path = new ArrayList<>();
                for (POI at = target; at != null; at = cameFrom.get(at)) {
                    path.add(at);
                }
                return path;
            }

            // If current node has not been visited, visit it
            if (!visited.contains(current)) {
                visited.add(current);

                // Explore neighbours
                for (Map.Entry<Double, POI> neighbour : current.getPOIs().entrySet()) {
                    if (!visited.contains(neighbour) && !stack.contains(neighbour)) {
                        stack.push(neighbour.getValue());
                        cameFrom.put(neighbour.getValue(), current); // Remember where we came from
                    }
                }
            }
        }
        return null; // Return null if no path is found
    }






}
