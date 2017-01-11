package com.fuga.pattern;

import com.fuga.Utils;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class PatternFactory {
    private static final Map<String, BitSetPattern> cache = new HashMap<>();
    private final char background;
    private final char element;

    public PatternFactory() {
        this(Utils.DEFAULT_BACKGROUND, Utils.DEFAULT_ELEMENT);
    }

    public PatternFactory(char background, char element) {
        this.background = background;
        this.element = element;
    }

    public BitSetPattern create(String textRepresentation) {
        if (textRepresentation == null)
            return new NullPattern(textRepresentation, new BitSet());
        cache.putIfAbsent(textRepresentation, getPattern(textRepresentation));

        return cache.get(textRepresentation);
    }

    private BitSetPattern getPattern(String textRepresentation) {
        BitSet bitSet = toBitSet(textRepresentation);
        return new BitSetPattern(textRepresentation, bitSet);
    }

    private BitSet toBitSet(String text) {
        text = text.replace(System.lineSeparator(), "");
        BitSet bitSet = new BitSet(text.length());

        for (int i = 0; i < text.toCharArray().length; i++)
            bitSet.set(i, Utils.getCharToBoolean(text.charAt(i), background, element));

        return bitSet;
    }

}
