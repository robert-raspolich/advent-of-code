package com.raspolich.adventofcode.model;

public class RearrangementProcedure {
    private final int move;
    private final int from;
    private final int to;

    private final boolean multiMove;

    public RearrangementProcedure(String raw, boolean multiMove) {
        this.multiMove = multiMove;

        raw = raw.replace("move ", "").replace(" from ", ",").replace(" to ", ",");
        String[] parts = raw.split(",");
        move = Integer.parseInt(parts[0]);
        from = Integer.parseInt(parts[1]);
        to = Integer.parseInt(parts[2]);
    }

    public int getMove() {
        return move;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public boolean isMultiMove() {
        return multiMove;
    }
}
