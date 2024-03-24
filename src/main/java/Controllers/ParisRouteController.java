package Controllers;

import Models.POI;
import Models.Pixel;
import Utilites.Algorithms;
import Utilites.Graph;
import Utilites.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseButton;

import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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
    private ArrayList<POI> POIArray = null;


    @FXML
    public ComboBox<String> algorithmsCombo;

    @FXML
    public ComboBox<POI> startPointCombo;
    @FXML
    public ComboBox<POI> endPointCombo;


    public void initialize() {
        parisMap = imageView.getImage();
        setAlgorithmsCombo();
        //imageView.setImage(parisMap);
        blackAndWhiteImage = Utilities.convertToBlackAndWhite(parisMap);
        //imageView.setImage(blackAndWhiteImage);
        graph();
        //System.out.println(matrix());
        Utilities.graphConnections(blackAndWhiteImage, graph);
        //drawPath();
        //test();
        setPOIS();
        setPOICombo();
    }
    private void setPOIS(){
         POIArray =  Utilities.poiLinks(Utilities.readInDatabase());
    }
    private void setAlgorithmsCombo(){
        algorithmsCombo.getItems().addAll(
                "Depth First Search",
                "Breadth First Search",
                "Dijkstra's Algorithm");
    }

    private void setPOICombo(){
        startPointCombo.getItems().addAll(POIArray);
        endPointCombo.getItems().addAll(POIArray);
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
        Pixel startPixel = graph.pixelGraph[startPoint[1]][startPoint[0]];
        Pixel endPixel = graph.pixelGraph[endPoint[1]][endPoint[0]];
        POI startPOI = startPointCombo.getValue();
        String selectedAlgorithm = algorithmsCombo.getValue();
        POI endPOI = endPointCombo.getValue();
        if (startPOI!=null && endPOI!=null){
            List<POI> pathPOI = null;
            if ("Depth First Search".equals(selectedAlgorithm)){
                pathPOI = Algorithms.DFSAlgorithm2(startPOI,endPOI);
            }
            if ("Breadth First Search".equals(selectedAlgorithm)){
                pathPOI = Algorithms.BFSAlgorithm2(startPOI,endPOI);

            }
            if ("Dijkstra's Algorithm".equals(selectedAlgorithm)){
                pathPOI = Algorithms.BFSAlgorithm2(startPOI,endPOI);
            }
            for (int i =0;i<pathPOI.size()-1;i++) {
                POI current = pathPOI.get(i);
                POI ahead = pathPOI.get(i+1);
                Line line = new Line(current.getX(),current.getY(),ahead.getX(),ahead.getY());
                line.setUserData("pathLine");
                ((Pane) imageView.getParent()).getChildren().add(line);
            }
        }
        if (startPixel!=null && endPixel!=null){
            System.out.println(startPixel + ", " + endPixel);
            List<int[]> path = null;

            if ("Depth First Search".equals(selectedAlgorithm)){
                path = Algorithms.DFSAlgorithm(startPixel,endPixel);
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
                circle.setCenterX(coords[0]+1);
                circle.setCenterY(coords[1]+1);
                circle.setRadius(1);
                circle.setUserData("pathCircle");
                ((Pane) imageView.getParent()).getChildren().add(circle);
            }
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




    private void test(){
        ArrayList<POI> list = POIArray;
        for (POI poi : list){
            System.out.println(poi.getName());
            HashMap<Double, POI> linkedPOIs = poi.getPOIs();
            Pane pane = (Pane) imageView.getParent();
            int radius = 4;
            Circle circle = new Circle(poi.getX()+radius,poi.getY()+radius,radius,Color.GREEN);
            pane.getChildren().add(circle);

            for (Map.Entry<Double, POI> entry : linkedPOIs.entrySet()) {
                POI linkedPOI = entry.getValue();
                System.out.println(" POI: " + linkedPOI.getName() + " Distance: " + entry.getKey());
                Line line = new Line(linkedPOI.getX()+radius,linkedPOI.getY()+radius,poi.getX()+radius,poi.getY()+radius);
                line.setFill(Color.RED);
                pane.getChildren().add(line);
            }
        }
    }







}









