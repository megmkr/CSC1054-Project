import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;;
import java.io.*;
import java.util.Scanner;

public class GameWindow extends JPanel{
   private ArrayList<ArrayList<GameObject>> gameObjectArray = new ArrayList<ArrayList<GameObject>>();
   private Player gamePlayer;

   public GameWindow(){
      
      //size and color
      setSize(832,655);
      setBackground(Color.GRAY);
      
      //creating border
      setBorder(BorderFactory.createLineBorder(Color.black,8));   
      
      //for key input
      addKeyListener(new GetThingsMoving());
      setFocusable(true);
      
      try{
         Scanner sc = new Scanner(new File("GameMap.txt"));
         int playerPosX = (sc.nextInt()*25)+20;
         int playerPosY = (sc.nextInt()*25)+20;
         gamePlayer = new Player(playerPosX, playerPosY, Color.GREEN);
         
         int rows = sc.nextInt();
         int cols = sc.nextInt();
         int blockPosX =20; //so the blocks dont overlap the border and centered at middle
         int blockPosY =20;
         
         //fill 2d arraylist with innerlists
         for(int a=0; a<rows; a++){
            ArrayList<GameObject> innerList = new ArrayList<GameObject>();
            gameObjectArray.add(innerList);
         }
         //add game objects to arraylist
         for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
               int newBlock = sc.nextInt();
               if(newBlock == 1){
                  gameObjectArray.get(i).add(new GameObject(blockPosX,blockPosY,Color.BLUE));
               }else if(newBlock == 2){
                  gameObjectArray.get(i).add(new Block(blockPosX,blockPosY,Color.RED));
               }else if(newBlock == 0){
                  gameObjectArray.get(i).add(null);
                  //gameObjectArray.get(i).add(new EmptyObject(blockPosX, blockPosY, new Color(0,0,0,0)));
               }
               blockPosX=blockPosX+25;
            }
            blockPosX=20;
            blockPosY= blockPosY + 25;
         }
      }catch(FileNotFoundException fnf){
      } 
      
      //create timer for smooth movement
      Timer t = new Timer(10, new MovingTimer());
      t.start();
   }
   //paint component draws squares from saved objects in arraylist
   public void paintComponent(Graphics g){
      super.paintComponent(g);
      for(int i = 0; i<gameObjectArray.size(); i++){
         for(int j = 0; j<gameObjectArray.get(0).size(); j++){
            if(gameObjectArray.get(i).get(j) != null)
               gameObjectArray.get(i).get(j).draw(g);
         }
      } 
      gamePlayer.draw(g);
   }
   //members to control direction
   private boolean up;
   //private boolean down;
   private boolean right;
   private boolean left;
   private boolean down;
   public class GetThingsMoving implements KeyListener{
      public void keyTyped(KeyEvent e){}
      //stop box if key released
      public void keyReleased(KeyEvent e){
         if(e.getKeyCode() == KeyEvent.VK_W){
            up=false;
         }
         if(e.getKeyCode() == KeyEvent.VK_S){
            down=false;
         }
         if(e.getKeyCode() == KeyEvent.VK_A){
            left = false;    
         }
         if(e.getKeyCode() == KeyEvent.VK_D){
            right = false; 
         }
       }
      //move box if key pressed
      public void keyPressed(KeyEvent e){
         if(e.getKeyCode() == KeyEvent.VK_W){
            up = true;
         }else if(e.getKeyCode() == KeyEvent.VK_S){
            down = true;
         }else if(e.getKeyCode() == KeyEvent.VK_A){
            left = true;
         }else if(e.getKeyCode() == KeyEvent.VK_D){
            right = true;
         }

         repaint();
      }
   }
   //makes the movement smooth! called every 10ms
   public class MovingTimer implements ActionListener{
      public void actionPerformed(ActionEvent e){
         ArrayList<GameObject> smallerArray= new ArrayList<GameObject>();
         //getting all objects within one block radius of player
         for(int i =0; i<gameObjectArray.size(); i++){
            for(int j=0; j<gameObjectArray.get(0).size(); j++){
               if(gameObjectArray.get(i).get(j) != null && 
                  gameObjectArray.get(i).get(j).getX() >= gamePlayer.getX()-25 && gameObjectArray.get(i).get(j).getX() <= gamePlayer.getX()+25 &&
                  gameObjectArray.get(i).get(j).getY() >= gamePlayer.getY()-25 && gameObjectArray.get(i).get(j).getY() <= gamePlayer.getY()+25){
                  
                  smallerArray.add(gameObjectArray.get(i).get(j));
               } 
            }
         }
         //calls to move method in player class
         //checks if move makes collision
         if(up/* && gamePlayer.isOnGround(smallerArray)*/){
            //int jump = 7;
            gamePlayer.move(-1,0,smallerArray);
         }
         if(left){
            gamePlayer.move(0,-1,smallerArray);
         }
         if(right){
            gamePlayer.move(0,1,smallerArray);
         }
         if(down){
            gamePlayer.move(1,0,smallerArray);
         }         
         repaint();       
      }
   }   
   public static void main(String [] args){
      JFrame newWindow = new JFrame("Platformer Game");
      newWindow.add(new GameWindow());
      newWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      newWindow.setSize(816,638);
      newWindow.setVisible(true);
   }
}