package com.raspolich.adventofcode.model;

import java.util.List;

public class PacketPair {
    private final List<Object> left;
    private final List<Object> right;

    public PacketPair(List<Object> left, List<Object> right) {
        this.left = left;
        this.right = right;
    }

    public boolean valid() {
        Boolean valid = valid(left, right);

        // Don't think it should ever be null at this point.
        return valid != null ? valid : false;
    }

    private Boolean valid(Object leftObj, Object rightObj) {
        if (leftObj instanceof Double && rightObj instanceof Double) {
            int leftInt = ((Double)leftObj).intValue();
            int rightInt = ((Double)rightObj).intValue();
            if (leftInt < rightInt) {
                return true;
            } else if (leftInt > rightInt) {
                return false;
            }
        } else if (leftObj instanceof List && rightObj instanceof List) {
            List<Object> leftList = (List<Object>)leftObj;
            List<Object> rightList = (List<Object>)rightObj;

            for (int i = 0; i < Math.max(leftList.size(), rightList.size()); i++) {
                if (i >= leftList.size()) {
                    return true;
                }
                if (i >= rightList.size()) {
                    return false;
                }

                Boolean valid = valid(leftList.get(i), rightList.get(i));
                if (valid != null) {
                    return valid;
                }
            }
        } else if (leftObj instanceof Double && rightObj instanceof List) {
            return valid(List.of(leftObj), rightObj);
        } else if (leftObj instanceof List && rightObj instanceof Double) {
            return valid(leftObj, List.of(rightObj));
        }

        return null;
    }
}
