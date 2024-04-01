package Utilites;


import Models.GraphNode;

import java.util.*;
import java.util.LinkedList;

public class Algorithms {


    public static List<GraphNode> BFSAlgorithm(GraphNode startPixel, GraphNode endPixel, Set<GraphNode> avoidSet) {
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
                    System.out.println(at);
                    path.add(0, at);
                }
                System.out.println(cameFrom);
                return path;
            }
            for (GraphNode neighbour : current.adjList) {
                if (!visited.contains(neighbour) && !avoidSet.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                    cameFrom.put(neighbour, current);
                }
            }
        }
        return new LinkedList<>();
    }

    public static List<GraphNode> BFSWithPOIs(GraphNode start,GraphNode end,List<GraphNode> goSet, Set<GraphNode> avoidSet){
        List<GraphNode> path = new LinkedList<>();
        goSet.add(0,start);
        goSet.add(goSet.size(),end);
        for (int i = 0; i<goSet.size()-1;i++){
            path.addAll(BFSAlgorithm(goSet.get(i),goSet.get(i+1),avoidSet));
        }
        return path;
    }

    public static List<List<GraphNode>> DFSAlgorithmAllPaths(GraphNode start, GraphNode end, int maxDepth, Set<GraphNode> avoidSet) {
        Stack<List<GraphNode>> stack = new Stack<>();
        List<List<GraphNode>> allPaths = new ArrayList<>();

        stack.push(Arrays.asList(start)); // Start path with the start node

        while (!stack.isEmpty()) {
            List<GraphNode> currentPath = stack.pop();
            if (currentPath.size() > maxDepth) continue; // Skip paths that exceed maxDepth
            GraphNode current = currentPath.get(currentPath.size() - 1);

            if (current.equals(end)) {
                allPaths.add(new ArrayList<>(currentPath));
                continue;
            }

            for (GraphNode neighbour : current.adjList) {
                if (!currentPath.contains(neighbour) && !avoidSet.contains(neighbour)) {
                    List<GraphNode> newPath = new ArrayList<>(currentPath);
                    newPath.add(neighbour);
                    stack.push(newPath);
                }
            }
        }
        return allPaths;
    }


    public static List<GraphNode> DFSAlgorithm(GraphNode start, GraphNode target, Set<GraphNode> avoidSet) {
        Stack<GraphNode> stack = new Stack<>();
        Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
        Set<GraphNode> visited = new HashSet<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            GraphNode current = stack.pop();

            if (current.equals(target)) {
                LinkedList<GraphNode> path = new LinkedList<>();
                for (GraphNode at = target; at != null; at = cameFrom.get(at)) {
                    path.addFirst(at);// Efficiently add to the front
                }
                return path;
            }

            if (visited.add(current)) { // Only process nodes that haven't been visited
                for (GraphNode neighbour : current.adjList) {
                    if (!visited.contains(neighbour) && !avoidSet.contains(neighbour)) {
                        stack.push(neighbour);
                        cameFrom.put(neighbour, current);
                    }
                }
            }
        }
        return null;
    }
    public static List<GraphNode> DFSPOIs(GraphNode start,GraphNode end,List<GraphNode> goSet, Set<GraphNode> avoidSet){
        List<GraphNode> path = new LinkedList<>();
        goSet.add(0,start);
        goSet.add(goSet.size(),end);
        for (int i = 0; i<goSet.size()-1;i++){
            path.addAll(DFSAlgorithm(goSet.get(i),goSet.get(i+1),avoidSet));
        }
        return path;
    }
    public static List<List<GraphNode>> DFSAlgorithmAllPathsForGoSet(GraphNode start, GraphNode end, int maxDepth, Set<GraphNode> avoidSet, List<GraphNode> goSet) {
        List<List<GraphNode>> allPaths = new ArrayList<>();
        if (goSet == null || goSet.isEmpty()) {
            // If goSet is empty, find paths from start to end directly
            return DFSAlgorithmAllPaths(start, end, maxDepth, avoidSet);
        }

        // Ensure the start and end nodes are correctly positioned in goSet
        if (!goSet.get(0).equals(start)) {
            goSet.add(0, start);
        }
        if (!goSet.get(goSet.size() - 1).equals(end)) {
            goSet.add(end);
        }

        // Find paths for each consecutive pair in goSet
        for (int i = 0; i < goSet.size() - 1; i++) {
            GraphNode currentStart = goSet.get(i);
            GraphNode currentEnd = goSet.get(i + 1);
            List<List<GraphNode>> pathsBetweenCurrentNodes = DFSAlgorithmAllPaths(currentStart, currentEnd, maxDepth, avoidSet);

            if (pathsBetweenCurrentNodes.isEmpty()) {
                // If no path exists between any pair, return an empty list indicating failure
                return new ArrayList<>();
            }

            if (allPaths.isEmpty()) {
                // For the first pair, initialize allPaths with found paths
                allPaths.addAll(pathsBetweenCurrentNodes);
            } else {
                // Combine existing paths with new paths, respecting the order
                List<List<GraphNode>> newAllPaths = new ArrayList<>();
                for (List<GraphNode> existingPath : allPaths) {
                    for (List<GraphNode> newPath : pathsBetweenCurrentNodes) {
                        List<GraphNode> combinedPath = new ArrayList<>(existingPath);
                        combinedPath.addAll(newPath.subList(1, newPath.size())); // Avoid duplicating the connecting node
                        newAllPaths.add(combinedPath);
                    }
                }
                allPaths = newAllPaths; // Update allPaths with combined paths
            }
        }

        return allPaths;
    }

    public static LinkedList<GraphNode> dijkstraAlgorithm(GraphNode startNode, GraphNode lookingFor, Set<GraphNode> avoidSet) {
        Map<GraphNode, GraphNode> prevNode = new HashMap<>(); // Maps each node to its predecessor on the cheapest path
        Map<GraphNode, Double> distances = new HashMap<>(); // Maps each node to its distance from the start
        PriorityQueue<GraphNode> unencountered = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        // Initialise distances to all nodes as infinity, except for the start node
        distances.put(startNode, 0.0); // Distance from start to start is 0
        unencountered.add(startNode);

        while (!unencountered.isEmpty()) {
            GraphNode currentNode = unencountered.poll(); // Node with the smallest distance
            currentNode.listToHashMap();
            if (currentNode.equals(lookingFor)) { // Found the target node
                LinkedList<GraphNode> path = new LinkedList<>();
                for (GraphNode at = lookingFor; at != null; at = prevNode.get(at)) {
                    path.addFirst(at);
                }
                return path;
            }

            // Iterate over the adjacency list (map) of the current node to update distances
            for (Map.Entry<GraphNode, Double> adjEntry : currentNode.adjacencies.entrySet()) {
                GraphNode adjNode = adjEntry.getKey();
                Double edgeCost = adjEntry.getValue();
                double newDist = distances.getOrDefault(currentNode, Double.MAX_VALUE) + edgeCost;

                if (newDist < distances.getOrDefault(adjNode, Double.MAX_VALUE)  && !avoidSet.contains(adjNode)) {
                    distances.put(adjNode, newDist);
                    prevNode.put(adjNode, currentNode);
                    unencountered.add(adjNode);
                }
            }
        }

        return null; // No path found
    }
    public static List<GraphNode> dijkstraWithPOIs(GraphNode start,GraphNode end,List<GraphNode> goSet, Set<GraphNode> avoidSet){
        List<GraphNode> path = new LinkedList<>();
        goSet.add(0,start);
        goSet.add(goSet.size(),end);
        for (int i = 0; i<goSet.size()-1;i++){
            path.addAll(dijkstraAlgorithm(goSet.get(i),goSet.get(i+1),avoidSet));
        }
        return path;
    }

}
