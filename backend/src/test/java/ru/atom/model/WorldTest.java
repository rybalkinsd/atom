package ru.atom.model;

import org.junit.Before;
import org.junit.Test;
import ru.atom.model.actor.Pawn;
import ru.atom.util.JsonHelper;
import ru.atom.util.V;

/**
 * Created by sergey on 2/3/17.
 */
public class WorldTest {
    private World world;

    @Before
    public void setUp() throws Exception {
        world = new World();
        Pawn pawn = new Pawn(V.of(3, 7));
        world.register(pawn);
    }

    @Test
    public void serialization() throws Exception {
        System.out.println(JsonHelper.toJson(world));
    }

}