package Utilites;

import Models.GraphNode;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.ArrayList;

import java.io.*;

public class API {

    public static Image convertToBlackAndWhite(Image image) {
        if (image == null) return null;

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = pixelReader.getColor(x, y);
                if (matchesColour(color,Color.WHITE,.04)) {
                    pixelWriter.setColor(x, y, Color.WHITE);
                } else {
                    pixelWriter.setColor(x, y, Color.BLACK);
                }
            }
        }

        return writableImage;
    }
    public static boolean matchesColour(Color color, Color targetColour, double tolerance) {
        boolean redDifference = Math.abs(targetColour.getRed() - color.getRed()) <= tolerance;
        boolean greenDifference = Math.abs(targetColour.getGreen() - color.getGreen()) <= tolerance;
        boolean blueDifference = Math.abs(targetColour.getBlue() - color.getBlue()) <= tolerance;

        return (redDifference && greenDifference && blueDifference);
    }

    public static void convertToPixels(Image image, Graph graph){
        int height = (int) image.getHeight();
        int width = (int) image.getWidth();
        PixelReader pixelReader = image.getPixelReader();

        for (int y = 0; y<width;y++){
            for (int x = 0; x<height;x++){
                if(pixelReader.getColor(x,y).equals(Color.WHITE)){
                    graph.pixelGraph[y][x] = new GraphNode(null,x,y,null);
                }else{
                    graph.pixelGraph[y][x] = null;
                }
            }
        }
    }

    public static void graphConnections(Image image, Graph graph){
        int height = (int) image.getHeight();
        int width = (int) image.getWidth();

        for (int y = 0; y<width;y++){
            for (int x = 0; x<height;x++){
                if(graph.pixelGraph[y][x]!=null){
                    int down = y + 1;
                    int right = x + 1;

                    if (down < height && graph.pixelGraph[down][x]!=null) {
                        graph.pixelGraph[y][x].connectToNodeUndirected(graph.pixelGraph[down][x]);
                    }
                    if (right < width && graph.pixelGraph[y][right]!=null ) {
                        graph.pixelGraph[y][x].connectToNodeUndirected(graph.pixelGraph[y][right]);
                    }
                }
            }
        }
    }

    public static ArrayList<GraphNode> readInDatabase(){
        String line = "";
        ArrayList<GraphNode> POIs = new ArrayList<>();
        try {
            File database = new File("src/main/resources/Data/POI.csv");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(database));
            while ((line = bufferedReader.readLine())!=null){
                String[] values = line.split(",");
                Image iconImage;
                try {
                    iconImage = new Image(new FileInputStream(values[3].trim()));
                } catch (IOException e) {
                    e.printStackTrace();
                    iconImage = null;
                }
                GraphNode poi = new GraphNode(values[0].trim(),Integer.parseInt(values[1].trim()),Integer.parseInt(values[2].trim()),iconImage);
                POIs.add(poi);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return POIs;
    }

    public static ArrayList<GraphNode> poiLinks(ArrayList<GraphNode> POIs){
        ArrayList<GraphNode> arrayList = new ArrayList<>();
        for(GraphNode poi : POIs){
            for (GraphNode poi2 : POIs) {
                if (poi!=poi2){
                    int x1 = poi.getX();
                    int y1 = poi.getY();
                    int x2 = poi2.getX();
                    int y2 = poi2.getY();
                    int xDiff = Math.abs(x2-x1);
                    int yDiff = Math.abs(y2-y1);
                    double distance = Math.sqrt((Math.pow(xDiff,2))+(Math.pow(yDiff,2)));
                    if (poi2.getLinks()!=null && !poi2.getLinks().contains(poi) && !poi.getLinks().contains(poi2)){
                        if (distance<=50

                        ){
                            poi.connectToNodeUndirected(poi2);
                        } else if (poi.getLinks().size()<=4 && distance<=200 && poi2.getLinks().size()<=4  && !poi2.getLinks().contains(poi) && !poi.getLinks().contains(poi2)) {
                            poi.connectToNodeUndirected(poi2);
                        }
                    }
                }
            }
            arrayList.add(poi);
        }
        return arrayList;
    }
    public static ArrayList<GraphNode> getPOIs(){
        return poiLinks(readInDatabase());
    }
}
