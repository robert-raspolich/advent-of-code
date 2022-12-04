package com.raspolich.adventofcode.model;

public class RangePair {
    private final Range rangeOne;
    private final Range rangeTwo;

    public RangePair(String raw) {
        //Leaving out input validation for now. Assume AofC is giving good input :)
        String[] parts = raw.split(",");
        this.rangeOne = new Range(parts[0]);
        this.rangeTwo = new Range(parts[1]);
    }

    private boolean oneContainsTwo() {
        return rangeOne.getStart() <= rangeTwo.getStart() && rangeOne.getEnd() >= rangeTwo.getEnd();
    }

    private boolean twoContainsOne() {
        return rangeTwo.getStart() <= rangeOne.getStart() && rangeTwo.getEnd() >= rangeOne.getEnd();
    }

    public boolean hasFullOverlap() {
        return oneContainsTwo() || twoContainsOne();
    }

    public boolean hasOverlap() {
        return rangeOne.getStart() <= rangeTwo.getEnd() && rangeOne.getEnd() >= rangeTwo.getStart();
    }
}
