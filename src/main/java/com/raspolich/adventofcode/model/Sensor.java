package com.raspolich.adventofcode.model;

import java.awt.Point;

public class Sensor {
    private final Point location;
    private final Point closestBeacon;

    private Sensor(Point location, Point closestBeacon) {
        this.location = location;
        this.closestBeacon = closestBeacon;
    }

    public static Sensor fromInputLine(String line) {
        String[] parts = line.replace("Sensor at x=", "")
                .replace(" y=", "")
                .replace(" closest beacon is at x=", "")
                .split(":");

        String[] sensorParts = parts[0].split(",");
        String[] beaconParts = parts[1].split(",");
        Point sensor = new Point(Integer.parseInt(sensorParts[0]), Integer.parseInt(sensorParts[1]));
        Point beacon = new Point(Integer.parseInt(beaconParts[0]), Integer.parseInt(beaconParts[1]));

        return new Sensor(sensor, beacon);
    }

    public int range() {
        return distance(closestBeacon);
    }

    public int distance(Point point) {
        return Math.abs(point.x - location.x) + Math.abs(point.y - location.y);
    }

    public boolean withinRange(Point point) {
        return distance(point) <= range();
    }

    public Integer leftBoundAtRow(int row) {
        if (row < location.y && row >= location.y - range()) {
            return location.x - (range() - (location.y - row));
        } else if (row > location.y && row <= location.y + range()) {
            return location.x - (range() - (row - location.y));
        } else if (row == this.location.y) {
            return location.x - range();
        } else {
            return null;
        }
    }

    public Integer rightBoundAtRow(int row) {
        if (row < location.y && row >= location.y - range()) {
            return location.x + (range() - (location.y - row));
        } else if (row > location.y && row <= location.y + range()) {
            return location.x + (range() - (row - location.y));
        } else if (row == this.location.y) {
            return location.x + range();
        } else {
            return null;
        }
    }

    public Range getSlice(int row, int leftBound, int rightBound) {
        Integer left = leftBoundAtRow(row);
        Integer right = rightBoundAtRow(row);
        if (left != null && right != null && left <= right && right >= leftBound) {
            return new Range(Math.max(left, leftBound), Math.min(right, rightBound));
        } else {
            return null;
        }
    }

    public Point getLocation() {
        return location;
    }

    public Point getClosestBeacon() {
        return closestBeacon;
    }
}
