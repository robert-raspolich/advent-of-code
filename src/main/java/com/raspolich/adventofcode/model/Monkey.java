package com.raspolich.adventofcode.model;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class Monkey {
    private static final Map<Integer, Monkey> instances = new HashMap<>();
    private static int worryDivisor = 3;

    private final List<Long> items;
    private final Operation operation;
    private final int testDivisor;
    private final int throwToOnTrue;
    private final int throwToOnFalse;

    private int inspectCount = 0;

    private Monkey(List<Long> items, Operation operation, int testDivisor, int throwToOnTrue, int throwToOnFalse) {
        this.items = items;
        this.operation = operation;
        this.testDivisor = testDivisor;
        this.throwToOnTrue = throwToOnTrue;
        this.throwToOnFalse = throwToOnFalse;
    }

    public static void initializeInstances(List<String> inputLines) {
        int monkeyNumber = 0;
        List<Long> items = null;
        Operation operation = null;
        int testDivisor = 0;
        int throwToOnTrue = 0;
        int throwToOnFalse = 0;

        for (String line : inputLines) {
            if (line.startsWith("Monkey ")) {
                monkeyNumber = Integer.parseInt(line.replace("Monkey ", "").replace(":", ""));
            } else if (line.startsWith("  Starting items: ")) {
                items = new ArrayList<>();
                for (String item : line.replace("  Starting items: ", "").split(", ")) {
                    items.add(Long.parseLong(item));
                }
            } else if (line.startsWith("  Operation: new = old ")) {
                String[] parts = line.replace("  Operation: new = old ", "").split(" ");
                operation = new Operation(parts[0], parts[1]);
            } else if (line.startsWith("  Test: divisible by ")) {
                testDivisor = Integer.parseInt(line.replace("  Test: divisible by ", ""));
            } else if (line.startsWith("    If true: throw to monkey ")) {
                throwToOnTrue = Integer.parseInt(line.replace("    If true: throw to monkey ", ""));
            } else if (line.startsWith("    If false: throw to monkey ")) {
                throwToOnFalse = Integer.parseInt(line.replace("    If false: throw to monkey ", ""));
            } else if ("|".equals(line)) {
                instances.put(monkeyNumber, new Monkey(items, operation, testDivisor, throwToOnTrue, throwToOnFalse));
            }
        }
    }

    public static void setWorryDivisor(int divisor) {
        worryDivisor = divisor;
    }

    public static void doRound() {
        for (Integer monkeyNumber : instances.keySet()) {
            instances.get(monkeyNumber).inspectItems();
        }
    }

    public static long getMonkeyBusiness() {
        List<Integer> topTwo = instances.values().stream().map(m -> m.inspectCount)
                .sorted(Comparator.comparingInt(ic -> (int) ic).reversed()).limit(2).toList();

        long monkeyBusiness = 1;
        for (int inspectCount : topTwo) {
            monkeyBusiness *= inspectCount;
        }

        return  monkeyBusiness;
    }

    public void inspectItems() {
        while (!CollectionUtils.isEmpty(items)) {
            this.inspectCount++;
            long worryLevel = this.items.remove(0);
            worryLevel = this.operation.doOperation(worryLevel);
            if (Monkey.worryDivisor != 1) {
                worryLevel = worryLevel / Monkey.worryDivisor;
            } else {
                int modulo = 1;
                for (Monkey monkey : instances.values().stream().toList()) {
                    modulo *= monkey.testDivisor;
                }
                worryLevel = worryLevel % modulo;
            }
            if (worryLevel % this.testDivisor == 0) {
                instances.get(this.throwToOnTrue).catchItem(worryLevel);
            } else {
                instances.get(this.throwToOnFalse).catchItem(worryLevel);
            }
        }
    }

    public void catchItem(long worryLevel) {
        this.items.add(worryLevel);
    }

    private record Operation(String operator, String modifier) {

        public long doOperation(long value) {
            long parsedModifier;
            if ("old".equals(modifier)) {
                parsedModifier = value;
            } else {
                parsedModifier = Long.parseLong(modifier);
            }
            return switch (this.operator) {
                case "+" -> value + parsedModifier;
                case "*" -> value * parsedModifier;
                default -> 0;
            };
        }
    }
}
