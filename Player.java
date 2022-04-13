import java.awt.Color;
import java.util.ArrayList;

public class Player extends GameObject{
   //constructor
   public Player(int posX, int posY, Color color){
      super(posX, posY, color);
      
   }
   //checks if player is on the ground using collides
   public boolean isOnGround(ArrayList<GameObject> mapData){
      setY(getY()+1);
      for(int i=0; i<mapData.size(); i++){
         if(super.collides(mapData.get(i))){
            setY(mapData.get(i).getY() -25); //sets player on top of collided object
            return true;
         }
      }
      setY(getY()-1);
      return false;
   }
   //move method for left and right 
   public boolean move(int moveX, ArrayList<GameObject> mapData){
      setX(getX()+moveX);
      
      if(collides(mapData)){
         setX(getX()-moveX);
         return false;
      }else{
         return true;
      }
   }
   //method to resolve collision if player hits ceiling
   public void collidesUp(ArrayList<GameObject> mapData){
      for(int i=0; i<mapData.size(); i++){
         if(super.collides(mapData.get(i))){
            setY(mapData.get(i).getY() +25);
         }
      }
   }
   //calls to the super collides method with each possible object it collided with
   public boolean collides(ArrayList<GameObject> mapData){
      for(int i =0; i<mapData.size(); i++){
         if(super.collides(mapData.get(i)) == true)
            return true;
      }
      return false;
   }
   
}