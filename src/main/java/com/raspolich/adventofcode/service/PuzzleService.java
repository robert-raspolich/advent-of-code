package com.raspolich.adventofcode.service;

import com.raspolich.adventofcode.model.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.awt.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
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

        getAnswersMap.put(new PuzzleId(2022, 7, ONE), () -> getPuzzleAnswer2022Day7(ONE));
        getAnswersMap.put(new PuzzleId(2022, 7, TWO), () -> getPuzzleAnswer2022Day7(TWO));

        getAnswersMap.put(new PuzzleId(2022, 8, ONE), () -> getPuzzleAnswer2022Day8(ONE));
        getAnswersMap.put(new PuzzleId(2022, 8, TWO), () -> getPuzzleAnswer2022Day8(TWO));

        getAnswersMap.put(new PuzzleId(2022, 9, ONE), () -> getPuzzleAnswer2022Day9(ONE));
        getAnswersMap.put(new PuzzleId(2022, 9, TWO), () -> getPuzzleAnswer2022Day9(TWO));

        getAnswersMap.put(new PuzzleId(2022, 10, ONE), () -> getPuzzleAnswer2022Day10(ONE));
        getAnswersMap.put(new PuzzleId(2022, 10, TWO), () -> getPuzzleAnswer2022Day10(TWO));

        getAnswersMap.put(new PuzzleId(2022, 11, ONE), () -> getPuzzleAnswer2022Day11(ONE));
        getAnswersMap.put(new PuzzleId(2022, 11, TWO), () -> getPuzzleAnswer2022Day11(TWO));

        getAnswersMap.put(new PuzzleId(2022, 12, ONE), () -> getPuzzleAnswer2022Day12(ONE));
        getAnswersMap.put(new PuzzleId(2022, 12, TWO), () -> getPuzzleAnswer2022Day12(TWO));
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

    private String getPuzzleAnswer2022Day7(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 7));
        DeviceDirectory rootDirectory = new DeviceDirectory("/");
        DeviceDirectory currentDirectory = rootDirectory;

        for (String line : inputLines) {
            String[] parts = line.split(" ");

            if ("$".equals(parts[0])) {
                if ("cd".equals(parts[1])) {
                    if ("/".equals(parts[2])) {
                        currentDirectory = rootDirectory;
                    } else if ("..".equals(parts[2])) {
                        currentDirectory = currentDirectory.getParentDirectory();
                    } else {
                        currentDirectory = currentDirectory.getSubDirectory(parts[2]);
                    }
                }
            } else if ("dir".equals(parts[0])) {
                currentDirectory.addDirectory(new DeviceDirectory(parts[1], currentDirectory));
            } else {
                currentDirectory.addFile(new DeviceFile(parts[1], Integer.parseInt(parts[0])));
            }
        }

        if (ONE.equals(puzzleNumber)) {
            int filteredSize = DeviceDirectory.allDirectories().stream().map(DeviceDirectory::size).filter(size -> size <= 100000).reduce(0, Integer::sum);
            return String.valueOf(filteredSize);
        } else {
            int spaceRequired = 30_000_000 - (70_000_000 - rootDirectory.size());
            int minSize = DeviceDirectory.allDirectories().stream()
                    .map(DeviceDirectory::size)
                    .filter(size -> size >= spaceRequired)
                    .min(Integer::compare).orElse(0);
            return String.valueOf(minSize);
        }
    }

    private String getPuzzleAnswer2022Day8(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 8));
        int[][] grid = new int[inputLines.size()][inputLines.get(0).length()];

        for (int i = 0; i < inputLines.size(); i++) {
            for (int j = 0; j < inputLines.get(0).length(); j++) {
                grid[i][j] = Integer.parseInt(inputLines.get(i).substring(j, j + 1));
            }
        }

        ForestGrid forest = new ForestGrid(grid);

        if (ONE.equals(puzzleNumber)) {
            return String.valueOf(forest.visibleTreeCount());
        } else {
            return String.valueOf(forest.getMaxScenicScore());
        }
    }

    private String getPuzzleAnswer2022Day9(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 9));
        Rope rope = new Rope(ONE.equals(puzzleNumber) ? 2 : 10);

        inputLines.forEach(line -> {
            String[] parts = line.split(" ");
            rope.move(Rope.Direction.fromCode(parts[0]), Integer.parseInt(parts[1]));
        });

        return String.valueOf(rope.visitedByTailCount());
    }

    private String getPuzzleAnswer2022Day10(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 10));

        //TODO: refactor into CRT class
        if (ONE.equals(puzzleNumber)) {
            int x = 1;
            int currentCycle = 1;
            int firstCycleCheck = 20;
            int lastCycleCheck = 220;
            int cycleCheckIncrement = 40;
            int totalSignalStrength = 0;

            for (String line : inputLines) {
                if (line.equals("noop")) {
                    if ((currentCycle - firstCycleCheck) % cycleCheckIncrement == 0 && currentCycle <= lastCycleCheck) {
                        totalSignalStrength += currentCycle * x;
                    }
                    currentCycle++;
                } else if (line.startsWith("addx")) {
                    int value = Integer.parseInt(line.split(" ")[1]);

                    if ((currentCycle - firstCycleCheck) % cycleCheckIncrement == 0 && currentCycle <= lastCycleCheck) {
                        totalSignalStrength += currentCycle * x;
                    }

                    currentCycle++;

                    if ((currentCycle - firstCycleCheck) % cycleCheckIncrement == 0 && currentCycle <= lastCycleCheck) {
                        totalSignalStrength += currentCycle * x;
                    }

                    currentCycle++;
                    x += value;
                }
            }

            return String.valueOf(totalSignalStrength);
        } else {
            CRT crt = new CRT(inputLines);
            return crt.draw();
        }
    }

    private String getPuzzleAnswer2022Day11(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 11));

        Monkey.initializeInstances(inputLines);
        int rounds = ONE.equals(puzzleNumber) ? 20 : 10000;
        Monkey.setWorryDivisor(ONE.equals(puzzleNumber) ? 3 : 1);

        for (int i = 0; i < rounds; i++) {
            Monkey.doRound();
        }

        return String.valueOf(Monkey.getMonkeyBusiness());
    }

    private String getPuzzleAnswer2022Day12(PuzzleId.PuzzleNumber puzzleNumber) {
        List<String> inputLines = getPuzzleInput(new PuzzleId.PuzzleDay(2022, 12));

        char[][] grid = new char[inputLines.size()][inputLines.get(0).length()];
        Point start = null;
        List<Point> possibleEnds = new ArrayList<>();

        for (int i = 0; i < inputLines.size(); i++) {
            char[] line = inputLines.get(i).toCharArray();
            for (int j = 0; j < line.length; j++) {
                char elevation = line[j];

                if (ONE.equals(puzzleNumber)) {
                    if (elevation == 'S') {
                        start = new Point(j, i);
                        grid[i][j] = 'a';
                    } else if (elevation == 'E') {
                        possibleEnds.add(new Point(j, i));
                        grid[i][j] = 'z';
                    } else {
                        grid[i][j] = elevation;
                    }
                } else {
                    if (elevation == 'E') {
                        start = new Point(j, i);
                        grid[i][j] = 'z';
                    } else if (elevation == 'S' || elevation == 'a') {
                        possibleEnds.add(new Point(j, i));
                        grid[i][j] = 'a';
                    } else {
                        grid[i][j] = elevation;
                    }
                }
            }
        }

        int moves = 0;
        boolean startFromEnd = TWO.equals(puzzleNumber);
        LocationWIthElevation startLocation = new LocationWIthElevation(start, null, grid, startFromEnd);
        Map<Point, List<Point>> currentCandidates = new HashMap<>();
        currentCandidates.put(start, startLocation.getReachableNeighbors());

        while (!CollectionUtils.isEmpty(currentCandidates)) {
            moves++;
            Map<Point, List<Point>> nextCandidates = new HashMap<>();

            for (Point parentLocation : currentCandidates.keySet()) {
                for (Point location : currentCandidates.get(parentLocation)) {
                    if (possibleEnds.contains(location)) {
                        return String.valueOf(moves);
                    }

                    List<Point> children = new LocationWIthElevation(location, parentLocation, grid, startFromEnd).getReachableNeighbors();
                    if (!CollectionUtils.isEmpty(children)) {
                        nextCandidates.put(location, children);
                    }
                }
            }

            currentCandidates = nextCandidates;
        }


        return "No path found";
    }
}
