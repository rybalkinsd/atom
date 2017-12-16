package ru.atom.gameserver.json;

import org.junit.Test;
import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.model.Bomb;
import ru.atom.gameserver.model.Buff;
import ru.atom.gameserver.model.Fire;
import ru.atom.gameserver.model.Pawn;
import ru.atom.gameserver.model.Wall;
import ru.atom.gameserver.model.Wood;
import ru.atom.gameserver.util.JsonHelper;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexandr on 06.12.2017.
 */
public class JsonTest {

    @Test
    public void pawnJsonTest() {
        Pawn pawn = new Pawn(1, new Point(1.0f, 1.0f), 1.0f, 1);
        String actualString = JsonHelper.getJsonNode(pawn).toString();
        String expectedString = "{\"id\":1,\"position\":{\"x\":1.0,\"y\":1.0}," +
                "\"velocity\":1.0,\"maxBombs\":1,\"bombPower\":1,\"speedModifier\":1.0}";
        assertEquals(expectedString, actualString);
    }

    @Test
    public void wallJsonTest() {
        Wall wall = new Wall(1, new Point(1.0f, 1.0f));
        String actualString = JsonHelper.getJsonNode(wall).toString();
        String expectedString = "{\"id\":1,\"position\":{\"x\":1.0,\"y\":1.0}}";
        assertEquals(expectedString, actualString);
    }

    @Test
    public void bombJsonTest() {
        Bomb bomb = new Bomb(1, new Point(1.0f, 1.0f), 2000, 2);
        String actualString = JsonHelper.getJsonNode(bomb).toString();
        String expectedString = "{\"id\":1,\"position\":{\"x\":1.0,\"y\":1.0},\"lifetime\":2000,\"power\":2}";
        assertEquals(expectedString, actualString);
    }

    @Test
    public void woodWithoutBuffJsonTest() {
        Wood wood = new Wood(1, new Point(1.0f, 1.0f));
        String actualString = JsonHelper.getJsonNode(wood).toString();
        String expectedString = "{\"id\":1,\"position\":{\"x\":1.0,\"y\":1.0}}";
        assertEquals(expectedString, actualString);
    }

    @Test
    public void buffJsonTest() {
        Buff buff = new Buff(1, new Point(1.0f, 1.0f), Buff.BuffType.POWER);
        String actualString = JsonHelper.getJsonNode(buff).toString();
        String expectedString = "{\"id\":1,\"position\":{\"x\":1.0,\"y\":1.0},\"buffType\":\"POWER\"}";
        assertEquals(expectedString, actualString);
    }

    @Test
    public void explosionJsonTest() {
        Fire explosion = new Fire(1, new Point(1.0f, 1.0f), 2000);
        String actualString = JsonHelper.getJsonNode(explosion).toString();
        String expectedString = "{\"id\":1,\"position\":{\"x\":1.0,\"y\":1.0},\"lifetime\":2000}";
        assertEquals(expectedString, actualString);
    }

}
