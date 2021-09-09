package com.example.snakegame.Classes;

import java.util.List;

public class Walls {
    public Walls() {
    }

    public static void AddWalls1(List<Coordinate> walls, int BallWidthNumber,int BallHeightNumber) { // walls for the first level
        // top and bottom walls
        for (int x = 0; x < BallWidthNumber; x++) {
            walls.add(new Coordinate(x, 0));
            walls.add(new Coordinate(x, BallHeightNumber - 1));
        }
        // right and left walls
        for (int y = 0; y < BallHeightNumber; y++) {
            walls.add(new Coordinate(0, y));
            walls.add(new Coordinate(BallWidthNumber - 1, y));
        }
    }
    public static void AddWalls2(List<Coordinate> walls, int BallWidthNumber,int BallHeightNumber) { // walls for the second level
        for (int x = 1; x < BallWidthNumber-1; x++) {
            walls.add(new Coordinate(x, BallHeightNumber/2));
        }
        for (int y = 1; y < BallHeightNumber-1; y++) {
            walls.add(new Coordinate(BallWidthNumber/2, y));
        }
    }
    public static void AddWalls3(List<Coordinate> walls, int BallWidthNumber,int BallHeightNumber) { // walls for the third level
        // top and bottom walls
        int a = BallHeightNumber/4;
        for (int x = 1; x < BallWidthNumber/2; x++) {
            walls.add(new Coordinate(x, 1));
            walls.add(new Coordinate(x, a));
            walls.add(new Coordinate(x, 2*a));
            walls.add(new Coordinate(x, 3*a));
            walls.add(new Coordinate(x, BallHeightNumber - 2));
        }

        for (int x = BallWidthNumber/2+1 ; x < BallWidthNumber-1; x++) {
            walls.add(new Coordinate(x, 1));
            walls.add(new Coordinate(x, a));
            walls.add(new Coordinate(x, 2*a));
            walls.add(new Coordinate(x, 3*a));
            walls.add(new Coordinate(x, BallHeightNumber - 2));
        }
    }


}
