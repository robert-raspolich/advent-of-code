package com.raspolich.adventofcode.model;

import java.util.Optional;

public enum RockPaperScissorsSelection {
    ROCK(1), PAPER(2), SCISSORS(3);

    private final int pointValue;
    RockPaperScissorsSelection(int pointValue) {
        this.pointValue = pointValue;
    }

    public static RockPaperScissorsSelection fromCode(String code) {
        if ("A".equalsIgnoreCase(code) || "X".equalsIgnoreCase(code)) {
            return ROCK;
        } else if ("B".equalsIgnoreCase(code) || "Y".equalsIgnoreCase(code)) {
            return PAPER;
        } else if ("C".equalsIgnoreCase(code) || "Z".equalsIgnoreCase(code)) {
            return SCISSORS;
        } else {
            return null;
        }
    }

    public static RockPaperScissorsSelection fromCodes(String elfCode, String resultCode) {
        return Optional.ofNullable(RockPaperScissorsSelection.fromCode(elfCode)).map(elfSelection -> {
            if ("X".equalsIgnoreCase(resultCode)) {
                return switch (elfSelection) {
                    case ROCK -> SCISSORS;
                    case PAPER -> ROCK;
                    case SCISSORS -> PAPER;
                };
            } else if ("Y".equalsIgnoreCase(resultCode)) {
                return elfSelection;
            } else if ("Z".equalsIgnoreCase(resultCode)) {
                return switch (elfSelection) {
                    case ROCK -> PAPER;
                    case PAPER -> SCISSORS;
                    case SCISSORS -> ROCK;
                };
            } else {
                return null;
            }
        }).orElse(null);
    }

    private boolean beats(RockPaperScissorsSelection opponentSelection) {
        return ((ROCK.equals(this) && SCISSORS.equals(opponentSelection)))
                || (SCISSORS.equals(this) && PAPER.equals(opponentSelection))
                || (PAPER.equals(this) && ROCK.equals(opponentSelection));
    }

    public int getRoundPoints(RockPaperScissorsSelection opponentSelection) {
        if (this.beats(opponentSelection)) {
            return this.pointValue + 6;
        } else if (this.equals(opponentSelection)) {
            return this.pointValue + 3;
        } else {
            return this.pointValue;
        }
    }
}
