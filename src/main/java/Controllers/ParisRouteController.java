package Controllers;

import Models.GraphNode;
import Utilites.Algorithms;
import Utilites.Graph;
import Utilites.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.io.FileInputStream;
import java.io.IOException;
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
    private int[] waypoint = new int[2];
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

    private List<List<GraphNode>> allPaths;

    @FXML
    TextField waypointTextField;
    @FXML
    ListView<GraphNode> waypointListView;
    @FXML
    ComboBox<GraphNode> POICombo;
    @FXML
    ListView<GraphNode> POIList;
    private int[] waypointCoord = new int[2];

    @FXML
    Label startPixelCoord;
    @FXML
    Label endPixelCoord;
    @FXML
    TreeView<String> DFSRoutes;



    private ArrayList<GraphNode> getPOIs = null;
    private ArrayList<GraphNode> getPOIsLinked = null;

    public void initialize() {
        parisMap = imageView.getImage();
        setAlgorithmsCombo();
        waypoint = getCoordinates();
        POIs();
        POILinks();
        blackAndWhiteImage = Utilities.convertToBlackAndWhite(parisMap);
        graph();
        Utilities.graphConnections(blackAndWhiteImage, graph);
        startAndEndCombo();
        setPOICombo();

    }


    @FXML
    public void addWaypoint(){
        if (waypointCoord!=null){
            GraphNode waypoint = new GraphNode(waypointTextField.getText(),waypointCoord[0],waypointCoord[1]);
            waypointListView.getItems().add(waypoint);
            startCombo.getItems().add(waypoint);
            endCombo.getItems().add(waypoint);
            getPOIs.add(waypoint);
            POILinks();
            drawCircle(waypointCoord,"General");
            drawWaypoint(waypointCoord);
        }

    }
    private void setAlgorithmsCombo(){
        algorithmsCombo.getItems().addAll(
                "Depth First Search",
                "Breadth First Search",
                "Dijkstra's Algorithm");
    }

    private void addDFSRoutes() {
        TreeItem<String> rootItem = new TreeItem<>("Paths");

        int pathCounter = 1;
        for (List<GraphNode> path : allPaths) {

            TreeItem<String> pathItem = new TreeItem<>("Path " + pathCounter);
            rootItem.getChildren().add(pathItem);

            for (GraphNode node : path) {
                TreeItem<String> nodeItem = new TreeItem<>(node.toString());
                pathItem.getChildren().add(nodeItem);
            }

            pathCounter++;
        }
        DFSRoutes.setRoot(rootItem);
        DFSRoutes.refresh();
    }

    @FXML
    public void setStartPoint(){
        startPoint = getCoordinates();
        startPixelCoord.setText(startPoint[0] + ", " + startPoint[1]);
    }
    private void startAndEndCombo(){
        startCombo.getItems().addAll(getPOIsLinked);
        endCombo.getItems().addAll(getPOIsLinked);
    }
    private void setPOICombo(){
        for (GraphNode graphNode : getPOIsLinked){
            POICombo.getItems().add(graphNode);
        }
    }
    @FXML
    public void addPOI(){
        POIList.getItems().add(POICombo.getValue());
    }
    private List<GraphNode> getPOIsToVisit(){
        if (POIList.getItems().size()<1){
            return new ArrayList<>();
        }
        return POIList.getItems();
    }
    @FXML
    public void setEndPoint(){
        endPoint = getCoordinates();
        endPixelCoord.setText(endPoint[0] + ", " + endPoint[1]);
    }

    @FXML
    public void setWaypoint(){
        waypointCoord = getCoordinates();
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
                    if (graph.pixelGraph[coordinates[1]][coordinates[0]] != null){
                        System.out.println("pixel");
                    }

                }
                Circle circle = new Circle();
                circle.setFill(Color.RED);
                circle.setCenterX(coordinates[0]+3);
                circle.setCenterY(coordinates[1]+3);
                circle.setRadius(3);
                circle.setUserData("EdgeCircle");
                ((Pane) imageView.getParent()).getChildren().add(circle);
            }
        });
        return coordinates;
    }

    private void drawCircle(int[] coords,String type){
        Circle circle = new Circle();
        if (type.equals("Start")){
            circle.setFill(Color.GREEN);
        }
        else if (type.equals("End")){
            circle.setFill(Color.RED);
        }
        else {
            circle.setFill(Color.BLUE);
        }
        circle.setCenterX(coords[0]+3);
        circle.setCenterY(coords[1]+3);
        circle.setRadius(3);
        circle.setUserData("EdgeCircle");
        ((Pane) imageView.getParent()).getChildren().add(circle);
    }
    private void drawWaypoint(int[] coords) {
        Image waypointImage;
        try {
            waypointImage = new Image(new FileInputStream("src/main/resources/Image/GeneralIcon1.png"));
        } catch (IOException e) {
            e.printStackTrace();
            waypointImage = null;
        }
        ImageView iconView = new ImageView();
        iconView.setImage(waypointImage);
        iconView.setX(coords[0]-waypointImage.getWidth()/2);
        iconView.setY(coords[1]-waypointImage.getHeight()/2);
        ((Pane) imageView.getParent()).getChildren().add(iconView);
    }
    private List<GraphNode> findShortestPath(GraphNode start, GraphNode end){
        int shortest = Integer.MAX_VALUE;
        List<GraphNode> shortestList = null;
        for (List<GraphNode> list : allPaths){
            if (list.size()<shortest && list.contains(start) && list.contains(end)){
                shortestList = list;
            }
        }
        return shortestList;
    }

    @FXML
    private void calculatePath(){
        GraphNode start = startCombo.getValue();
        GraphNode end = endCombo.getValue();
        List<GraphNode> toVisit = getPOIsToVisit();
        /*for (GraphNode graphNode : toVisit){
            System.out.println(graphNode);
        }  */      //System.out.println(start + ", " + end);
        List<GraphNode> path1 = null;
        String selectedAlgorithm = algorithmsCombo.getValue();
        if(start!=null && end!=null){
            if ("Depth First Search".equals(selectedAlgorithm)){
                allPaths = Algorithms.DFSAlgorithmAllPaths(start,end);
                addDFSRoutes();
                path1 = findShortestPath(start,end);
            }
            if ("Breadth First Search".equals(selectedAlgorithm)){
                path1 = Algorithms.BFSAlgorithm(start,end);
            }
            if ("Dijkstra's Algorithm".equals(selectedAlgorithm)){
                path1 = Algorithms.BFSAlgorithm(start,end);
            }
            if (path1!=null){
                System.out.println(path1.size());
                for (int i = 0; i<path1.size()-1;i++){
                    GraphNode current = path1.get(i);
                    System.out.println(current);
                    GraphNode next = path1.get(i+1);
                    //System.out.println(next);
                    Line line = new Line(current.getX(),current.getY(),next.getX(),next.getY());
                    line.setUserData("pathLine");
                    ((Pane) imageView.getParent()).getChildren().add(line);
                }
            }
        }
    }
    @FXML
    private void calculatePixelPath(){
        GraphNode startPixel;
        GraphNode endPixel;
        List<GraphNode> toVisit = getPOIsToVisit();
        List<GraphNode> path = null;
        String selectedAlgorithm = algorithmsCombo.getValue();
        if (startPoint!=null && endPoint!=null){
            System.out.println(startPoint);
            System.out.println(endPoint);
            startPixel = graph.pixelGraph[startPoint[1]][startPoint[0]];
            endPixel = graph.pixelGraph[endPoint[1]][endPoint[0]];
            System.out.println(startPixel + ", " + endPixel);
            if (startPixel!=null && endPixel!=null){
                if ("Depth First Search".equals(selectedAlgorithm)){
                    path = Algorithms.DFSAlgorithm(startPixel,endPixel);
                }
                if ("Breadth First Search".equals(selectedAlgorithm)){
                    path = Algorithms.BFSAlgorithm(startPixel,endPixel);
                }
                if ("Dijkstra's Algorithm".equals(selectedAlgorithm)){
                    path = Algorithms.BFSAlgorithm(startPixel,endPixel);

                }
            }
        }
        if (path!=null){
            for (GraphNode coords : path) {
                System.out.println(coords.getX() + ", " + coords.getY());
                int radius = 1;
                Circle circle = new Circle();
                circle.setFill(Color.GREEN);
                circle.setCenterX(coords.getX() + radius);
                circle.setCenterY(coords.getY() + radius);
                circle.setRadius(radius);
                circle.setUserData("pathCircle");
                ((Pane) imageView.getParent()).getChildren().add(circle);
            }
        }
    }


    @FXML
    public void clearMap(){
        Pane pane = (Pane) imageView.getParent();
        pane.getChildren().removeIf(node -> node instanceof Circle);
        pane.getChildren().removeIf(node -> node instanceof Line);


    }

    @FXML
    public void clearPath(){
        Pane pane = (Pane) imageView.getParent();
        pane.getChildren().removeIf(node -> "pathCircle".equals(node.getUserData()));
        pane.getChildren().removeIf(node -> "pathLine".equals(node.getUserData()));

    }


    private void POILinks(){
        getPOIsLinked = Utilities.poiLinks(getPOIs);
    }
    private void POIs(){
        getPOIs = Utilities.readInDatabase();
    }
    //private set
    private void test(){
        ArrayList<GraphNode> list = getPOIsLinked;
        for (GraphNode poi : list){
            //System.out.println(poi.getName());
            List<GraphNode> linkedPOIs = poi.getLinks();
           // System.out.println(poi.getLinks().size());
            Pane pane = (Pane) imageView.getParent();
            int radius = 4;
            Circle circle = new Circle(poi.getX()+radius,poi.getY()+radius,radius,Color.GREEN);
            pane.getChildren().add(circle);

            for (GraphNode graphNode: linkedPOIs) {
               // System.out.println("Linked to " + graphNode);
                GraphNode linkedPOI = graphNode;
                Line line = new Line(linkedPOI.getX()+radius,linkedPOI.getY()+radius,poi.getX()+radius,poi.getY()+radius);
                line.setFill(Color.RED);
                pane.getChildren().add(line);
            }
        }
    }
    @FXML
    public void showAllPOILinks(){
        ArrayList<GraphNode> list = getPOIsLinked;
        int radius = 4;
        for (GraphNode poi : list){
            List<GraphNode> linkedPOIs = poi.getLinks();
            Pane pane = (Pane) imageView.getParent();
            for (GraphNode graphNode: linkedPOIs) {
                GraphNode linkedPOI = graphNode;
                Line line = new Line(linkedPOI.getX()+radius,linkedPOI.getY()+radius,poi.getX()+radius,poi.getY()+radius);
                line.setFill(Color.RED);
                pane.getChildren().add(line);
            }
        }
    }






}









