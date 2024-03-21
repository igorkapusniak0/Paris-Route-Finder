package Models;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class POI {
    private String name;
    private int x;
    private int y;
    private HashMap<Double, POI> POIs;

     public POI(String name, int x , int y,HashMap<Double,POI>POIs){
        setName(name);
        setX(x);
        setY(y);
        setPOIs(POIs);
     }
     public void setName(String name){
         this.name = name;
     }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }

    public String getName(){
         return name;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setPOIs(HashMap<Double,POI>POIs){
         this.POIs = POIs;
    }
    public HashMap<Double, POI> getPOIs(){
         return POIs;
    }


}
