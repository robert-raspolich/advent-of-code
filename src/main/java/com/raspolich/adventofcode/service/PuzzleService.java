package com.raspolich.adventofcode.service;

import com.raspolich.adventofcode.model.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.raspolich.adventofcode.model.PuzzleId.PuzzleNumber.ONE;
import static com.raspolich.adventofcode.model.PuzzleId.PuzzleNumber.TWO;

@Service
public class PuzzleService {

    private final Map<PuzzleId, Supplier<Integer>> getAnswersMap;

    public PuzzleService() {
        getAnswersMap = new HashMap<>();

        getAnswersMap.put(new PuzzleId(2022, 1, ONE), () -> getPuzzleAnswer2022Day1(ONE));
        getAnswersMap.put(new PuzzleId(2022, 1, TWO), () -> getPuzzleAnswer2022Day1(TWO));

        getAnswersMap.put(new PuzzleId(2022, 2, ONE), () -> getPuzzleAnswer2022Day2(ONE));
        getAnswersMap.put(new PuzzleId(2022, 2, TWO), () -> getPuzzleAnswer2022Day2(TWO));

        getAnswersMap.put(new PuzzleId(2022, 3, ONE), () -> getPuzzleAnswer2022Day3(ONE));
        getAnswersMap.put(new PuzzleId(2022, 3, TWO), () -> getPuzzleAnswer2022Day3(TWO));

        getAnswersMap.put(new PuzzleId(2022, 4, ONE), () -> getPuzzleAnswer2022Day4(ONE));
        getAnswersMap.put(new PuzzleId(2022, 4, TWO), () -> getPuzzleAnswer2022Day4(TWO));
    }

    public List<String> getPuzzleInput(PuzzleId.PuzzleDay puzzleDay) {
        try {
            String filename = "daily-inputs/puzzle-" + puzzleDay.year() + "-" + puzzleDay.day() + ".txt";
            URL fileUrl = getClass().getClassLoader().getResource(filename);
            if (fileUrl != null) {
                Path path = Paths.get(fileUrl.toURI());
                try (Stream<String> lines = Files.lines(path)) {
                    return lines.toList();
                }
            }
        } catch(Exception e) {
            //This is just for fun.  Let's ignore it.
        }

        return Collections.emptyList();
    }

    public int getPuzzleAnswer(PuzzleId puzzleId) {
        if (getAnswersMap.containsKey(puzzleId)) {
            return getAnswersMap.get(puzzleId).get();
        }

        return -1;
    }

    private int getPuzzleAnswer2022Day1(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 1));
        List<Integer> calorieCounts = new ArrayList<>();

        int calorieCount = 0;
        for (String calories : inputLines) {
            if (StringUtils.isBlank(calories)) {
                calorieCounts.add(calorieCount);
                calorieCount = 0;
            } else {
                calorieCount += Integer.parseInt(calories);
            }
        }

        if (ONE.equals(puzzleNumber)) {
            return Collections.max(calorieCounts);
        } else {
            calorieCounts.sort(Collections.reverseOrder());
            return calorieCounts.stream().limit(3).mapToInt(Integer::intValue).sum();
        }
    }

    private int getPuzzleAnswer2022Day2(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 2));
        int totalPoints = 0;

        for (String round : inputLines) {
            RockPaperScissorsSelection opponentSelection;
            RockPaperScissorsSelection yourSelection;

            if (ONE.equals(puzzleNumber)) {
                opponentSelection = RockPaperScissorsSelection.fromCode(round.split(" ")[0]);
                yourSelection = RockPaperScissorsSelection.fromCode(round.split(" ")[1]);
            } else {
                opponentSelection = RockPaperScissorsSelection.fromCode(round.split(" ")[0]);
                yourSelection = RockPaperScissorsSelection.fromCodes(round.split(" ")[0], round.split(" ")[1]);
            }

            totalPoints += yourSelection != null ? yourSelection.getRoundPoints(opponentSelection) : 0;
        }

        return totalPoints;
    }

    private int getPuzzleAnswer2022Day3(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 3));
        int totalPriority = 0;

        if (ONE.equals(puzzleNumber)) {
            for (String line : inputLines) {
                Rucksack rucksack = new Rucksack(line);
                if (rucksack.getCommonItemType() != null) {
                    totalPriority += rucksack.getCommonItemType().getPriority();
                }
            }
        } else {
            List<String> group = new ArrayList<>();
            for (String line : inputLines) {
                group.add(line);
                if (group.size() == 3) {
                    totalPriority += new ElfGroup(group).getBadgeTypePriority();
                    group = new ArrayList<>();
                }
            }
        }

        return totalPriority;
    }

    private int getPuzzleAnswer2022Day4(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 4));

        int count = 0;
        for (String line : inputLines) {
            RangePair rangePair = new RangePair(line);
            if (ONE.equals(puzzleNumber) ? rangePair.hasFullOverlap() : rangePair.hasOverlap()) {
                count++;
            }
        }

        return count;
    }
}
