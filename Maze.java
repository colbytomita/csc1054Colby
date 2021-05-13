import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;

public class Maze extends Application{
   //creates a flowpane, a 2d array to hold the maze data, a canvas to show the maze, and a hbox to show the user has won
   FlowPane root = new FlowPane();
   static int[][] mazeData = new int[21][21];
   MyMaze theMaze = new MyMaze();
   HBox win = new HBox();
   
   public void start(Stage stage){
      //adds label to the hbox and adds the maze canvas to the flowpane, then creating the stage
      win.getChildren().add(new Label("You win!"));
      root.getChildren().add(theMaze);
      Scene scene = new Scene(root, 525, 525);
      stage.setScene(scene);
      stage.setTitle("First Maze");
      stage.show();
      //implements the keylistener into the flowpane 
      root.setOnKeyPressed(new KeyPressedListener());
      root.requestFocus();
      
   }
   
   public static void main(String[] args){
      //launch method
      launch(args);
   }
   
   public class MyMaze extends Canvas{
      //instantiates variables that are usde in the constructor
      GraphicsContext gc;
      int tempX = 0;
      int tempY = 0;
      private int playerX;
      private int playerY;
      
      public MyMaze(){
         //gives the variables values and sets the dimensions of the canvas
         gc = getGraphicsContext2D();
         setHeight(525);
         setWidth(525);
         createArray();
         //sets the starting position of the character
         for(int i = 0; i < 21; i++){
            if(mazeData[0][i] == 0){
               mazeData[0][i] = 2;
               playerX = i;
               playerY = 0;
            }
         }
         draw();
      }
      //various mutators and accessor methods
      public int getX(){
         return playerX;
      }
      
      public void setX(int i){
         playerX = i;
      }
      
      public int getY(){
         return playerY;
      }
      
      public void setY(int i){
         playerY = i;
      }
      //fills the 2d array with data from a file
      public void createArray(){
         try{
         Scanner scan = new Scanner(new File("mazedata.txt"));
            for(int i = 0; i < 21; i++){
               for(int c = 0; c < 21; c++){
                  mazeData[i][c] = scan.nextInt();
               }
            }
         }
         catch(FileNotFoundException fnfe){
         }
      }
      
      public void draw(){
         //clears any initial drawings on the canvas
         gc.clearRect(0, 0, 525, 525);
         //takes in data from the 2d array and prints out a maze using those values
         for(int i = 0; i < 21; i++){
            for(int c = 0; c < 21; c++){
               if(mazeData[i][c] == 1){
                  gc.setFill(Color.BLACK);
                  gc.fillRect(tempX, tempY, 25, 25);
                  tempX += 25;
               }else if(mazeData[i][c] == 0){
                  gc.setFill(Color.WHITE);
                  gc.fillRect(tempX, tempY, 25, 25);
                  tempX += 25;
               }else if(mazeData[i][c] == 2){
                  gc.setFill(Color.CYAN);
                  gc.fillRect(tempX, tempY, 25, 25);
                  tempX += 25;
               }
            }
            tempY += 25;
            tempX = 0;
         }
         tempY = 0;
         tempX = 0;
      }
   }
   
   public class KeyPressedListener implements EventHandler<KeyEvent>{
      public void handle(KeyEvent event){
         try{
         //the key listener checks to see if there is a wall in the position that theuser wants to move, 
         //if there is a wall then nothing happens, if there isn't a wall then it will move the user to the new spot
         //left arrow key function
         if(event.getCode() == KeyCode.LEFT){
            if(mazeData[theMaze.getY()][theMaze.getX() - 1] == 0){
               mazeData[theMaze.getY()][theMaze.getX()] = 0;
               mazeData[theMaze.getY()][theMaze.getX() - 1] = 2;
               theMaze.setX(theMaze.getX() - 1);
               theMaze.draw();
            }
         }
         //right arrow key function
         if(event.getCode() == KeyCode.RIGHT){
            if(mazeData[theMaze.getY()][theMaze.getX() + 1] == 0){
               mazeData[theMaze.getY()][theMaze.getX()] = 0;
               mazeData[theMaze.getY()][theMaze.getX() + 1] = 2;
               theMaze.setX(theMaze.getX() + 1);
               theMaze.draw();
            }
         }
         //up arrow key function
         if(event.getCode() == KeyCode.UP){
            if(mazeData[theMaze.getY() - 1][theMaze.getX()] == 0){
               mazeData[theMaze.getY()][theMaze.getX()] = 0;
               mazeData[theMaze.getY() - 1][theMaze.getX()] = 2;
               theMaze.setY(theMaze.getY() - 1);
               theMaze.draw();
            }
         }
         //down arrow key function
         if(event.getCode() == KeyCode.DOWN){
            if(mazeData[theMaze.getY() + 1][theMaze.getX()] == 0){
               mazeData[theMaze.getY()][theMaze.getX()] = 0;
               mazeData[theMaze.getY() + 1][theMaze.getX()] = 2;
               theMaze.setY(theMaze.getY() + 1);
               theMaze.draw();
          //checks to see if the user has reached the end of the maze and if they have then it will clear the flowpane and add the hbox that says you win
               if(theMaze.getY() == 20){
                  root.getChildren().clear();
                  root.getChildren().add(win);
               }
            }
         }
      }
      catch(ArrayIndexOutOfBoundsException fnfe){
      
      }
      }
   }
   
}