package com.fuga.pattern;

public interface Pattern {
    String getText();

    int getWidth();

    int getHeight();

    int getLength();

    boolean isInvader(int radarInd, Pattern invaderPattern, double precision);
}
