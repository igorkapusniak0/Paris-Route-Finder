package Utilites;


import Models.GraphNode;

import java.util.*;
import java.util.LinkedList;

public class Algorithms {


    public static List<GraphNode> BFSAlgorithm(GraphNode start, GraphNode end, Set<GraphNode> avoidSet) {
        Queue<GraphNode> queue = new LinkedList<>();
        Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
        Set<GraphNode> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);
        cameFrom.put(start, null);

        while (!queue.isEmpty()) {
            GraphNode current = queue.remove();

            if (current.equals(end)) {
                List<GraphNode> path = new LinkedList<>();
                for (GraphNode at = end; at != null; at = cameFrom.get(at)) {
                    //System.out.println(at);
                    path.add(0, at);
                }
                //System.out.println(cameFrom);
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

        stack.push(Arrays.asList(start));

        while (!stack.isEmpty()) {
            List<GraphNode> currentPath = stack.pop();
            if (currentPath.size() > maxDepth) continue;
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


    public static List<GraphNode> DFSAlgorithm(GraphNode start, GraphNode end, Set<GraphNode> avoidSet) {
        Stack<GraphNode> stack = new Stack<>();
        Map<GraphNode, GraphNode> cameFrom = new HashMap<>();
        Set<GraphNode> visited = new HashSet<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            GraphNode current = stack.pop();

            if (current.equals(end)) {
                LinkedList<GraphNode> path = new LinkedList<>();
                for (GraphNode at = end; at != null; at = cameFrom.get(at)) {
                    path.addFirst(at);
                }
                return path;
            }

            if (visited.add(current)) {
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
    public static List<List<GraphNode>> DFSAlgorithmAllPathsWithPOIs(GraphNode start, GraphNode end, int maxDepth, Set<GraphNode> avoidSet, List<GraphNode> goSet) {
        List<List<GraphNode>> allPaths = new ArrayList<>();
        if (goSet == null || goSet.isEmpty()) {
            return DFSAlgorithmAllPaths(start, end, maxDepth, avoidSet);
        }

        if (!goSet.get(0).equals(start)) {
            goSet.add(0, start);
        }
        if (!goSet.get(goSet.size() - 1).equals(end)) {
            goSet.add(end);
        }

        for (int i = 0; i < goSet.size() - 1; i++) {
            GraphNode currentStart = goSet.get(i);
            GraphNode currentEnd = goSet.get(i + 1);
            List<List<GraphNode>> pathsBetweenCurrentNodes = DFSAlgorithmAllPaths(currentStart, currentEnd, maxDepth, avoidSet);

            if (pathsBetweenCurrentNodes.isEmpty()) {
                return new ArrayList<>();
            }

            if (allPaths.isEmpty()) {
                allPaths.addAll(pathsBetweenCurrentNodes);
            } else {
                List<List<GraphNode>> newAllPaths = new ArrayList<>();
                for (List<GraphNode> existingPath : allPaths) {
                    for (List<GraphNode> newPath : pathsBetweenCurrentNodes) {
                        List<GraphNode> combinedPath = new ArrayList<>(existingPath);
                        combinedPath.addAll(newPath.subList(1, newPath.size()));
                        newAllPaths.add(combinedPath);
                    }
                }
                allPaths = newAllPaths;
            }
        }

        return allPaths;
    }

    public static LinkedList<GraphNode> dijkstraAlgorithm(GraphNode start, GraphNode end, Set<GraphNode> avoidSet) {
        Map<GraphNode, GraphNode> prevNode = new HashMap<>();
        Map<GraphNode, Double> distances = new HashMap<>();
        PriorityQueue<GraphNode> unencountered = new PriorityQueue<>(Comparator.comparingDouble(distances::get));


        distances.put(start, 0.0);
        unencountered.add(start);

        while (!unencountered.isEmpty()) {
            GraphNode currentNode = unencountered.poll();
            currentNode.listToHashMap();
            if (currentNode.equals(end)) {
                LinkedList<GraphNode> path = new LinkedList<>();
                for (GraphNode at = end; at != null; at = prevNode.get(at)) {
                    path.addFirst(at);
                }
                return path;
            }

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

        return null;
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
