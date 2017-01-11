package com.fuga.pattern;

import java.util.*;

public class BitSetPattern {
    private final BitSet bitSet;
    private final String text;
    private final int width;
    private final int height;

    public BitSetPattern(String text, BitSet bitSet) {
        this.text = text;
        this.bitSet = bitSet;
        this.width = calculateWidth();
        this.height = calculateHeight();
    }

    public String getText() {
        return text;
    }

    public BitSet getBitSet() {
        return bitSet;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int calculateHeight() {
        String[] lines = text.split(System.lineSeparator());
        return lines.length;
    }

    private int calculateWidth() {
        String[] lines = text.split(System.lineSeparator());
        OptionalInt maxOptional = Arrays.stream(lines).mapToInt(l -> l.length()).max();
        return maxOptional.isPresent() ? maxOptional.getAsInt() : 0;
    }

    public BitSet getSlice(int startPoint, int sliceWidth, int sliceHeight) {
        BitSet slice = new BitSet(sliceWidth * sliceHeight);
        for (int i = startPoint, sliceInd = 0; sliceInd < sliceWidth * sliceHeight; i++, sliceInd++) {
            if (isNextRow(sliceWidth, sliceInd))
                i += width - sliceWidth;
            slice.set(sliceInd, bitSet.get(i));
        }

        return slice;
    }

    private boolean isNextRow(int sliceWidth, int sliceInd) {
        return sliceInd != 0 && sliceInd % sliceWidth == 0;
    }

    public int getLength() {
        return width * height;
    }

    public static class UnsupportedPatternCharacter extends RuntimeException {
    }
}
