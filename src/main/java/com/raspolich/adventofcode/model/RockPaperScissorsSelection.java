package com.raspolich.adventofcode.model;

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
        RockPaperScissorsSelection elfSelection = RockPaperScissorsSelection.fromCode(elfCode);

        if ("X".equalsIgnoreCase(resultCode)) {
            switch (elfSelection) {
                case ROCK:
                    return SCISSORS;
                case PAPER:
                    return ROCK;
                case SCISSORS:
                    return PAPER;
            }
        } else if ("Y".equalsIgnoreCase(resultCode)) {
            return elfSelection;
        } else if ("Z".equalsIgnoreCase(resultCode)) {
            switch (elfSelection) {
                case ROCK:
                    return PAPER;
                case PAPER:
                    return SCISSORS;
                case SCISSORS:
                    return ROCK;
            }
        }

        return null;
    }

    private boolean beats(RockPaperScissorsSelection opponentSelection) {
        if (((ROCK.equals(this) && SCISSORS.equals(opponentSelection)))
                || (SCISSORS.equals(this) && PAPER.equals(opponentSelection))
                || (PAPER.equals(this) && ROCK.equals(opponentSelection))) {
            return true;
        } else {
            return false;
        }
    }

    public int getRoundPoints(RockPaperScissorsSelection opponentSelection) {
        if (this.beats(opponentSelection)) {
            return this.pointValue + 6;
        } else if (this.equals(opponentSelection)) {
            return this.pointValue + 3;
        } else {
            return this.pointValue + 0;
        }
    }
}
