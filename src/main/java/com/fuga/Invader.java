package com.fuga;

import com.fuga.pattern.BitSetPattern;

public class Invader {
    private int coord;
    private BitSetPattern pattern;

    public Invader(BitSetPattern pattern, int coord) {
        this.pattern = pattern;
        this.coord = coord;
    }

    public int getHeight() {
        return pattern.getHeight();
    }

    public int getWidth() {
        return pattern.getWidth();
    }

    public int getCoord() {
        return coord;
    }
}


