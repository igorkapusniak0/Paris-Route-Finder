package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;


public class GraphNode {
    public String name;
    public int x;
    public int distance=Integer.MAX_VALUE;
    public int y;
    public List<GraphNode> adjList=new ArrayList<>();
    public HashMap<GraphNode, Double> adjacencies = new HashMap<>();

    public void listToHashMap(){
        for (GraphNode node : adjList){
            double distance = calculateDistance(this,node);
            adjacencies.put(node,distance);
        }
    }

    private double calculateDistance(GraphNode start, GraphNode end){
        return Math.sqrt(Math.pow(start.getX()-end.getX(),2)+Math.pow(start.getY()-end.getY(),2));
    }

    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public GraphNode(String name, int x, int y) {
        setName(name);
        setX(x);
        setY(y);

    }
    /*public void connectToNodeDirected(GraphNode<T> destNode) {
        adjList.add(destNode);
    }*/
    public void connectToNodeUndirected(GraphNode destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }
    public List<GraphNode> getLinks(){
        return adjList;
    }

    @Override
    public String toString(){
        return getName();
    }


}