package com.raspolich.adventofcode.model;

import java.util.*;

public class HandheldDevice {
    private final char[] data;
    private final int delimiterSize;

    public HandheldDevice(char[] data, int delimiterSize) {
        this.data = data;
        this.delimiterSize = delimiterSize;
    }

    public int getDelimiterPosition(int occurrence) {
        var delimiterQueue = new LinkedList<>();
        var delimiterPositions = new ArrayList<Integer>();

        for (int i = 0; i < data.length; i++) {
            if (delimiterQueue.size() == delimiterSize) {
                var distinctSize = delimiterQueue.stream().distinct().toList().size();
                if (delimiterQueue.size() == distinctSize) {
                    delimiterPositions.add(i);
                }
            }

            delimiterQueue.add(data[i]);

            if (delimiterQueue.size() > delimiterSize) {
                delimiterQueue.remove();
            }
        }

        return delimiterPositions.get(occurrence - 1);
    }
}
