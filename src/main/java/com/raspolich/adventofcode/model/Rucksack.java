package com.raspolich.adventofcode.model;

import java.util.*;

public class Rucksack {

    private final Compartment compartment1;
    private final Compartment compartment2;

    public Rucksack(String rawTypesString) {
        this.compartment1 = new Compartment(rawTypesString.substring(0, (rawTypesString.length() / 2)).toCharArray());
        this.compartment2 = new Compartment(rawTypesString.substring(rawTypesString.length() / 2).toCharArray());
    }

    public ItemType getCommonItemType() {
        for (ItemType type : compartment1.itemTypes) {
            if (compartment2.itemTypes.contains(type)) {
                return type;
            }
        }
        return  null;
    }

    public static class ItemType {

        private final char code;

        public ItemType(char code) {
            this.code = code;
        }

        public int getPriority() {
            if (String.valueOf(code).equals(String.valueOf(code).toLowerCase())) {
                return (int)code - 96;
            } else {
                return (int)code - 38;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemType itemType = (ItemType) o;
            return code == itemType.code;
        }

        @Override
        public int hashCode() {
            return Objects.hash(code);
        }
    }

    public static class Compartment {
        private final List<ItemType> itemTypes;

        public Compartment(char[] types) {
            List<ItemType> tempTypes = new ArrayList<>();
            for (char code : types) {
                tempTypes.add(new ItemType(code));
            }
            itemTypes = tempTypes;
        }
    }
}
