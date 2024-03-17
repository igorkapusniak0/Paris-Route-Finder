package Models;
import java.util.ArrayList;

public class Pixel<T> {


    public T data;
    public ArrayList<Pixel<T>> adjList=new ArrayList<>();

    public int x,y;
    public void connectToNodeDirected(Pixel<T> destNode) {
        adjList.add(destNode);
    }
//    public void connectToNodeUndirected(Pixel<T> destNode) {
//        adjList.add(destNode);
//        destNode.adjList.add(this);

//    public String links(){
//        return adjList.toString();
//    }

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

//    public void connectToNodeDirected(Pixel<T> destNode) {
//        adjList.add(destNode);
//    }

    public void connectToNodeUndirected(Pixel<T> destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }

    public String links() {
        StringBuilder links = new StringBuilder("[");
        for (Pixel<T> pixel : adjList) {
            links.append(String.format(" (%d, %d)", pixel.x, pixel.y));
        }
        links.append(" ]");
        return links.toString();
    }


    public String getCoordinates() {
        return x + "," + y;
    }



}
