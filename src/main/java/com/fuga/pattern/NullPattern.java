package com.fuga.pattern;

import java.util.BitSet;

public class NullPattern extends BitSetPattern {
    public NullPattern(String text, BitSet bitSet) {
        super(text, bitSet);
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public BitSet getBitSet() {
        return new BitSet();
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public BitSet getSlice(int startPoint, int sliceWidth, int sliceHeight) {
        return new BitSet();
    }

    @Override
    public int getLength() {
        return 0;
    }
}
