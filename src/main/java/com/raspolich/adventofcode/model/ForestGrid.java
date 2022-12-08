package com.raspolich.adventofcode.model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class ForestGrid {
    private final int[][] grid;

    public ForestGrid(int[][] grid) {
        this.grid = grid;
    }

    public int visibleTreeCount() {
        Set<Point> visibleTrees = new HashSet<>();
        int gridWidth = grid[0].length;

        for (int row = 0; row < grid.length; row++) {
            //Look left
            int maxHeight = 0;
            for (int column = 0; column < gridWidth; column++) {
                int height = grid[row][column];
                if (column == 0 || height > maxHeight) {
                    maxHeight = height;
                    visibleTrees.add(new Point(column, row));
                }
            }

            //Look right
            maxHeight = 0;
            for (int column = gridWidth - 1; column >= 0; column--) {
                int height = grid[row][column];
                if (column == gridWidth - 1 || height > maxHeight) {
                    maxHeight = height;
                    visibleTrees.add(new Point(column, row));
                }
            }
        }

        for (int column = 0; column < gridWidth; column++) {
            //Look down
            int maxHeight = 0;
            for (int row = 0; row < grid.length; row++) {
                int height = grid[row][column];
                if (row == 0 || height > maxHeight) {
                    maxHeight = height;
                    visibleTrees.add(new Point(column, row));
                }
            }

            //Look up
            maxHeight = 0;
            for (int row = grid.length - 1; row >= 0; row--) {
                int height = grid[row][column];
                if (row == grid.length - 1 || height > maxHeight) {
                    maxHeight = height;
                    visibleTrees.add(new Point(column, row));
                }
            }
        }

        return visibleTrees.size();
    }

    public int getMaxScenicScore() {
        int maxScore = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                int score = getScenicScore(row, column, grid[row][column]);
                if (score > maxScore) {
                    maxScore = score;
                }
            }
        }

        return maxScore;
    }

    private int getScenicScore(int selectedRow, int selectedColumn, int selectedHeight) {
        int gridWidth = grid[0].length;
        int gridHeight = grid.length;

        int treesLeft = 0;
        for (int column = selectedColumn - 1; column >= 0; column--) {
            int height = grid[selectedRow][column];
            if (column == selectedColumn - 1 || selectedHeight >= height) {
                treesLeft++;
                if (selectedHeight == height) {
                    break;
                }
            } else {
                break;
            }
        }

        int treesUp = 0;
        for (int row = selectedRow - 1; row >= 0; row--) {
            int height = grid[row][selectedColumn];
            if (row == selectedRow - 1 || selectedHeight >= height) {
                treesUp++;
                if (selectedHeight == height) {
                    break;
                }
            } else {
                break;
            }
        }

        int treesRight = 0;
        for (int column = selectedColumn + 1; column < gridWidth; column++) {
            int height = grid[selectedRow][column];
            if (column == selectedColumn + 1 || selectedHeight >= height) {
                treesRight++;
                if (selectedHeight == height) {
                    break;
                }
            } else {
                break;
            }
        }

        int treesDown = 0;
        for (int row = selectedRow + 1; row < gridHeight; row++) {
            int height = grid[row][selectedColumn];
            if (row == selectedRow + 1 || selectedHeight >= height) {
                treesDown++;
                if (selectedHeight == height) {
                    break;
                }
            } else {
                break;
            }
        }

        return treesLeft * treesUp * treesRight * treesDown;
    }
}
