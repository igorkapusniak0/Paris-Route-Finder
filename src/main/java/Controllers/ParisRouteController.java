package Controllers;

import Models.GraphNode;
import Utilites.Algorithms;
import Utilites.Graph;
import Utilites.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    @FXML
    public ComboBox<String> algorithmsCombo;
    @FXML
    public ComboBox<GraphNode> startCombo;
    @FXML
    public ComboBox<GraphNode> endCombo;


    @FXML
    ComboBox<GraphNode> waypointCombo;
    @FXML
    TextField waypointTextField;
    @FXML
    ListView<GraphNode> waypointListView;

    private ArrayList<GraphNode> getPOIs = null;
    private ArrayList<GraphNode> getPOIsLinked = null;

    public void initialize() {
        parisMap = imageView.getImage();
        setAlgorithmsCombo();
        POIs();
        POILinks();
        blackAndWhiteImage = Utilities.convertToBlackAndWhite(parisMap);
        //imageView.setImage(blackAndWhiteImage);
        graph();
        //System.out.println(matrix());
        Utilities.graphConnections(blackAndWhiteImage, graph);
        startAndEndCombo();
        //drawPath();
        //test();


    }

    @FXML
    public void addWaypoint(){
        if (getCoordinates()!=null){
            int x = getCoordinates()[0];
            int y = getCoordinates()[1];
            GraphNode waypoint = new GraphNode(waypointTextField.getText(),x,y);
            waypointListView.getItems().add(waypoint);
            startCombo.getItems().add(waypoint);
            endCombo.getItems().add(waypoint);
            getPOIs.add(waypoint);
            POILinks();
            test();
        }
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
    private void startAndEndCombo(){
        startCombo.getItems().addAll(getPOIsLinked);
        endCombo.getItems().addAll(getPOIsLinked);
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

    private String theMatrix() {
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
        GraphNode start = startCombo.getValue();
        GraphNode end = endCombo.getValue();
        System.out.println(startPixel + ", " + endPixel);
        System.out.println(start + ", " + end);
        List<GraphNode> path = null;
        List<GraphNode> path1 = null;
        String selectedAlgorithm = algorithmsCombo.getValue();




        if (startPixel!=null && endPixel!=null){
            if ("Depth First Search".equals(selectedAlgorithm)){
                path = Algorithms.findPathDepthFirst(startPixel,endPixel);

            }
            if ("Breadth First Search".equals(selectedAlgorithm)){
                path = Algorithms.BFSAlgorithm(startPixel,endPixel);

            }
            if ("Dijkstra's Algorithm".equals(selectedAlgorithm)){
                path = Algorithms.BFSAlgorithm(startPixel,endPixel);

            }

        }
        if (path!=null){
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
        if(start!=null && end!=null){
            if ("Depth First Search".equals(selectedAlgorithm)){
                path1 = Algorithms.findPathDepthFirst(start,end);
                System.out.println("DFS");
                System.out.println(path1);
            }
            if ("Breadth First Search".equals(selectedAlgorithm)){
                path1 = Algorithms.BFSAlgorithm(start,end);
                System.out.println("BFS");
                System.out.println(path1);
            }
            if ("Dijkstra's Algorithm".equals(selectedAlgorithm)){
                path1 = Algorithms.BFSAlgorithm(start,end);
            }
        if (path1!=null){
            for (int i = 0; i<path1.size()-1;i++){
                GraphNode current = path1.get(i);
                System.out.println(current);
                GraphNode next = path1.get(i+1);
                System.out.println(next);
                Line line = new Line(current.getX(),current.getY(),next.getX(),next.getY());
                line.setUserData("pathLine");
                ((Pane) imageView.getParent()).getChildren().add(line);
            }
        }
        }
    }


    @FXML
    public void clearMap(){
        Pane pane = (Pane) imageView.getParent();
        pane.getChildren().removeIf(node -> node instanceof Circle);
        pane.getChildren().removeIf(node -> node instanceof Line);
        startCombo.getSelectionModel().clearSelection();
        endCombo.getSelectionModel().clearSelection();
        startCombo.setPromptText("Starting Point");
        endCombo.setPromptText("Ending Point");
        startPoint = null;
        endPoint = null;
    }

    @FXML
    public void clearPath(){
        Pane pane = (Pane) imageView.getParent();
        pane.getChildren().removeIf(node -> "pathCircle".equals(node.getUserData()));
        pane.getChildren().removeIf(node -> "pathLine".equals(node.getUserData()));
        startCombo.getSelectionModel().clearSelection();
        endCombo.getSelectionModel().clearSelection();
        startCombo.setPromptText("Starting Point");
        endCombo.setPromptText("Ending Point");
        startPoint = null;
        endPoint = null;
    }


    private void POILinks(){
        getPOIsLinked = getPOIs;
    }
    private void POIs(){
        getPOIs = Utilities.readInDatabase();
    }
    //private set
    private void test(){
        ArrayList<GraphNode> list = getPOIsLinked;
        for (GraphNode poi : list){
            System.out.println(poi.getName());
            List<GraphNode> linkedPOIs = poi.getLinks();
            Pane pane = (Pane) imageView.getParent();
            int radius = 4;
            Circle circle = new Circle(poi.getX()+radius,poi.getY()+radius,radius,Color.GREEN);
            pane.getChildren().add(circle);

            for (GraphNode graphNode: linkedPOIs) {
                System.out.println("Linked to " + graphNode);
                GraphNode linkedPOI = graphNode;
                Line line = new Line(linkedPOI.getX()+radius,linkedPOI.getY()+radius,poi.getX()+radius,poi.getY()+radius);
                line.setFill(Color.RED);
                pane.getChildren().add(line);
            }
        }
    }






}









