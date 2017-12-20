package ru.atom.gameserver.model;

import org.junit.Test;
import ru.atom.gameserver.geometry.Bar;
import ru.atom.gameserver.geometry.Point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ModelTest {

    Pawn pawn = new Pawn(1, new Point(0, 0), 0.5f, 1);

    @Test
    public void pawnCalculateBarTest() {
        Bar expectedBar = new Bar(0,0,25,25);
        pawn.calculateBar();
        assertTrue(pawn.getBar().equals(expectedBar));
    }

    @Test
    public void pawnGetVelocityTest() {
        assertTrue(Math.abs(pawn.getVelocity() - 0.5) < 0.001);
    }

    @Test
    public void pawnGetMaxBombsTest() {
        assertEquals(pawn.getMaxBombs(), 1);
    }

    @Test
    public void pawnGetBombPowerTest() {
        assertEquals(pawn.getBombPower(), 1);
    }

    @Test
    public void grassTest() {
        Grass grass = new Grass(1, new Point(15, 15));
        assertTrue(grass.getPosition().equals(new Point(15,15)));
    }

    @Test
    public void bombTest() {
        Bomb bomb = new Bomb(2, new Point(56, 56), 700, 1);
        bomb.calculateBar();
        assertTrue(bomb.getBar().equals(new Bar(new Point(56,56), 28, 28)));
    }
}
