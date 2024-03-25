package Controllers;

import Models.GraphNode;
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

import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ParisRouteController {
    @FXML
    public ImageView imageView;
    @FXML
    public ImageView iconImageView;
    private Image parisMap;
    private int[] startPoint = new int[2];
    private int[] endPoint = new int[2];
    private Image blackAndWhiteImage = null;
    private Graph graph = new Graph();


    @FXML
    public ComboBox<String> algorithmsCombo;


    public void initialize() {
        parisMap = imageView.getImage();
        setAlgorithmsCombo();
        POILinks();
        //imageView.setImage(parisMap);
        blackAndWhiteImage = Utilities.convertToBlackAndWhite(parisMap);
        //imageView.setImage(blackAndWhiteImage);
        graph();
        //System.out.println(matrix());
        Utilities.graphConnections(blackAndWhiteImage, graph);
        //drawPath();
        test();
//null


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
        GraphNode[][] pixelGraph = new GraphNode[height][width];
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


    private void drawEdgeCircle(int[] coords){
        Circle circle = new Circle();
        circle.setFill(Color.RED);
        circle.setCenterX(coords[0]);
        circle.setCenterY(coords[1]);
        circle.setRadius(3);
        ((Pane) imageView.getParent()).getChildren().add(circle);
    }

    private int[] getCoordinates(){
        int[] coordinates = new int[2];
        iconImageView.setOnMouseClicked(mouseEvent -> {
            if (blackAndWhiteImage!=null){
                if (mouseEvent.getButton()== MouseButton.PRIMARY){
                    coordinates[0] = (int) mouseEvent.getX();
                    coordinates[1] = (int) mouseEvent.getY();
                    System.out.println(coordinates[0] + ", " + coordinates[1]);
                    Circle circle = new Circle();
                    circle.setFill(Color.RED);
                    circle.setCenterX(coordinates[0]+3);
                    circle.setCenterY(coordinates[1]+3);
                    circle.setRadius(3);
                    circle.setUserData("EdgeCircle");
                    ((Pane) imageView.getParent()).getChildren().add(circle);
                }
            }
        });
        return coordinates;
    }

    @FXML
    private void calculatePath(){
        GraphNode startPixel = graph.pixelGraph[startPoint[1]][startPoint[0]];
        GraphNode endPixel = graph.pixelGraph[endPoint[1]][endPoint[0]];
        System.out.println(startPixel + ", " + endPixel);
        List<GraphNode> path = null;
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

        for (GraphNode coords : path) {
            Circle circle = new Circle();
            circle.setFill(Color.GREEN);
            circle.setCenterX(coords.getX());
            circle.setCenterY(coords.getY());
            circle.setRadius(1);
            circle.setUserData("pathCircle");
            ((Pane) imageView.getParent()).getChildren().add(circle);
        }
    }
    @FXML
    public void clearMap(){
        Pane pane = (Pane) imageView.getParent();
        pane.getChildren().removeIf(node -> node instanceof Circle);
    }

    @FXML
    public void clearPath(){
        Pane pane = (Pane) imageView.getParent();
        pane.getChildren().removeIf(node -> "pathCircle".equals(node.getUserData()));
    }

    private ArrayList<GraphNode> getPOIs(){
        ArrayList<GraphNode> POIs = Utilities.readInDatabase();
        return POIs;
    }

    private ArrayList<GraphNode> POILinks(){
        ArrayList<GraphNode> poiArrayList = Utilities.poiLinks(getPOIs());
        return poiArrayList;
    }
    private void test(){
        ArrayList<GraphNode> list = POILinks();
        for (GraphNode poi : list){
            System.out.println(poi.getName());
            List<GraphNode> linkedPOIs = poi.getLinks();
            Pane pane = (Pane) imageView.getParent();
            int radius = 4;
            Circle circle = new Circle(poi.getX()+radius,poi.getY()+radius,radius,Color.GREEN);
            pane.getChildren().add(circle);

            for (GraphNode graphNode: linkedPOIs) {
                GraphNode linkedPOI = graphNode;
                Line line = new Line(linkedPOI.getX()+radius,linkedPOI.getY()+radius,poi.getX()+radius,poi.getY()+radius);
                line.setFill(Color.RED);
                pane.getChildren().add(line);
            }
        }
    }






}









