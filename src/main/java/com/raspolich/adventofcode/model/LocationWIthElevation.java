package com.raspolich.adventofcode.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class LocationWIthElevation {
    private final char elevation;
    private final Point location;
    private final Point parentLocation;
    private final List<Point> reachableNeighbors;

    public LocationWIthElevation(Point location, Point parentLocation, char[][] grid, boolean startFromEnd) {
        this.location = location;
        this.parentLocation = parentLocation;
        this.elevation = grid[location.y][location.x];
        this.reachableNeighbors = new ArrayList<>();

        if (location.y != 0) {
            Point above = new Point(location.x, location.y - 1);
            if (reachable(above, grid, startFromEnd)) {
                this.reachableNeighbors.add(above);
            }
        }
        if (location.y != grid.length - 1) {
            Point below = new Point(location.x, location.y + 1);
            if (reachable(below, grid, startFromEnd)) {
                this.reachableNeighbors.add(below);
            }
        }
        if (location.x != 0) {
            Point left = new Point(location.x - 1, location.y);
            if (reachable(left, grid, startFromEnd)) {
                this.reachableNeighbors.add(left);
            }
        }
        if (location.x != grid[0].length - 1) {
            Point right = new Point(location.x + 1, location.y);
            if (reachable(right, grid, startFromEnd)) {
                this.reachableNeighbors.add(right);
            }
        }
    }

    private boolean reachable(Point neighbor, char[][] grid, boolean startFromEnd) {
        char neighborElevation = grid[neighbor.y][neighbor.x];

        if (startFromEnd) {
            return !this.location.equals(this.parentLocation) && (this.elevation <= neighborElevation || neighborElevation + 1 == this.elevation);
        } else {
            return !this.location.equals(this.parentLocation) && (this.elevation >= neighborElevation || neighborElevation - 1 == this.elevation);
        }
    }

    public List<Point> getReachableNeighbors() {
        return reachableNeighbors;
    }
}
