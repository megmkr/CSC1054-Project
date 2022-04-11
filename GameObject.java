import java.awt.Color;
import java.awt.Graphics;

public class GameObject{
   //member variables
   private int posX, posY;
   private Color color;
   //constructor
   public GameObject(int posX, int posY, Color color){
      this.posX = posX;
      this.posY = posY;
      this.color = color;
   }
   //accessors
   public int getX(){
      return posX;
   }
   public int getY(){
      return posY;
   }
   //mutators
   public void setX(int newX){
      posX = newX;
   }
   public void setY(int newY){
      posY = newY;
   }
   //collides method based on pseudocode
   public boolean collides(GameObject go){
      if(this == go){
            return false;
      }else{
         int topThis = posY-12;
         int bottomThis = posY+13;
         int leftThis = posX-12;
         int rightThis = posX+13;
         int topOther = go.posY-12;
         int bottomOther = go.posY+13;
         int leftOther = go.posX-12;
         int rightOther = go.posX+13;
           /*System.out.println("Bottom player: " +bottomThis + ", Top object: " +topOther);
            System.out.println("Top player: " +topThis + ", Bottom object: " +bottomOther);
            System.out.println("left player: " +leftThis + ", Right object: " +rightOther);
            System.out.println("right player: " +rightThis + ", left object: " +leftOther);
            System.out.println();*/

            return ((bottomThis < topOther)||
                     (topThis > bottomOther)||
                     (leftThis < rightOther)||
                     (rightThis < leftOther));
      }
   }
   
   //draw method draws squares
   public void draw(Graphics g){
      g.setColor(color);
      g.fillRect(posX-12, posY-12, 25, 25);
   }
   public String toString(){
      return posX + "" + posY;
   }
}