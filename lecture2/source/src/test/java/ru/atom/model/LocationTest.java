package ru.atom.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class LocationTest {

    private static final Location MOSCOW = new Location(55.7498597,37.3523184);
    private static final Location LONDON = new Location(51.5287718,-0.2416814);

    private static final double ACCURACY = 15_000;

    @Test
    public void testDistanceToSelf() throws Exception {
        assertTrue(MOSCOW.distanceTo(MOSCOW) < ACCURACY);
        assertTrue(LONDON.distanceTo(LONDON) < ACCURACY);
    }

    @Test
    public void testDistanceToCommutative() throws Exception {
        double m2l = MOSCOW.distanceTo(LONDON);
        double l2m = LONDON.distanceTo(MOSCOW);
        assertTrue(
                Math.abs(m2l - l2m) < ACCURACY
        );
    }

    @Test
    public void testDistanceToBetweenTwoCities() {
        assertTrue(
                Math.abs(MOSCOW.distanceTo(LONDON) - 2_500_000) < ACCURACY
        );
    }
}