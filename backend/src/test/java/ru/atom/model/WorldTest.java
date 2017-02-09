package ru.atom.model;

import org.junit.Before;
import org.junit.Test;
import ru.atom.model.actor.Pawn;
import ru.atom.util.JsonHelper;
import ru.atom.util.V;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


/**
 * Created by sergey on 2/3/17.
 */
public class WorldTest {
    private World world;

    @Before
    public void setUp() throws Exception {
        world = new World(Level.STANDARD);
        Pawn pawn = Pawn.create(V.of(3, 7));
        world.register(pawn);
    }

    @Test
    public void serialization() throws Exception {
        Throwable ex = catchThrowable(() -> JsonHelper.toJson(world));
        assertThat(ex).isNull();
    }

}