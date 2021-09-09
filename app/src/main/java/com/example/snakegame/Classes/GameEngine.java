package com.example.snakegame.Classes;

import com.example.snakegame.Activities.GameActivity;
import com.example.snakegame.Enums.Direction;
import com.example.snakegame.Enums.GameState;
import com.example.snakegame.Enums.TitleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameEngine extends SnakeMechanics{
    public static final int BallWidthNumber = 30; // the width of map
    public static final int BallHeightNumber = 50; // the height of map
    public static int eatenApples = 0; // apples that snake has already eaten
    private Direction currentDirection = Direction.East; // direction in the beginning of level
    private GameState currentGameState = GameState.Running; // GameState in the beginning of level
    private List<Coordinate> crossings = new ArrayList<>();
    private List<Coordinate> walls = new ArrayList<>();
    private List<Coordinate> snake = new ArrayList<>();
    private List<Coordinate> apples = new ArrayList<>();
    private boolean increaseTail = false;
    private Random random = new Random();

    private Coordinate getSnakeHead(){
        return snake.get(0);
    }

  public GameEngine(){
  }
    // functions add the  game elements into the level according to its number
  public void initGame1(){
      super.AddSnake(snake);
      Walls.AddWalls1(walls,BallWidthNumber,BallHeightNumber);
      Apples.AddApples(random,BallWidthNumber,BallHeightNumber,snake,apples,walls,crossings);
  }
  public void initGame2(){
        super.AddSnake(snake);
        Walls.AddWalls2(walls,BallWidthNumber,BallHeightNumber);
        super.AddCrossings(crossings);
      Apples.AddApples(random,BallWidthNumber,BallHeightNumber,snake,apples,walls,crossings);
    }
  public void initGame3(){
        super.AddSnake(snake);
        Walls.AddWalls3(walls,BallWidthNumber,BallHeightNumber);
        super.AddCrossings(crossings);
      Apples.AddApples(random,BallWidthNumber,BallHeightNumber,snake,apples,walls,crossings);
    }
  public void initInfiGame(){
        super.AddSnake(snake);
        Walls.AddWalls1(walls,BallWidthNumber,BallHeightNumber);
      Apples.AddApples(random,BallWidthNumber,BallHeightNumber,snake,apples,walls,crossings);
    }

  public void UpdateDirection(Direction newDirection){
      if(Math.abs(newDirection.ordinal()-currentDirection.ordinal())%2==1)
          currentDirection = newDirection;
  } // updates the direction of snake if the new one is athwart to current
// Updates the place of snake according to the direction
  public void Update(){
      switch (currentDirection) {
          case North:
              UpdateSnake(0,-1);
              break;
          case East:
              UpdateSnake(1,0);
              break;
          case West:
              UpdateSnake(-1,0);
              break;
          case South:
              UpdateSnake(0,1);
              break;
      }
      // Checks wall collision
      for(Coordinate w : walls){
          if(snake.get(0).equals(w)){
            currentGameState = GameState.Lost;
          }

      }
      // Checks self-collision
      for(int i = 1; i<snake.size();i++){
          if(getSnakeHead().equals(snake.get(i))) {
              currentGameState = GameState.Lost;
          }
      }
      // checks eating the apple
      Coordinate appleToRemove = null;
      for(Coordinate a:apples){
          if(getSnakeHead().equals(a)){
              appleToRemove = a;
              increaseTail = true;
          }
      }
      if(appleToRemove!= null){
          apples.remove(appleToRemove);
          Apples.AddApples(random,BallWidthNumber,BallHeightNumber,snake,apples,walls,crossings);
      }
  }
  // updates the coordinate of each part of the snake
    public void UpdateSnake(int x, int y) {
        int newX = snake.get(snake.size() - 1).getX();
        int newY = snake.get(snake.size() - 1).getY();
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }
        snake.get(0).setX(snake.get(0).getX() + x);
        snake.get(0).setY(snake.get(0).getY() + y);
        super.CrossUpdateSnake(snake, crossings);
        if (increaseTail) {
            snake.add(new Coordinate(newX, newY));
            increaseTail = false;
            eatenApples++;
            GameActivity.sp.play(GameActivity.eatId, 1, 1, 0, 0, 1); // sound of eating the apple
        }
    }

  public TitleType [] [] getMap(){
      TitleType [] [] map = new TitleType [BallWidthNumber][BallHeightNumber];
      for(int x = 0; x < BallWidthNumber; x++){
          for(int y = 0; y < BallHeightNumber; y++){
              map [x] [y] = TitleType.Nothing;
          }
      }
      for(Coordinate s: snake){
          map[s.getX()] [s.getY()] = TitleType.SnakeTail;
      }
      map[snake.get(0).getX()][snake.get(0).getY()] = TitleType.SnakeHead;

      for(Coordinate cross:crossings){
          map[cross.getX()][cross.getY()] = TitleType.Crossing;
      }
      for(Coordinate wall:walls){
          map[wall.getX()][wall.getY()] = TitleType.Wall;
      }
      for(Coordinate a: apples){
          map[a.getX()][a.getY()] = TitleType.Apple;
      }
      return map;
  } // creates the level map

  public GameState getCurrentGameState(){
      return currentGameState;
  }
}
