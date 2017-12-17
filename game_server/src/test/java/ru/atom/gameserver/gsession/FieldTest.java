package ru.atom.gameserver.gsession;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FieldTest {

    @Test
    public void testCellType() {
        Field field = new Field();
        assertEquals(Field.WALL, field.getCellType(new Field.Cell(0, 0)));
    }

    @Test
    public void testSetGetId() {
        Field field = new Field();
        field.setId(new Field.Cell(1, 1), 112);
        assertEquals(112, field.getId(new Field.Cell(1, 1)));
    }

    @Test
    public void testCanMove() {
        Field field = new Field();
        assertTrue(field.canMove(new Field.Cell(1, 1), new Field.Cell(1, 2)));
    }

    @Test
    public void testFireCells() {
        Field field = new Field();
        assertEquals(3, field.getFireCells(new Field.Cell(1, 1), 1).size());
    }

    @Test
    public void testApplyFire() {
        Field field = new Field();
        List<Field.Cell> fireCells = field.getFireCells(new Field.Cell(1,2), 1);
        assertEquals(1, field.applyFireCells(fireCells).size());
    }

}
