package com.whu.DogMaze;

import java.util.Scanner;

/**
 * The program to get statistic data for DogMaze.
 *
 *  @author Henry Xu
 *  */

public class MazeStatisitic {
    /** Maze. */
    private Maze _maze;
    /** The size of the maze. */
    private int _size;
    /** Total length of the paths the dog covers when it wins. */
    private int _winLength;
    /** Total length of the paths the dog covers when it is dead. */
    private int _deadLength;
    /** Total length of the paths the dog covers. */
    private int _totalLenghth;
    /** Test times. */
    private int _times;
    /** Win times. */
    private int _winTime;
    /** Dead times. */
    private int _deadTime;
    /** Win rate. */
    private double _winRate;
    /** Dead rate. */
    private double _deadRate;
    /** The average length of the paths the dog covers when it wins. */
    private double _winLengthAverage;
    /** The average length of the paths the dog covers when it is dead. */
    private double _deadLengthAverage;
    /** The average length of all the paths the dog covers. */
    private double _totalLengthAverage;

    /** Constructor of MazeStatistic. */
    public MazeStatisitic(int size, int times) {
        _times = times;
        _size = size;
        _maze = new Maze(_size);
        _winLength = 0;
        _deadLength = 0;
        _totalLenghth = 0;
        _winRate = 0;
        _deadRate = 0;
        _winLengthAverage = 0;
        _deadLengthAverage = 0;
        _totalLengthAverage = 0;
    }

    /** Simulate the process many times and get the statistic data. */
    public void getData() {
        for (int i = 0; i < _times; i++) {
            int length = _maze.process();
            if (_maze.isWin()) {
                _winLength += length;
                _winTime += 1;
            } else {
                _deadLength += length;
                _deadTime += 1;
            }
            _maze.reset();
        }
        _winRate = _winTime / (double)_times;
        _deadRate = _deadTime / (double)_times;
        _totalLenghth = _winLength + _deadLength;
        if (_winTime == 0) {
            _winLengthAverage = 0;
        } else {
            _winLengthAverage = _winLength / (double)_winTime;
        }
        if (_deadTime == 0) {
            _deadLengthAverage = 0;
        } else {
            _deadLengthAverage = _deadLength / (double)_deadTime;
        }
        _totalLengthAverage = _totalLenghth / (double)_times;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入地图大小和模拟次数：");
        int size = scanner.nextInt();
        int times = scanner.nextInt();
        MazeStatisitic mazeStatisitic = new MazeStatisitic(size, times);
        mazeStatisitic.getData();
        System.out.println("地图尺寸：" + mazeStatisitic._size);
        System.out.println("试验次数：" + mazeStatisitic._times);
        System.out.println("成功次数：" + mazeStatisitic._winTime);
        System.out.println("失败次数：" + mazeStatisitic._deadTime);
        System.out.println("成功率：" + mazeStatisitic._winRate);
        System.out.println("失败率：" + mazeStatisitic._deadRate);
        System.out.println("成功总路径长度：" + mazeStatisitic._winLength);
        System.out.println("失败总路径长度：" + mazeStatisitic._deadLength);
        System.out.println("成功平均路径长度：" + mazeStatisitic._winLengthAverage);
        System.out.println("失败平均路径长度：" + mazeStatisitic._deadLengthAverage);
        System.out.println("总路径长度：" + mazeStatisitic._totalLenghth);
        System.out.println("总路径平均长度：" + mazeStatisitic._totalLengthAverage);
    }
}
