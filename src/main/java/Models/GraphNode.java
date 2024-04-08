package Models;

import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Objects;
import javafx.scene.image.Image;


public class GraphNode {
    public String name;
    public int x;
    public int distance=Integer.MAX_VALUE;
    public int y;
    public Image icon;
    public int stars;
    private double rating;
    public List<GraphNode> adjList=new ArrayList<>();
    public HashMap<GraphNode, Double> adjacencies = new HashMap<>();

    public void listToHashMapShortest(){
        for (GraphNode node : adjList){
            double distance = calculateDistance(this,node);
            adjacencies.put(node,distance);
        }
    }
    public void listToHashMapHistorical() {
        for (GraphNode node : adjList) {
            adjacencies.put(node, rating);
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

    public GraphNode(String name, int x, int y, Image icon, int stars) {
        setName(name);
        setX(x);
        setY(y);
        setIcon(icon);
        setStars(stars);
        setRating();
    }

    public void setStars(int stars){
        this.stars=stars;
    }
    public int getStars(){
        return stars;
    }
    public double getRating(){
        return getRating();
    }
    public void setRating(){
        double temp = (fromCentre()*100) / (315);
        this.rating = stars * temp;
    }
    private double fromCentre(){
        int xdiff = Math.abs(315-x);
        int ydiff = Math.abs(315-y);

        return Math.sqrt(Math.pow(xdiff,2)+Math.pow(ydiff,2));
    }

    public void setIcon(Image icon){
        this.icon = icon;
    }
    public Image getIcon(){
        return icon;
    }
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