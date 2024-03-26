package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GraphNode {
    public String name;
    public int x;
    public int y;
    public List<GraphNode> adjList=new ArrayList<>();

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