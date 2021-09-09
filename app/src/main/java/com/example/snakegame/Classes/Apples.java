package com.example.snakegame.Classes;


import java.util.List;
import java.util.Random;

public class Apples {
    // function adds apples in random places of map except of walls and snake
    public static void AddApples(Random random, int BallWidthNumber, int BallHeightNumber, List<Coordinate> snake,  List<Coordinate> apples,List<Coordinate> walls, List<Coordinate> crossings){
        Coordinate coordinate = null;
        boolean added = false;
        while (added == false){
            int x = 1 + random.nextInt(BallWidthNumber-2);
            int y = 1 + random.nextInt(BallHeightNumber-2);
            coordinate = new Coordinate(x,y);
            boolean collision = false;
            for(Coordinate s: snake){
                if(s.equals(coordinate)){
                    collision = true;
                }
            }
            for(Coordinate a: apples){
                if(a.equals(coordinate)){
                    collision = true;
                }
            }
            for(Coordinate w: walls){
                if(w.equals(coordinate)){
                    collision = true;
                }
            }
            for(Coordinate c: crossings){
                if(c.equals(coordinate)){
                    collision = true;
                }
            }
            added =!collision;
        }
        apples.add(coordinate);
    }
}
