package com.fuga;

import com.fuga.pattern.BitSetPattern;

public class Utils {
    public static final char DEFAULT_ELEMENT = 'o';
    public static final char DEFAULT_BACKGROUND = '-';

    public static boolean getCharToBoolean(char c, char background, char element) {
        if(c != background && c != element)
            throw new BitSetPattern.UnsupportedPatternCharacter();
        return c == element ? true : false;
    }

}
