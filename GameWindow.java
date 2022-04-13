import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;;
import java.io.*;
import java.util.Scanner;
//import java.lang.Math;

public class GameWindow extends JPanel{
   private ArrayList<ArrayList<GameObject>> gameObjectArray;
   private Player gamePlayer;
   private int gravity =1;  //speed of gravity
   private int counter =0;  //counter to indicate when to speed up gravity (every 20 ticks)
   private double jump =7; //indicates jump capacity/speed
   private ArrayList<GameObject> smallerArray; //array to use with collides method (contains immediate surroundings of player)

   
   public GameWindow(){
      
      //size and color
      setSize(832,655);
      setBackground(Color.PINK);
     
      //creating border
      setBorder(BorderFactory.createLineBorder(Color.black,8));   
      
      //for key input
      addKeyListener(new GetThingsMoving());
      setFocusable(true);
      
      //instantiating arraylist
      gameObjectArray = new ArrayList<ArrayList<GameObject>>();
      try{
         Scanner sc = new Scanner(new File("GameMap.txt"));
         int playerPosX = (sc.nextInt()*25)+20;
         int playerPosY = (sc.nextInt()*25)+20;
         gamePlayer = new Player(playerPosX, playerPosY, Color.WHITE);
         
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
                  gameObjectArray.get(i).add(new Block(blockPosX,blockPosY,Color.BLUE));
               }else if(newBlock == 2){
                  gameObjectArray.get(i).add(new Block(blockPosX,blockPosY,Color.RED));
               }else if(newBlock == 0){
                  gameObjectArray.get(i).add(null);
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
   private boolean right;
   private boolean left;
   public class GetThingsMoving implements KeyListener{
      public void keyTyped(KeyEvent e){
         if(e.getKeyChar() == 'w' && gamePlayer.isOnGround(smallerArray)){
            //reset everything for even jump
            gravity = 1;
            jump =7;
            counter =0;
         }
      }
      //stop box if key released
      public void keyReleased(KeyEvent e){
         if(e.getKeyCode() == KeyEvent.VK_A){
            left = false;    
         }
         if(e.getKeyCode() == KeyEvent.VK_D){
            right = false; 
         }
       }
      //move box if key pressed
      public void keyPressed(KeyEvent e){
         if(e.getKeyCode() == KeyEvent.VK_A){
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
      boolean victory = false;  
         //getting all non-null objects within one block radius of player to check for collision
         smallerArray = new ArrayList<GameObject>();
         for(int i =0; i<gameObjectArray.size(); i++){
            for(int j=0; j<gameObjectArray.get(0).size(); j++){
               if(gameObjectArray.get(i).get(j) != null && 
                  gameObjectArray.get(i).get(j).getX() >= gamePlayer.getX()-25 && gameObjectArray.get(i).get(j).getX() <= gamePlayer.getX()+25 &&
                  gameObjectArray.get(i).get(j).getY() >= gamePlayer.getY()-25 && gameObjectArray.get(i).get(j).getY() <= gamePlayer.getY()+25){
                  
                  smallerArray.add(gameObjectArray.get(i).get(j));
                  
                  //code to determine collision with victory block
                  if(gameObjectArray.get(i).get(j).getColor()== Color.RED){
                     int input = JOptionPane.showOptionDialog(null, "YOU WON!", "Victory Screen", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                     if(input == JOptionPane.OK_OPTION){
                        System.exit(1);
                     }
                  }           
               } 
            }
         }
         //jump
         for(int i=0;i<jump;i++){
            gamePlayer.setY(gamePlayer.getY()-1);
            if(gamePlayer.collides(smallerArray)){
               gamePlayer.collidesUp(smallerArray);
               jump=0;
            }
         }
         jump-=.1;
         
         //move left
         if(left){
            gamePlayer.move(-1,smallerArray);
         }
         //move right
         if(right){
            gamePlayer.move(1,smallerArray);
         }
         //put gravity to work
         if(!gamePlayer.isOnGround(smallerArray)){
            for(int i =0; i<gravity; i++){
               gamePlayer.setY(gamePlayer.getY()+1);
            }
            counter++;
            if(counter==20){
               gravity++;
               counter=0;
            }        
         }else{
            gravity = 1;
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