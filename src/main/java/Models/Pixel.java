package Models;
import java.util.ArrayList;

public class Pixel {

    public int[] coordinates;

    public ArrayList<Pixel> adjList = new ArrayList<>();


    public Pixel(int[] coordinates) {
        setCoordinates(coordinates);
    }


//null
    public void connectToNodeUndirected(Pixel destNode) {
        adjList.add(destNode);
        destNode.adjList.add(this);
    }

    public String links() {
        StringBuilder links = new StringBuilder("[");
        for (Pixel pixel : adjList) {
            links.append(String.format(" (%d, %d)", coordinates[0], coordinates[1]));
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
