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
      if(collides(mapData)){
         setY(getY()-1);
         return true;
      }else{
         return false;
      }
   }
   //moves player based on key input and checks validity using collides
   public boolean move(int moveY, int moveX, ArrayList<GameObject> mapData){
      setY(getY()+moveY);
      setX(getX()+moveX);
      //System.out.println("old " +getX() + " " +getY());
      if(collides(mapData)){
         //System.out.println("here");
         setY(getY()-moveY);
         setX(getX()-moveX);
         //System.out.println("new " +getX() + " " +getY());
         return false;
      }else{
         return true;
      }
   }/*
   public boolean moveUp(ArrayList<GameObject> mapData){
      setY(getY()-1);
      if(collides(mapData)){
         setY(getY()+1);
         return false;
      }else{
         return true;
      }
   }
   public boolean moveDown(ArrayList<GameObject> mapData){
      setY(getY()+1);
      if(collides(mapData)){
         setY(getY()-1);
         return false;
      }else{
         return true;
      }
   }
   public boolean moveRight(ArrayList<GameObject> mapData){
      setX(getX()+1);
      if(collides(mapData)){
         setX(getX()-1);
         return false;
      }else{
         return true;
      }
   }
   public boolean moveLeft(ArrayList<GameObject> mapData){
      setX(getX()-1);
      if(collides(mapData)){
         setX(getX()+1);
         return false;
      }else{
         return true;
      }
   }*/

   //calls to the super collides method with each possible object it collided with
   public boolean collides(ArrayList<GameObject> mapData){
      for(int i =0; i<mapData.size(); i++){
         if(super.collides(mapData.get(i)) == true)
            return true;;
            //if(collides && mapData.get(i).get(j).getColor() == Color.RED){
               //indicate victory
      }
      return false;
   }
      
}