package Controllers;

import Models.Pixel;
import Utilites.Algorithms;
import Utilites.Graph;
import Utilites.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ParisRouteController {
    FileInputStream input;

    {
        try {
            input = new FileInputStream("src/main/resources/Image/Paris.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private final Image parisMap = new Image(input);
    @FXML
    public ImageView imageView;

    private Image blackAndWhiteImage = null;
    private Graph graph = new Graph();


    public void initialize() {
        imageView.setImage(parisMap);
        blackAndWhiteImage = Utilities.convertToBlackAndWhite(parisMap);
        imageView.setImage(blackAndWhiteImage);
        graph();
        //System.out.println(matrix());
        Utilities.graphConnections(blackAndWhiteImage, graph);
        getCoordinates();
        //showlinks();
        //test();
    }

    private void graph() {
        int height = (int) blackAndWhiteImage.getHeight();
        int width = (int) blackAndWhiteImage.getWidth();
        Pixel[][] pixelGraph = new Pixel[height][width];
        graph.setPixelGraph(pixelGraph);
        Utilities.convertToPixels(blackAndWhiteImage, graph);
    }

    private String matrix() {
        String string = "";
        for (int i = 0; i < (int) blackAndWhiteImage.getWidth(); i++) {
            for (int ii = 0; ii < (int) blackAndWhiteImage.getHeight(); ii++) {
                string += graph.pixelGraph[i][ii] + ", ";
            }
            string += "\n";
        }
        return string;
    }

    public void showlinks() {
        for (int i = 0; i < (int) blackAndWhiteImage.getWidth(); i++) {
            for (int ii = 0; ii < (int) blackAndWhiteImage.getHeight(); ii++) {
                if (graph.pixelGraph[i][ii] instanceof Pixel) {
                    System.out.println(graph.pixelGraph[i][ii].links());
                }
            }
        }
    }

    /*public void test(){
        for (int y = 0; y<blackAndWhiteImage.getHeight();y++){
            for (int x = 0; x<blackAndWhiteImage.getWidth();x++){
                Algorithms.breadthFirstSearch(graph,x,y,imageView);
            }
        }
    }*/

    private int[] getCoordinates(){
        int[] coordinates = new int[2];
        imageView.setOnMouseClicked(mouseEvent -> {
            if (blackAndWhiteImage!=null){
                if (mouseEvent.getButton()== MouseButton.PRIMARY){
                    coordinates[0] = (int) mouseEvent.getX();
                    coordinates[1] = (int) mouseEvent.getY();
                    System.out.println(coordinates[0] + ", " + coordinates[1]);
                    System.out.println(graph.pixelGraph[coordinates[1]][coordinates[0]]);
                    List<int[]> list = Algorithms.DFSAlgorithm(graph.pixelGraph[coordinates[1]][coordinates[0]]);
                    for (int[] list1: list){
                        System.out.println(list1[0] + ", " + list1[1]);
                    }
                }
            }
        });
        return coordinates;
    }


}









