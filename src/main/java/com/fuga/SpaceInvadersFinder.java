package com.fuga;

import com.fuga.pattern.BitSetPattern;
import com.fuga.pattern.Pattern;

import java.util.List;

public interface SpaceInvadersFinder {
    List<Invader> find(List<BitSetPattern> invaders, BitSetPattern radarText);

    List<Invader> find(List<BitSetPattern> invaders, BitSetPattern radarText, double precision);
}
