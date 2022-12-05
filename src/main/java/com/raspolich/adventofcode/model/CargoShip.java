package com.raspolich.adventofcode.model;

import java.util.*;

public class CargoShip {

    private final Map<Integer, Stack<String>> stacks;

    public CargoShip(List<String> raw) {
        stacks = new HashMap<>();
        Map<Integer, Stack<String>> tempStacks = new HashMap<>();

        for (String line : raw) {
            char[] chars = line.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if ("[".equals(String.valueOf(chars[i]))) {
                    int stackNumber = getStackNumber(i + 1);
                    if (!tempStacks.containsKey(stackNumber)) {
                        tempStacks.put(stackNumber, new Stack<>());
                    }
                    tempStacks.get(stackNumber).push(String.valueOf(chars[i + 1]));
                }
            }
        }

        for (int stackNumber : tempStacks.keySet()) {
            Stack<String> tempStack = tempStacks.get(stackNumber);
            Stack<String> stack = new Stack<>();

            while(!tempStack.isEmpty()) {
                stack.push(tempStack.pop());
            }

            stacks.put(stackNumber, stack);
        }
    }

    public void rearrange(List<RearrangementProcedure> procedures) {
        for (var procedure : procedures) {
            if (procedure.isMultiMove()) {
                var fromStack = stacks.get(procedure.getFrom());
                var toStack = stacks.get(procedure.getTo());
                var cratesToMove = fromStack.subList(fromStack.size() - procedure.getMove(), fromStack.size());
                toStack.addAll(cratesToMove);
                for (int i = 0; i < procedure.getMove(); i++) {
                    fromStack.pop();
                }
            } else {
                for (int i = 0; i < procedure.getMove(); i++) {
                    stacks.get(procedure.getTo()).push(stacks.get(procedure.getFrom()).pop());
                }
            }
        }

    }

    public List<String> getTopCrates() {
        List<String> topCrates = new ArrayList<>();

        for (var stackNumber : stacks.keySet()) {
            var stack = stacks.get(stackNumber);
            if (stack.isEmpty()) {
                topCrates.add(" ");
            } else {
                topCrates.add(stack.lastElement());
            }
        }

        return topCrates;
    }

    private static int getStackNumber(int rawPosition) {
        if (rawPosition == 1) {
            return 1;
        } else {
            return (rawPosition / 4) + 1;
        }
    }
}
