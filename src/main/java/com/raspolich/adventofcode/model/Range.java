package com.raspolich.adventofcode.model;

import io.micrometer.common.util.StringUtils;

public class Range {

    private final int start;
    private final int end;

    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public Range(String raw) {
        int tempStart = 0;
        int tempEnd = 0;

        if (StringUtils.isNotBlank(raw)) {
            String[] parts = raw.split("-");
            if (parts.length == 2) {
                try {
                    tempStart = Integer.parseInt(parts[0]);
                    tempEnd = Integer.parseInt(parts[1]);

                } catch (Exception e) {
                    // Ignore, using default values from above.
                }
            }
        }

        this.start = tempStart;
        this.end = tempEnd;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
