package com.fuga;

import com.fuga.pattern.BitSetPattern;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BruteForceSpaceInvadersFinder implements SpaceInvadersFinder {
    private static final double MAX_PRECISION = 1;

    @Override
    public List<Invader> find(List<BitSetPattern> invaders, BitSetPattern radarText) {
        return find(invaders, radarText, MAX_PRECISION);
    }

    @Override
    public List<Invader> find(List<BitSetPattern> invaders, BitSetPattern radarPattern, double precision) {
        List<Invader> foundInvaders = new ArrayList<>();
        for (BitSetPattern invader : invaders)
            foundInvaders.addAll(getFoundInvadersOfType(radarPattern, invader, precision));

        return foundInvaders;
    }

    private List<Invader> getFoundInvadersOfType(BitSetPattern radarPattern, BitSetPattern invaderPattern, double precision) {
        List<Invader> foundInvaders = new ArrayList<>();
        for (int radarInd = 0; radarInd < radarPattern.getLength(); radarInd++)
            if (isInvader(radarPattern, radarInd, invaderPattern, precision))
                foundInvaders.add(getInvader(invaderPattern, radarInd));

        return foundInvaders;
    }

    public boolean isInvader(BitSetPattern radarPattern, int radarInd, BitSetPattern invaderPattern, double precision) {
        BitSet radarSlice = radarPattern.getSlice(radarInd, invaderPattern.getWidth(), invaderPattern.getHeight());
        radarSlice.xor(invaderPattern.getBitSet());
        return getPercentageOfDifferentPixels(radarSlice, invaderPattern.getLength()) <= (1 - precision);
    }

    private double getPercentageOfDifferentPixels(BitSet radarXorInvader, int invaderSize) {
        return radarXorInvader.cardinality() / (double) invaderSize;
    }

    private Invader getInvader(BitSetPattern invaderPattern, int coord) {
        return new Invader(invaderPattern, coord);
    }


}
