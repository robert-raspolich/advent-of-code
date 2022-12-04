package com.raspolich.adventofcode.model;

import java.util.Objects;

public class PuzzleId {

    public enum PuzzleNumber {
        ONE(1), TWO(2);

        private final int value;

        PuzzleNumber(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static PuzzleNumber fromInt(int value) {
            for (PuzzleNumber puzzleNumber : PuzzleNumber.values()) {
                if (puzzleNumber.getValue() == value) {
                    return puzzleNumber;
                }
            }

            return null;
        }
    }

    private final PuzzleDay puzzleDay;
    private final PuzzleNumber puzzleNumber;

    public PuzzleId(int year, int day, PuzzleNumber puzzleNumber) {
        this.puzzleDay = new PuzzleDay(year, day);
        this.puzzleNumber = puzzleNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleId puzzleId = (PuzzleId) o;
        return puzzleDay.equals(puzzleId.puzzleDay) && puzzleNumber == puzzleId.puzzleNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(puzzleDay, puzzleNumber);
    }

    public record PuzzleDay(int year, int day) {

        @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PuzzleDay puzzleDay = (PuzzleDay) o;
                return year == puzzleDay.year && day == puzzleDay.day;
            }

    }
}
