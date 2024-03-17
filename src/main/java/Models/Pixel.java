package Models;
import java.util.ArrayList;

public class Pixel {

    public int[] coordinates;

    public ArrayList<Pixel> adjList = new ArrayList<>();

    public int x,y;
    public void connectToNodeDirected(Pixel destNode) {
        adjList.add(destNode);
    }
//    public void connectToNodeUndirected(Pixel<T> destNode) {
//        adjList.add(destNode);
//        destNode.adjList.add(this);

//    public String links(){
//        return adjList.toString();
//    }

    public Pixel(int[] coordinates) {
        setCoordinates(coordinates);
    }

//    public void connectToNodeDirected(Pixel<T> destNode) {
//        adjList.add(destNode);
//    }

    public void connectToNodeUndirected(Pixel destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }

    public String links() {
        StringBuilder links = new StringBuilder("[");
        for (Pixel pixel : adjList) {
            links.append(String.format(" (%d, %d)", pixel.x, pixel.y));
        }
        links.append(" ]");
        return links.toString();
    }
    public void setCoordinates(int[] coordinates){
        this.coordinates = coordinates;
    }


    public int[] getCoordinates(){
        return coordinates;
    }

    @Override
    public String toString() {
        return "x = " + coordinates[0] + ", y = " + coordinates[1];
    }



}
