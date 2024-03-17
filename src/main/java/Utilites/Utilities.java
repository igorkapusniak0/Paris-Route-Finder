package Utilites;

import Models.Pixel;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.image.*;

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
                if (color.equals(Color.WHITE)) {
                    pixelWriter.setColor(x, y, Color.WHITE);
                } else {
                    pixelWriter.setColor(x, y, Color.BLACK);
                }
            }
        }

        return writableImage;
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
                    if (x % width < width - 1 && graph.pixelGraph[y][right]!=null ) {
                        graph.pixelGraph[y][x].connectToNodeUndirected(graph.pixelGraph[y][right]);
                    }
                    if (down < height && graph.pixelGraph[down][x]!=null) {
                        graph.pixelGraph[y][x].connectToNodeUndirected(graph.pixelGraph[down][x]);
                    }
                }
            }
        }

    }





}
