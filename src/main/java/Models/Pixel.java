package Models;
import java.util.ArrayList;

public class Pixel<T> {


    public T data;
    public ArrayList<Pixel<T>> adjList=new ArrayList<>();
    public Pixel() {
    }
    public void connectToNodeDirected(Pixel<T> destNode) {
        adjList.add(destNode);
    }
    public void connectToNodeUndirected(Pixel<T> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }




}
