package com.raspolich.adventofcode.service;

import com.raspolich.adventofcode.model.ElfGroup;
import com.raspolich.adventofcode.model.PuzzleId;
import com.raspolich.adventofcode.model.RockPaperScissorsSelection;
import com.raspolich.adventofcode.model.Rucksack;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;

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
    }

    public List<String> getPuzzleInput(PuzzleId.PuzzleDay puzzleDay) {
        try {
            String filename = "daily-inputs/puzzle-" + puzzleDay.getYear() + "-" + puzzleDay.getDay() + ".txt";
            Path path = Paths.get(getClass().getClassLoader().getResource(filename).toURI());
            return Files.lines(path).toList();
        } catch(Exception e) {
            //This is just for fun.  Let's ignore it.
            return Collections.emptyList();
        }
    }

    public int getPuzzleAnswer(PuzzleId puzzleId) {
        if (getAnswersMap.containsKey(puzzleId)) {
            return getAnswersMap.get(puzzleId).get().intValue();
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
                calorieCount += Integer.valueOf(calories);
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

            totalPoints += yourSelection.getRoundPoints(opponentSelection);
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
}
