package Utilites;


import Models.GraphNode;

import java.util.*;
import java.util.LinkedList;

public class Algorithms {


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

    public static List<List<GraphNode>> DFSAlgorithmAllPaths(GraphNode start, GraphNode target) {
        Stack<List<GraphNode>> stack = new Stack<>();
        Set<GraphNode> visited = new HashSet<>();
        List<List<GraphNode>> allPaths = new ArrayList<>();

        stack.push(new ArrayList<>(Collections.singletonList(start)));

        while (!stack.isEmpty()) {
            List<GraphNode> currentPath = stack.pop();
            GraphNode current = currentPath.get(currentPath.size() - 1);

            allPaths.add(new ArrayList<>(currentPath));

            if (current.equals(target)) {
                continue;
            }

            if (!visited.contains(current)) {
                visited.add(current);


                for (GraphNode neighbour : current.adjList) {
                    if (!visited.contains(neighbour)) {
                        List<GraphNode> newPath = new ArrayList<>(currentPath);
                        newPath.add(neighbour);
                        stack.push(newPath);
                    }
                }
            }
        }
        return allPaths;
    }
    public static List<GraphNode> DFSAlgorithm(GraphNode start, GraphNode target) {
        Stack<GraphNode> stack = new Stack<>(); // Use Deque as a stack
        Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
        Set<GraphNode> visited = new HashSet<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            GraphNode current = stack.pop();

            if (current.equals(target)) {
                LinkedList<GraphNode> path = new LinkedList<>();
                for (GraphNode at = target; at != null; at = cameFrom.get(at)) {
                    path.addFirst(at); // Efficiently add to the front
                }
                return path;
            }

            if (visited.add(current)) { // Only process nodes that haven't been visited
                for (GraphNode neighbour : current.adjList) {
                    if (!visited.contains(neighbour)) {
                        stack.push(neighbour);
                        cameFrom.put(neighbour, current); // Mark the path for reconstruction
                    }
                }
            }
        }
        return null; // Return null if no path is found
    }

    public static LinkedList<GraphNode> dijkstraAlgorithm(GraphNode startNode, GraphNode lookingFor) {
        Map<GraphNode, GraphNode> prevNode = new HashMap<>(); // Maps each node to its predecessor on the cheapest path
        Map<GraphNode, Double> distances = new HashMap<>(); // Maps each node to its distance from the start
        PriorityQueue<GraphNode> unencountered = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        // Initialize distances to all nodes as infinity, except for the start node
        distances.put(startNode, 0.0); // Distance from start to start is 0
        unencountered.add(startNode);

        while (!unencountered.isEmpty()) {
            GraphNode currentNode = unencountered.poll(); // Node with the smallest distance
            currentNode.listToHashMap();
            if (currentNode.equals(lookingFor)) { // Found the target node
                return buildPath(lookingFor, prevNode); // Assemble the path from start to the found node
            }

            // Iterate over the adjacency list (map) of the current node to update distances
            for (Map.Entry<GraphNode, Double> adjEntry : currentNode.adjacencies.entrySet()) {
                GraphNode adjNode = adjEntry.getKey();
                Double edgeCost = adjEntry.getValue();
                double newDist = distances.getOrDefault(currentNode, Double.MAX_VALUE) + edgeCost;

                if (newDist < distances.getOrDefault(adjNode, Double.MAX_VALUE)) {
                    distances.put(adjNode, newDist);
                    prevNode.put(adjNode, currentNode);
                    unencountered.add(adjNode);
                }
            }
        }

        return null; // No path found
    }
    private static LinkedList<GraphNode> buildPath(GraphNode target, Map<GraphNode, GraphNode> prevNode) {
        LinkedList<GraphNode> path = new LinkedList<>();
        for (GraphNode at = target; at != null; at = prevNode.get(at)) {
            path.addFirst(at);
        }
        return path;
    }
}






