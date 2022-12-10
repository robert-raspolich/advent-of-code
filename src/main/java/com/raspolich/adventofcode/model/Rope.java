package com.raspolich.adventofcode.model;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Rope {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction fromCode(String code) {
            return switch (code) {
                case "U" -> UP;
                case "D" -> DOWN;
                case "L" -> LEFT;
                case "R" -> RIGHT;
                default -> null;
            };
        }
    }
    private final Point[] knots;

    private final Set<Point> visitedByTail = new HashSet<>();

    public Rope(int knotCount) {
        this.knots = new Point[knotCount];
        for (int i = 0; i < knotCount; i++) {
            this.knots[i] = new Point(0, 0);
        }
        this.visitedByTail.add(new Point(0, 0));
    }

    public int visitedByTailCount() {
        return visitedByTail.size();
    }

    public void move(Direction direction, int steps) {
        for (int i = 0; i < steps; i++) {
            moveHead(direction);
        }
    }

    private void moveHead(Direction direction) {
        Point head = knots[0];
        int x = head.x;
        int y = head.y;

        switch (direction) {
            case UP -> y -= 1;
            case DOWN -> y += 1;
            case LEFT -> x -= 1;
            case RIGHT -> x += 1;
        }

        head.setLocation(x, y);
        checkNext(0);
    }

    public void checkNext(int currentIndex) {
        if (currentIndex < knots.length) {
            if (!validDistanceToPrevious(currentIndex + 1)) {
                Point current = knots[currentIndex];
                Point next = knots[currentIndex + 1];

                if (current.x == next.x) {
                    next.setLocation(next.x, next.y + ((current.y - next.y)/ Math.abs((current.y - next.y))));
                } else if (current.y == next.y) {
                    next.setLocation(next.x + ((current.x - next.x)/ Math.abs((current.x - next.x))), next.y);
                } else {
                    next.setLocation(current.x > next.x ? next.x + 1 : next.x - 1, current.y > next.y ? next.y + 1 : next.y - 1);
                }

                if (next == tail()) {
                    visitedByTail.add(new Point(next.x, next.y));
                } else {
                    checkNext(currentIndex + 1);
                }
            }
        }
    }

    private Point tail() {
        return knots[knots.length - 1];
    }

    private boolean validDistanceToPrevious(int index) {
        return Math.abs(knots[index - 1].x - knots[index].x) <= 1
                && Math.abs(knots[index - 1].y - knots[index].y) <= 1;
    }
}
