import java.awt.Color;
import java.awt.Graphics;

public class GameObject{
   //member variables
   private int posX, posY;
   private Color color;
   private static boolean victory = false;
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
   public static boolean getVictory(){
      return victory;
   }
   public Color getColor(){
      return color;
   }
   //mutators
   public void setX(int newX){
      posX = newX;
   }
   public void setY(int newY){
      posY = newY;
   }
   public boolean collides(GameObject go){
      boolean xCollideFalse = (posX+13) <= (go.posX-12) || (posX-12) >= (go.posX+13);
      boolean yCollideFalse = (posY+13) <= (go.posY-12) || (posY-12) >= (go.posY+13);
      //checks if the collision box is red (i.e. victory box)
      if(go.color == Color.RED){
         victory = true;
      }
      if(xCollideFalse || yCollideFalse){
         return false;
      }else{
         return true;
      }
   }
   //draw method draws squares
   public void draw(Graphics g){
      g.setColor(color);
      g.fillRect(posX-12, posY-12, 25, 25);
   }
   public String toString(){
      return posX + "  " + posY;
   }
}