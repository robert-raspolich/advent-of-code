package com.raspolich.adventofcode.model;

import java.util.*;

public class ElfGroup {
    private final List<Set<Character>> rucksacks;

    public ElfGroup(List<String> rawList) {
        rucksacks = new ArrayList<>();
        for (String raw : rawList) {
            Set<Character> rucksack = new HashSet<>();
            for (char c : raw.toCharArray()) {
                rucksack.add(c);
            }
            rucksacks.add(rucksack);
        }
    }

    public int getBadgeTypePriority() {
        Map<Character, Integer> countMap = new HashMap<>();
        for(Set<Character> rucksack : rucksacks) {
            for(Character itemType : rucksack) {
                countMap.compute(itemType, (k, v) -> v == null ? 1 : v + 1);
            }
        }

        for (Character key : countMap.keySet()) {
            if (countMap.get(key).equals(rucksacks.size())) {
                if (String.valueOf(key).equals(String.valueOf(key).toLowerCase())) {
                    return (int)key - 96;
                } else {
                    return (int)key - 38;
                }
            }
        }

        return -1;
    }
}
