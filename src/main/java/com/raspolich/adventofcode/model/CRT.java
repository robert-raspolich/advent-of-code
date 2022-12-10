package com.raspolich.adventofcode.model;

import java.util.List;

public class CRT {
    private final List<String> operations;
    int x = 1;
    int cycle = 1;
    StringBuilder output = new StringBuilder();

    public CRT(List<String> operations) {
        this.operations = operations;
    }

    public String draw() {
        output = new StringBuilder();
        cycle = 1;

        for (String line : operations) {
            int cycles = line.equals("noop") ? 1 : 2;
            for (int i = 0; i < cycles; i++) {
                runCycle();
                cycle++;
            }
            if (line.startsWith("addx")) {
                x += Integer.parseInt(line.split(" ")[1]);
            }
        }

        return output.toString();
    }

    private void runCycle() {
        if ((cycle % 40) - 1 >= x - 1 && (cycle % 40) - 1 <= x + 1) {
            output.append("#");
        } else {
            output.append(".");
        }
        if (cycle % 40 == 0) {
            output.append("<br />");
        }
    }
}
