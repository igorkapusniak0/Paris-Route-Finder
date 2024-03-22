package Utilites;

import Models.POI;
import Models.Pixel;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import java.util.ArrayList;
import java.math.*;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class Utilities {

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
    private static boolean matchesColour(Color color, Color targetColour, double tolerance) {
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
                    graph.pixelGraph[y][x] = new Pixel(new int[]{x,y});
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

    public static ArrayList<POI> readInDatabase(){
        String line = "";
        ArrayList<POI> POIs = new ArrayList<>();
        try {
            File database = new File("src/main/resources/Data/POI.csv");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(database));
            while ((line = bufferedReader.readLine())!=null){
                String[] values = line.split(",");
                POI poi = new POI(values[0].trim(),Integer.parseInt(values[1].trim()),Integer.parseInt(values[2].trim()));
                POIs.add(poi);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return POIs;
    }

    public static ArrayList<POI> poiLinks(ArrayList<POI> POIs){
        ArrayList<POI> arrayList = new ArrayList<>();
        for(POI poi : POIs){
            for (POI poi2 : POIs) {
                if (poi!=poi2){
                    int x1 = poi.getX();
                    int y1 = poi.getY();
                    int x2 = poi2.getX();
                    int y2 = poi2.getY();
                    int xDiff = Math.abs(x2-x1);
                    int yDiff = Math.abs(y2-y1);
                    double distance = Math.sqrt((Math.pow(xDiff,2))+(Math.pow(yDiff,2)));
                    if (poi2.getPOIs()!=null ){
                        if (distance<=50){
                            poi.getPOIs().put(distance, poi2);
                            poi2.getPOIs().put(distance,poi);
                        } else if (poi.getPOIs().size()<=3 && distance<=200 && poi2.getPOIs().size()<=3) {
                            poi.getPOIs().put(distance, poi2);
                            poi2.getPOIs().put(distance,poi);
                        }
                    }
                }
            }
            arrayList.add(poi);
        }
        return arrayList;
    }
}
