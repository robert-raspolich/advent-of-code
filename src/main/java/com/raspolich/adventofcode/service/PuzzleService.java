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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.raspolich.adventofcode.model.PuzzleId.PuzzleNumber.ONE;
import static com.raspolich.adventofcode.model.PuzzleId.PuzzleNumber.TWO;

@Service
public class PuzzleService {

    private final Map<PuzzleId, Supplier<String>> getAnswersMap;

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

        getAnswersMap.put(new PuzzleId(2022, 5, ONE), () -> getPuzzleAnswer2022Day5(ONE));
        getAnswersMap.put(new PuzzleId(2022, 5, TWO), () -> getPuzzleAnswer2022Day5(TWO));

        getAnswersMap.put(new PuzzleId(2022, 6, ONE), () -> getPuzzleAnswer2022Day6(ONE));
        getAnswersMap.put(new PuzzleId(2022, 6, TWO), () -> getPuzzleAnswer2022Day6(TWO));
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

    public String getPuzzleAnswer(PuzzleId puzzleId) {
        if (getAnswersMap.containsKey(puzzleId)) {
            return String.valueOf(getAnswersMap.get(puzzleId).get());
        }

        return null;
    }

    private String getPuzzleAnswer2022Day1(PuzzleId.PuzzleNumber puzzleNumber) {
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
            return String.valueOf(Collections.max(calorieCounts));
        } else {
            calorieCounts.sort(Collections.reverseOrder());
            return String.valueOf(calorieCounts.stream().limit(3).mapToInt(Integer::intValue).sum());
        }
    }

    private String getPuzzleAnswer2022Day2(PuzzleId.PuzzleNumber puzzleNumber) {
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

            totalPoints += Optional.ofNullable(yourSelection).map(sel -> sel.getRoundPoints(opponentSelection)).orElse(0);
        }

        return String.valueOf(totalPoints);
    }

    private String getPuzzleAnswer2022Day3(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 3));
        int totalPriority = 0;

        if (ONE.equals(puzzleNumber)) {
            for (String line : inputLines) {
                Rucksack rucksack = new Rucksack(line);
                totalPriority += Optional.ofNullable(rucksack.getCommonItemType()).map(Rucksack.ItemType::getPriority).orElse(0);
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

        return String.valueOf(totalPriority);
    }

    private String getPuzzleAnswer2022Day4(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 4));

        int count = 0;
        for (String line : inputLines) {
            RangePair rangePair = new RangePair(line);
            if (ONE.equals(puzzleNumber) ? rangePair.hasFullOverlap() : rangePair.hasOverlap()) {
                count++;
            }
        }

        return String.valueOf(count);
    }

    private String getPuzzleAnswer2022Day5(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 5));
        List<String> stackLines = new ArrayList<>();
        List<String> procedureLines = new ArrayList<>();

        for (String line : inputLines) {
            if (line.contains("[")) {
                stackLines.add(line);
            } else if (line.contains("move")) {
                procedureLines.add(line);
            }
        }

        CargoShip ship = new CargoShip(stackLines);
        ship.rearrange(procedureLines.stream().map(line -> new RearrangementProcedure(line, TWO.equals(puzzleNumber))).collect(Collectors.toList()));

        StringBuilder answer = new StringBuilder();
        List<String> topCrates = ship.getTopCrates();
        topCrates.forEach(answer::append);

        return answer.toString();
    }

    private String getPuzzleAnswer2022Day6(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 6));
        char[] data = String.join("", inputLines).toCharArray();

        var device = new HandheldDevice(data, ONE.equals(puzzleNumber) ? 4 : 14);
        return String.valueOf(device.getDelimiterPosition(1));
    }
}
