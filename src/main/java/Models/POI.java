package Models;

import javafx.scene.image.Image;

public class POI {
    private String name;
    private int[] coordinates;
     private Image icon;

     public POI(String name, int[] coordinates, Image icon){

     }
     public void setName(String name){
         this.name = name;
     }
     public void setCoordinates(int[] coordinates){
         this.coordinates = coordinates;
     }
     public void setIcon(Image icon){
         this.icon = icon;
     }

     public String getName(){
         return name;
     }
     public int[] getCoordinates(){
         return coordinates;
     }
     public Image getIcon(){
         return icon;
     }


}
