package com.raspolich.adventofcode.model;

import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BeaconGrid {
    private final List<Sensor> sensors = new ArrayList<>();
    private long leftBound;
    private long rightBound;

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
        updateBounds();
    }

    private void updateBounds() {
        leftBound = leftBound();
        rightBound = rightBound();
    }

    public static BeaconGrid fromInputLines(List<String> inputLines) {
        BeaconGrid grid = new BeaconGrid();

        for (String line : inputLines) {
            grid.addSensor(Sensor.fromInputLine(line));
        }

        return grid;
    }

    private long leftBound() {
        return sensors.stream().mapToLong(s -> Math.min(s.getLocation().x - s.range(), s.getClosestBeacon().x)).min().orElse(0L);
    }

    private long rightBound() {
        return sensors.stream().mapToLong(s -> Math.max(s.getLocation().x + s.range(), s.getClosestBeacon().x)).max().orElse(0L);
    }

    public int nonBeaconPositionsInRow(int row) {
        Set<Point> nonBeaconPoints = new HashSet<>();
        List<Point> beacons = sensors.stream().map(Sensor::getClosestBeacon).toList();

        for (long x = leftBound; x <= rightBound; x++) {
            for (Sensor sensor : sensors) {
                Point testPoint = new Point((int)x, row);
                if (!beacons.contains(testPoint)) {
                    if (sensor.withinRange(testPoint)) {
                        nonBeaconPoints.add(testPoint);
                    }
                }
            }
        }

        return nonBeaconPoints.size();
    }
    
    public long tuningFrequency() {
        int lengthX = 4000001;
        int lengthY = 4000001;

        for (int y = 0; y < lengthY; y++) {
            int row = y;
            List<Range> sensorSlices = sensors.stream().filter(s -> s.getSlice(row, 0, lengthX - 1) != null)
                    .map(s -> s.getSlice(row, 0, lengthX -1)).sorted(Comparator.comparingInt(Range::getStart)).toList();
            List<Range> combinedSlices = new ArrayList<>();
            for (Range slice : sensorSlices) {
                if (CollectionUtils.isEmpty(combinedSlices)) {
                    combinedSlices.add(slice);
                } else {
                    Range lastSlice = combinedSlices.get(combinedSlices.size() - 1);
                    if (lastSlice.getEnd() >= slice.getStart()) {
                        lastSlice = new Range(lastSlice.getStart(), Math.max(lastSlice.getEnd(), slice.getEnd()));
                        combinedSlices.remove(combinedSlices.size() - 1);
                        combinedSlices.add(lastSlice);
                    } else {
                        combinedSlices.add(slice);
                    }
                }
            }

            if (combinedSlices.size() > 1) {
                return ((combinedSlices.get(0).getEnd() + 1) * 4000000L) + y;
            }
        }

        return 0L;
    }
}
