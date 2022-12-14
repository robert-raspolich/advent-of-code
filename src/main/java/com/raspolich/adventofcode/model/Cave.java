package com.raspolich.adventofcode.model;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cave {
    private enum Material { ROCK, SAND }

    private final Map<Point, Material> grid;

    private final boolean hasFloor;
    private final int lowestRockIndex;

    private Cave(Map<Point, Material> grid, boolean hasFloor) {
        this.grid = grid;
        this.hasFloor = hasFloor;
        this.lowestRockIndex = grid.keySet().stream().filter(p -> grid.get(p).equals(Material.ROCK)).mapToInt(p -> p.y).max().orElse(0);
    }

    public int fillWithSand(Point start) {
        int unitsOfSandCollected = 0;
        boolean fellIntoAbyss = false;
        boolean full = false;
        Point location = new Point(start.x, start.y);

        while (!fellIntoAbyss && !full) {
            if (hasFloor && location.y == lowestRockIndex + 1) {
                // landed on floor
                grid.put(new Point(location.x, location.y), Material.SAND);
                unitsOfSandCollected++;
                location = new Point(start.x, start.y);
            } else if (location.y > lowestRockIndex) {
                fellIntoAbyss = true;
            } else if (!grid.containsKey(new Point(location.x, location.y + 1))) {
                // straight down is empty, move there
                location.move(location.x, location.y + 1);
            } else if (!grid.containsKey(new Point(location.x - 1, location.y + 1))) {
                // down and to the left is empty, move there
                location.move(location.x - 1, location.y + 1);
            } else if (!grid.containsKey(new Point(location.x + 1, location.y + 1))) {
                // down and to the right is empty, move there
                location.move(location.x + 1, location.y + 1);
            } else {
                // came to rest, start next unit of sand at start point
                grid.put(new Point(location.x, location.y), Material.SAND);
                unitsOfSandCollected++;

                if (location.equals(start)) {
                    full = true;
                }

                location = new Point(start.x, start.y);
            }
        }

        return unitsOfSandCollected;
    }

    public static Cave fromInputLines(List<String> inputLines, boolean hasFloor) {
        Map<Point, Material> grid = new HashMap<>();

        for (String line : inputLines) {
            List<Point> points = Arrays.stream(line.split(" -> "))
                    .map(p -> new Point(Integer.parseInt(p.split(",")[0]), Integer.parseInt(p.split(",")[1])))
                    .toList();

            for (int i = 1; i < points.size(); i++) {
                drawRockLine(grid, new Line(points.get(i - 1), points.get(i)));
            }
        }

        return new Cave(grid, hasFloor);
    }

    private static void drawRockLine(Map<Point, Material> grid, Line line) {
        if (line.start.x < line.end.x) {
            for (int i = line.start.x; i <= line.end.x; i++) {
                grid.put(new Point(i, line.start.y), Material.ROCK);
            }
        } else if (line.start.x > line.end.x) {
            for (int i = line.start.x; i >= line.end.x; i--) {
                grid.put(new Point(i, line.start.y), Material.ROCK);
            }
        } else if (line.start.y < line.end.y) {
            for (int i = line.start.y; i <= line.end.y; i++) {
                grid.put(new Point(line.start.x, i), Material.ROCK);
            }
        } else if (line.start.y > line.end.y) {
            for (int i = line.start.y; i >= line.end.y; i--) {
                grid.put(new Point(line.start.x, i), Material.ROCK);
            }
        }
    }

    private record Line(Point start, Point end) {
    }
}
