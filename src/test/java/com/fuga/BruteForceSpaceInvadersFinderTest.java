package com.fuga;

import com.fuga.pattern.BitSetPattern;
import com.fuga.pattern.Pattern;
import com.fuga.pattern.PatternFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class BruteForceSpaceInvadersFinderTest {
    private SpaceInvadersFinder finder;
    private PatternFactory patternFactory;

    @Before
    public void setUp() throws Exception {
        finder = new BruteForceSpaceInvadersFinder();
        patternFactory = new PatternFactory();
    }

    @Test
    public void whenRadarInputIsNull_ShouldReturnFindInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = patternFactory.create(null);

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(0));
    }

    @Test
    public void whenRadarInputIsEmpty_ShouldReturnFindInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = patternFactory.create("");

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(0));
    }

    @Test
    public void whenSpaceInvaderListIsEmpty_ShouldFindZeroInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList();
        BitSetPattern input = getSpaceInvader1();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(0));
    }

    @Test
    public void whenRadarInputIsSameAsInvader1_shouldFindSingleSpaceInvader1AtPosition0() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getSpaceInvader1();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(1));
        assertThat(invaders.get(0).getCoord(), equalTo(0));
    }

    @Test
    public void whenRadarInputIsSameAsInvader2_shouldFindSingleSpaceInvader2AtPosition0() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader2());
        BitSetPattern input = getSpaceInvader2();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(1));
        assertThat(invaders.get(0).getCoord(), equalTo(0));
    }

    @Test
    public void whenRadarInputContainsInvaderStartingAtPosition1AndOtherSymbols_shouldFindSingleSpaceInvaderAtPosition1() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getInputWithSpaceInvader1WithFewOtherChars();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(1));
        assertThat(invaders.get(0).getCoord(), equalTo(1));
    }

    @Test
    public void whenRadarInputContainsInvaderSurroundedByOtherSymbols_shouldFindSingleSpaceInvader() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getInputWithSpaceInvader1SurroundedByOtherChars();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(1));
        assertThat(invaders.get(0).getCoord(), equalTo(17));
    }

    @Test
    public void whenRadarInputDoesNotContainInvader_shouldNotFindSpaceInvader() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getInputWithoutInvader();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(0));
    }

    @Test
    public void whenRadarInputContainsTwoSameInvadersVertical_shouldFindTwoInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getInputWith2SpaceInvaders1Vertical();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(2));
        assertThat(invaders.get(0).getCoord(), equalTo(0));
        assertThat(invaders.get(1).getCoord(), equalTo(getInvader1Length()));
    }

    private int getInvader1Length() {
        return getSpaceInvader1().getText().replaceAll(System.lineSeparator(), "").length();
    }

    @Test
    public void whenRadarInputContainsTwoSameInvadersHorizontal_shouldFindTwoInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getInputWith2SpaceInvaders1Horizontal();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(2));
    }

    @Test
    public void whenInputContainsTwoDifferentInvaders_shouldFindTwoInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1(), getSpaceInvader2());
        BitSetPattern input = getInputWithInvader1And2();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(2));
    }

    @Test
    public void whenRadarInputIsIn10percentDifferentThanInvader1AndPrecisionIs90percent_shouldFindSingleSpaceInvader1AtPosition0() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getSpaceInvader1With10PercentDifferences();

        List<Invader> invaders = finder.find(spaceInvaders, input, 0.9);

        assertThat(invaders.size(), equalTo(1));
        assertThat(invaders.get(0).getCoord(), equalTo(0));
    }

    @Test
    public void whenRadarInputIsIn10percentDifferentThanInvader2AndPrecisionIs90percent_shouldFindSingleSpaceInvader2AtPosition0() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader2());
        BitSetPattern input = getSpaceInvader2With10PercentDifferences();

        List<Invader> invaders = finder.find(spaceInvaders, input, 0.9);

        assertThat(invaders.size(), equalTo(1));
        assertThat(invaders.get(0).getCoord(), equalTo(0));
    }

    @Test
    public void whenRadarInputIsJustAbove10percentDifferentThanInvader1AndPrecisionIs90percent_shouldNotFindAnyInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getSpaceInvader1WithJustAbove10PercentDifferences(getSpaceInvader1(), 0.91);

        List<Invader> invaders = finder.find(spaceInvaders, input, 0.9);

        assertThat(invaders.size(), equalTo(0));
    }

    @Test
    public void whenRadarInputIsIn20percentDifferentThanInvader1AndPrecisionIs90percent_shouldNotFindAnyInvader() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getSpaceInvader1With20PercentDifferences();

        List<Invader> invaders = finder.find(spaceInvaders, input, 0.9);

        assertThat(invaders.size(), equalTo(0));
    }

    @Test
    public void whenExampleRadarInputAndPrecision1_shouldNotFindAnyInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1(), getSpaceInvader2());
        BitSetPattern input = getInput();

        List<Invader> invaders = finder.find(spaceInvaders, input, 1);

        assertThat(invaders.size(), equalTo(0));
    }

    @Test
    public void whenExampleRadarInputAndPrecision90percent_shouldNotFindAnyInvaders() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1(), getSpaceInvader2());
        BitSetPattern input = getInput();

        List<Invader> invaders = finder.find(spaceInvaders, input, 0.7);

        assertThat(invaders.size(), equalTo(0));
    }

    @Test
    public void whenHalfOfInvaderIsOnRadar_invaderShouldBeFound() throws Exception {
        List<BitSetPattern> spaceInvaders = Arrays.asList(getSpaceInvader1());
        BitSetPattern input = getInputRadarWithHalfOfInvader1();

        List<Invader> invaders = finder.find(spaceInvaders, input);

        assertThat(invaders.size(), equalTo(1));
    }

    private BitSetPattern getInputRadarWithHalfOfInvader1() {
        return patternFactory.create(String.format("--o-----o--%n" +
                                                   "---o---o---%n" +
                                                   "--ooooooo--%n" +
                                                   "-oo-ooo-oo-%n"));
    }

    private BitSetPattern getSpaceInvader1WithJustAbove10PercentDifferences(BitSetPattern input, double precision) {
       /* int invaderLen = input.length();
        int numberOfDifferentPixels = (int) Math.floor(1 - precision);
        List<Integer> toChangeIndexes = new ArrayList<>();

        for(int i=0;i<numberOfDifferentPixels;i++)
            toChangeIndexes.add((int) (Math.random() * invaderLen));

        String result = "";

        for(int i=0;i<invaderLen;i++) {
            if(toChangeIndexes.contains(i))
                result += input.charAt(i) == 'o' ? '-' : 'o';
            else
                result += input.charAt(i);
        }

        return result;*/

        return patternFactory.create(String.format("o-------o-o%n" +
                                                 "---o---o---%n" +
                                                 "--ooooo-o--%n" +
                                                 "-oo-ooo-oo-%n" +
                                                 "oo---oooooo%n" +
                                                 "o-ooooooo-o%n" +
                                                 "o-------o-o%n" +
                                                 "o--oo-oo---"));
    }

    private BitSetPattern getSpaceInvader1With20PercentDifferences() {
        return patternFactory.create(String.format("o-------o-o%n" +
                             "---o---o--o%n" +
                             "--ooooo-o--%n" +
                             "-oo-ooo-oo-%n" +
                             "oo---oooooo%n" +
                             "o-ooo--oo-o%n" +
                             "o-------o-o%n" +
                             "o----------"));
    }

    private BitSetPattern getSpaceInvader1With10PercentDifferences() {
        return patternFactory.create(String.format("o-------o--%n" +
                             "---o---o---%n" +
                             "--ooooo-o--%n" +
                             "-oo-ooo-oo-%n" +
                             "oo---oooooo%n" +
                             "o-ooooooo-o%n" +
                             "o-------o-o%n" +
                             "o--oo-oo---"));
    }

    private BitSetPattern getInputWithInvader1And2() {
        return patternFactory.create(getSpaceInvader1().getText() + System.lineSeparator() + getSpaceInvader2Filled(3));
    }

    private String getSpaceInvader2Filled(int numberOfPixelsToFill) {
        String filledInvader2 = "";
        for (String line : getSpaceInvader2().getText().split(System.lineSeparator()))
            filledInvader2 += String.format("%s%s%n", line, Stream.generate(() -> "-").limit(numberOfPixelsToFill).collect(joining()));

        return filledInvader2;
    }

    private BitSetPattern getInput() {
        return patternFactory.create(String.format("----o--oo----o--ooo--ooo---------o---oo-o----oo---o--o---------o----o------o----------------o--o--o-%n" +
                             "--o-o-----oooooooo-oooooo-------o----o------ooo-o---o--o----o------o--o---ooo-----o--oo-o------o----%n" +
                             "--o--------oo-ooo-oo-oo-oo------------------ooooo-----o-----o------o---o--o--o-o-o------o----o-o-o--%n" +
                             "-------o--oooooo--o-oo-o--o-o-----oo--o-o-oo--o-oo-oo-o--------o-----o------o-ooooo---o--o--o-------%n" +
                             "------o---o-ooo-ooo----o------o-------o---oo-ooooo-o------o----o--------o-oo--ooo-oo-------------o-o%n" +
                             "-o--o-----o-o---o-ooooo-o-------o----o---------o-----o-oo-----------oo----ooooooo-ooo-oo------------%n" +
                             "o-------------ooooo-o--o--o--o-------o--o-oo-oo-o-o-o----o-------------o--oooo--ooo-o----o-----o--o-%n" +
                             "--o-------------------------oo---------oo-o-o--ooo----o-----o--o--o----o--o-o-----o-o------o-o------%n" +
                             "-------------------o-----------------o--o---------------o--------o--oo-o-----oo-oo---o--o---o-----oo%n" +
                             "----------o----------o------------------o--o----o--o-o------------oo------o--o-o---o-----o----------%n" +
                             "------o----o-o---o-----o-o---------oo-o--------o---------------------------------o-o-o--o-----------%n" +
                             "---------------o-------------o-------o-------------------o-----o---------o-o-------------o-------oo-%n" +
                             "-o--o-------------o-o--------o--o--oo-------------o----ooo----o-------------o----------oo----o---o-o%n" +
                             "-o--o-------------o----oo------o--o-------o--o-----------o----o-----o--o----o--oo-----------o-------%n" +
                             "-o-----oo-------o------o---------------o--o----------o-----o-------o-----------o---o-o--oooooo-----o%n" +
                             "-o--------o-----o-----o---------oo----oo---o-----------o---o--oooo-oo--o-------o------oo--oo--o-----%n" +
                             "------------o-------------------o----oooo-------------oo-oo-----ooo-oo-----o-------o-oo-oooooooo---o%n" +
                             "-----------------------------------oooooooo---o-----o-------o--oooooo-o------------o-o-ooooooo-o----%n" +
                             "------------o------o-------o-------oo-oo--o--o---------o--o-o-o-ooooo-o--------------oo-o----o-oo-o-%n" +
                             "---o-o----------o--------oo----o----oooooooo-------o----o-o-o-o-----o-o-----o----------ooo-oo--o---o%n" +
                             "-o-o---------o-o---------------o--o--o--ooo---ooo-------o------oo-oo------------o--------o--o-o--o--%n" +
                             "-------oo---------------------------o-oo----------o------o-o-------o-----o----o-----o-oo-o-----o---o%n" +
                             "---o--------o-----o-------o-oo-----oo--oo-o----oo----------o--o---oo------oo----o-----o-------o-----%n" +
                             "---o--ooo-o---------o-o----o------------o---------o----o--o-------o-------------o----------------oo-%n" +
                             "---o------o----------------o----o------o------o---oo-----------o-------------o----------oo---------o%n" +
                             "--oo---------------o--o------o---o-----o--o-------------o------o-------o-----o-----o----o------o--o-%n" +
                             "-o-------o----------o-o-o-------o-----o--o-o-----------o-oo-----------o------o---------o-----o-o----%n" +
                             "----------o----o-------o----o--o------o------------o---o---------------oo----o-----ooo--------------%n" +
                             "----o--------oo----o-o----o--o------ooo----o-oooo---o--o-oo--------o-oo-----o-o---o-o--o-----oo-----%n" +
                             "------o--------o-ooooo----o---o--o-----o---------------o-o-------o-----o----------------------------%n" +
                             "o-------oo----o--oooooo-o---o--o------oooo----------o-oo-------o---o----------o------oo-------------%n" +
                             "-o---o----------o--oo-oo-o---o-----o-o-----------------------oo--o------o------o--------------------%n" +
                             "-----oo-o-o-o---ooooooooo----o----o--------o--o---oo---o------------o----------o-o---o------o-o--oo-%n" +
                             "------o------o---ooo-o---------------------------o--o---o---o----o--o-------o-----o------o----o----o%n" +
                             "-------o----------ooo-o-----o----o---o--o-oo--o--o-o--o------o--o-oo---ooo------------------------o-%n" +
                             "-o-------o------o-o--ooo--o---o---oo-----o----o-------------o----o-ooo-o------o--o-o------o-o-------%n" +
                             "---oo--o---o-o---------o---o--------------o--o-----o-------o-----o--o---o-oo--------o----o----o-----%n" +
                             "o------o----oo-o-----------oo--o---o--------o-o------o-------o-o------o-oo---------o-----oo---------%n" +
                             "----o--o---o-o-----------o---o------------o-------o----o--o--o--o-o---------------o-----------------%n" +
                             "-------oo--o-o-----o-----o----o-o--o----------------------o-------o------o----oo----ooo---------o---%n" +
                             "o-----oo-------------------o--o-----o-----------o------o-------o----o-----------o----------------o--%n" +
                             "--o---o-------o------------o--------------------o----o--o-------------oo---o---------oo--------o----%n" +
                             "--o--------o---------o------------o------o-------o------------o-------o---o---------ooooo-----------%n" +
                             "------o--------------o-o-o---------o---o-------o--o-----o-------o-o----------o-----oo-ooo----------o%n" +
                             "--o---------------o----o--oo-------------o---------o-------------------oo---------oo-o-ooo----------%n" +
                             "-o-----------o------ooo----o----------------ooo-----o--------o--o---o-----------o-o-oooooo--------oo%n" +
                             "-o---o-------o---o-oooo-----o-------------------o----oo-----------------o--o--------o--o------o--o--%n" +
                             "-------o---o------oooooo--o----ooo--o--------o-------o----------------------------oo-oo-o--o--------%n" +
                             "o--oo------o-----oo--o-oo------------oo--o------o--o-------------oo----o------------oooo-o------oo--%n" +
                             "-----o----------ooooooooo--------------oo--------------oo-----o-----o-o--o------o----------o----o---"));
    }

    private BitSetPattern getSpaceInvader1() {
        return patternFactory.create(String.format("--o-----o--%n" +
                                                 "---o---o---%n" +
                                                 "--ooooooo--%n" +
                                                 "-oo-ooo-oo-%n" +
                                                 "ooooooooooo%n" +
                                                 "o-ooooooo-o%n" +
                                                 "o-o-----o-o%n" +
                                                 "---oo-oo---"));
    }

    private BitSetPattern getSpaceInvader2() {
        return patternFactory.create(String.format("---oo---%n" +
                                                 "--oooo--%n" +
                                                 "-oooooo-%n" +
                                                 "oo-oo-oo%n" +
                                                 "oooooooo%n" +
                                                 "--o--o--%n" +
                                                 "-o-oo-o-%n" +
                                                 "o-o--o-o"));
    }

    private BitSetPattern getSpaceInvader2With10PercentDifferences() {
        return patternFactory.create(String.format("o-------%n" +
                                                 "--oooo-o%n" +
                                                 "-oooooo-%n" +
                                                 "oo-oo-oo%n" +
                                                 "ooo-oo-o%n" +
                                                 "--o--o--%n" +
                                                 "-o-oo-o-%n" +
                                                 "o-o--o-o"));
    }

    private BitSetPattern getInputWith2SpaceInvaders1Vertical() {
        return patternFactory.create(getSpaceInvader1().getText() + System.lineSeparator() + getSpaceInvader1().getText());
    }

    private BitSetPattern getInputWithSpaceInvader1SurroundedByOtherChars() {
        return patternFactory.create(String.format("oo" + "---oooo----" + "oo%n" +

                             "oo" + "--o-----o--" + "oo%n" +
                             "oo" + "---o---o---" + "oo%n" +
                             "oo" + "--ooooooo--" + "oo%n" +
                             "oo" + "-oo-ooo-oo-" + "oo%n" +
                             "oo" + "ooooooooooo" + "oo%n" +
                             "oo" + "o-ooooooo-o" + "oo%n" +
                             "oo" + "o-o-----o-o" + "oo%n" +
                             "oo" + "---oo-oo---" + "oo%n" +

                             "oo" + "oo--o-o-ooo" + "oo"));
    }

    private BitSetPattern getInputWithSpaceInvader1WithFewOtherChars() {
        return patternFactory.create(String.format("o--o-----o--o%n" +
                             "o---o---o---o%n" +
                             "o--ooooooo--o%n" +
                             "o-oo-ooo-oo-o%n" +
                             "ooooooooooooo%n" +
                             "oo-ooooooo-oo%n" +
                             "oo-o-----o-oo%n" +
                             "o---oo-oo---o"));
    }

    private BitSetPattern getInputWithoutInvader() {
        return patternFactory.create(String.format("o--------o--o%n" +
                             "o-----------o%n" +
                             "o-----------o%n" +
                             "o-oo-ooo-oo-o%n" +
                             "ooooo----oooo%n" +
                             "oo-o----oo-oo%n" +
                             "oo-o-----o-oo%n" +
                             "o---oo-oo---o"));
    }

    private BitSetPattern getInputWith2SpaceInvaders1Horizontal() {
        String[] lines = getSpaceInvader1().getText().split(System.lineSeparator());
        String input = "";
        for (String line : lines) {
            input += line + line + System.lineSeparator();
        }

        return patternFactory.create(input);
    }
}
