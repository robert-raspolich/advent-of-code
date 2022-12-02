package com.raspolich.adventofcode.model;

import java.util.Objects;

public class PuzzleId {

    public enum PuzzleNumber {
        ONE(1), TWO(2);

        private final int value;

        PuzzleNumber(int value) {
            this.value = value;
        }

        public static PuzzleNumber fromInt(int value) {
            if (value == 1) {
                return ONE;
            } else if (value == 2) {
                return TWO;
            } else {
                return null;
            }
        }
    }

    private final PuzzleDay puzzleDay;
    private final PuzzleNumber puzzleNumber;

    public PuzzleId(PuzzleDay puzzleDay, PuzzleNumber puzzleNumber) {
        this.puzzleDay = puzzleDay;
        this.puzzleNumber = puzzleNumber;
    }

    public PuzzleId(int year, int day, PuzzleNumber puzzleNumber) {
        this.puzzleDay = new PuzzleDay(year, day);
        this.puzzleNumber = puzzleNumber;
    }

    public PuzzleDay getPuzzleDay() {
        return puzzleDay;
    }

    public PuzzleNumber getPuzzleNumber() {
        return puzzleNumber;
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

    public static class PuzzleDay {
        private final int year;
        private final int day;

        public PuzzleDay(int year, int day) {
            this.year = year;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public int getDay() {
            return day;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PuzzleDay puzzleDay = (PuzzleDay) o;
            return year == puzzleDay.year && day == puzzleDay.day;
        }

        @Override
        public int hashCode() {
            return Objects.hash(year, day);
        }
    }
}
