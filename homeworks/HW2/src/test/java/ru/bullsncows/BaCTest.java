package ru.bullsncows;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


public class BaCTest {

    @Test
    public void testBaC() {
        BaC baC = new BaC();
        baC.setCurrent("31");
        BaC.Overlap caB = baC.getOverlap("33");
        assertEquals(caB.getB(), 1);
        assertEquals(caB.getC(),0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongLengthTest() {
        BaC baC = new BaC();
        baC.setCurrent("abc");
        BaC.Overlap caB = baC.getOverlap("a");
    }


    @Test
    public void testBaC2() {
        BaC baC = new BaC();
        baC.setCurrent("331");
        BaC.Overlap caB = baC.getOverlap("313");
        assertEquals(caB.getB(), 1);
        assertEquals(caB.getC(),2);
    }

    @Test
    public void testBaC3() {
        BaC baC = new BaC();
        baC.setCurrent("abcde");
        BaC.Overlap caB = baC.getOverlap("bcdea");
        assertEquals(caB.getB(), 0);
        assertEquals(caB.getC(),5);
    }


    @Test
    public void testFile() throws IOException {
        BaC baC = new BaC();
        baC.setWords("dictionary.txt");
        String next = baC.nextWord();
        BaC.Overlap overlap = baC.getOverlap(next);
        assertEquals(overlap.getB(),next.length());
        assertEquals(overlap.getC(),0);
    }
}
