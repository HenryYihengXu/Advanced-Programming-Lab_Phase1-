package com.whu.DogMaze;

import java.util.Random;

/**
 * The main program for DogMaze.
 *  Simulate the process of a dog walk in a maze.
 *  The dog chooses direction randomly.
 *  If all crosses in three directions is covered, the dog is dead.
 *  Simulate the process hundreds of times, finally get the statistic datas.
 *
 *  @author Henry Xu
 *  */
public class Maze {

    /** Size of the map. */
    private int _size = 50;
    /** Map in the maze. */
    private int[][] _map = null;
    /** the current X position of the dog. */
    private int _positionX;
    /** the current Y position of the dog. */
    private int _positionY;
    /** the length of the path that the dog has covered. */
    private int _pathLength;

    /** Constructor of Maze. */
    public Maze(int size) {
        _size = size;
        _map = new int[size][size];
        _positionX = size / 2;
        _positionY = size / 2;
        _pathLength = 0;
    }

    /** Dog moves up. */
    public void moveUp() {
        _positionY += 1;
    }

    /** Dog moves down. */
    public void moveDown() {
        _positionY -= 1;
    }

    /** Dog moves left. */
    public void moveLeft() {
        _positionX -= 1;
    }

    /** Dog moves right. */
    public void moveRight() {
        _positionX += 1;
    }

    /** Get all the available directions.
     * @return If no direction available, null.
     *          Else, a array contains directions*/
    public int[] getDirections() {
        int[] directions = {1, 1, 1, 1};
        int num = 0;
        if (_map[_positionX][_positionY + 1] == 0) {
            directions[0] = 0;
            num += 1;
        }
        if (_map[_positionX][_positionY - 1] == 0) {
            directions[1] = 0;
            num += 1;
        }
        if (_map[_positionX - 1][_positionY] == 0) {
            directions[2] = 0;
            num += 1;
        }
        if (_map[_positionX + 1][_positionY] == 0) {
            directions[3] = 0;
            num += 1;
        }
        if (num == 0) {
            return null;
        } else {
            return directions;
        }
    }

    /** @return Is the dog dead. */
    public boolean isDead() {
        return getDirections() == null;
    }

    /** @return Does the dog win. */
    public boolean isWin() {
        return _positionX == 0 || _positionX == _size - 1 || _positionY == 0 || _positionY == _size - 1;
    }

    /** Make a legal move. */
    public void move() {
        _pathLength += 1;
        _map[_positionX][_positionY] = 1;
        int[] directions = getDirections();
        int direction;
        do {
            direction = (int)(Math.random() * 4);
        } while (directions[direction] == 1);
        /* For test, show the direction made by the dog. */
        /* System.out.println(direction); */
        switch (direction) {
            case 0: moveUp(); break;
            case 1: moveDown(); break;
            case 2: moveLeft(); break;
            case 3: moveRight(); break;
            default:
        }
        /* For test, show the path of the dog. */
        /*System.out.println(_positionX + " " + _positionY);*/
    }

    /** Conduct the whole process.
     * @return the length of the pass the dog has covered */
    public int process() {
        while (!isWin() && !isDead()) {
            move();
        }
        return _pathLength;
    }

    /** Reset the maze. */
    public void reset() {
        _positionX = _size / 2;
        _positionY = _size / 2;
        _pathLength = 0;
        _map = new int[_size][_size];
    }

    public static void main(String[] args) {
    }
}
