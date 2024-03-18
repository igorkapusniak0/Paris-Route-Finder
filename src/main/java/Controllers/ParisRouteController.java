package Controllers;

import Models.POI;
import Models.Pixel;
import Utilites.Algorithms;
import Utilites.Graph;
import Utilites.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

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
    private int[] startPoint = new int[2];
    private int[] endPoint = new int[2];

    private Image blackAndWhiteImage = null;
    private Graph graph = new Graph();


    @FXML
    public ComboBox<String> algorithmsCombo;


    public void initialize() {
        setAlgorithmsCombo();
        imageView.setImage(parisMap);
        blackAndWhiteImage = Utilities.convertToBlackAndWhite(parisMap);
        imageView.setImage(blackAndWhiteImage);
        graph();
        //System.out.println(matrix());
        Utilities.graphConnections(blackAndWhiteImage, graph);
        //drawPath();



    }
    private void setAlgorithmsCombo(){
        algorithmsCombo.getItems().addAll(
                "Depth First Search",
                "Breadth First Search",
                "Dijkstra's Algorithm");
    }

    @FXML
    public void setStartPoint(){
        startPoint = getCoordinates();

    }
    @FXML
    public void setEndPoint(){
        endPoint = getCoordinates();
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
                    Circle circle = new Circle();
                    circle.setFill(Color.RED);
                    circle.setCenterX(coordinates[0]);
                    circle.setCenterY(coordinates[1]);
                    circle.setRadius(3);
                    ((Pane) imageView.getParent()).getChildren().add(circle);
                }
            }
        });
        return coordinates;
    }


    @FXML
    private void calculatePath(){
        Pixel startPixel = graph.pixelGraph[startPoint[1]][startPoint[0]];
        Pixel endPixel = graph.pixelGraph[endPoint[1]][endPoint[0]];
        System.out.println(startPixel + ", " + endPixel);
        List<int[]> path = null;
        String selectedAlgorithm = algorithmsCombo.getValue();
        if ("Depth First Search".equals(selectedAlgorithm)){
            path = Algorithms.findPathDepthFirst(startPixel,endPixel);
        }
        if ("Breadth First Search".equals(selectedAlgorithm)){
            path = Algorithms.BFSAlgorithm(startPixel,endPixel);

        }
        if ("Dijkstra's Algorithm".equals(selectedAlgorithm)){
            path = Algorithms.BFSAlgorithm(startPixel,endPixel);

        }

        for (int[] coords : path) {
            Circle circle = new Circle();
            circle.setFill(Color.GREEN);
            circle.setCenterX(coords[0]);
            circle.setCenterY(coords[1]);
            circle.setRadius(1);
            ((Pane) imageView.getParent()).getChildren().add(circle);
        }
    }



    private void drawPath() {
        imageView.setOnMouseClicked(mouseEvent -> {
            if (blackAndWhiteImage != null) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    // Calculate the coordinates relative to the imageView's content
                    double x = mouseEvent.getX();
                    double y = mouseEvent.getY();

                    // Assuming imageView is directly added to the Pane and fills it completely
                    System.out.println(x + ", " + y);

                    // Example for obtaining a Pixel object and starting DFS based on mouse click
                    Pixel clickedPixel = graph.pixelGraph[(int) y][(int) x];
                    List<int[]> path = Algorithms.BFSAlgorithm(clickedPixel,graph.pixelGraph[554][55]);

                    for (int[] coords : path) {
                        Circle circle = new Circle();
                        circle.setFill(Color.GREEN);
                        circle.setCenterX(coords[0]);
                        circle.setCenterY(coords[1]);
                        circle.setRadius(1);

                        // Now, add the circle to the Pane which is the parent of imageView
                        ((Pane) imageView.getParent()).getChildren().add(circle);
                    }
                }
            }
        });
    }

    @FXML
    public void clearMap(){
        Pane pane = (Pane) imageView.getParent();
        pane.getChildren().removeIf(node -> node instanceof Circle);
    }






}









