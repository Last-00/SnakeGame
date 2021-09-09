package com.example.snakegame.Classes;

import java.util.List;

public class SnakeMechanics {
    // function creates snake as the list with coordinates
    public void AddSnake(List<Coordinate> snake) {


        snake.add(new Coordinate(5, 7));
        snake.add(new Coordinate(4, 7));
        snake.add(new Coordinate(3, 7));
        snake.add(new Coordinate(2, 7));

    }
 // function creates elements that let the snake cross the map from one side to other
    public void AddCrossings(List<Coordinate> crossings) {
        // top and bottom crossings
        for (int x = 0; x < GameEngine.BallWidthNumber; x++) {
            crossings.add(new Coordinate(x, 0));
            crossings.add(new Coordinate(x, GameEngine.BallHeightNumber - 1));
        }
        // right and left crossings
        for (int y = 0; y < GameEngine.BallHeightNumber; y++) {
            crossings.add(new Coordinate(0, y));
            crossings.add(new Coordinate(GameEngine.BallWidthNumber - 1, y));
        }
    }

// function updates the coordinates of snake according to the coordinates of crossing
   public void CrossUpdateSnake(List<Coordinate> snake,List<Coordinate> crossings)
    {

        for(Coordinate cross : crossings) {
            if (snake.get(0).equals(cross)) {
                for (int i = snake.size() - 1; i > 0; i--) {
                    snake.get(i).setX(snake.get(i - 1).getX());
                    snake.get(i).setY(snake.get(i - 1).getY());
                }
                switch (cross.getX()){
                    case GameEngine.BallWidthNumber-1: snake.get(0).setX(1);
                        break;
                    case 0: snake.get(0).setX(GameEngine.BallWidthNumber-2);
                        break;
                }
                switch (cross.getY()){
                    case GameEngine.BallHeightNumber-1: snake.get(0).setY(1);
                        break;
                    case 0: snake.get(0).setY(GameEngine.BallHeightNumber-2);
                        break;
                }
            }
        }

    }

}
