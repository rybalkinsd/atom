package ru.atom.model;

import org.junit.Before;
import org.junit.Test;
import ru.atom.model.object.actor.Pawn;
import ru.atom.util.JsonHelper;
import ru.atom.util.V;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


/**
 * Created by sergey on 2/3/17.
 */
public class WorldTest {
    private World world;
    private Pawn player1;

    @Before
    public void setUp() throws Exception {
        world = new World(Level.STANDARD);
        player1 = Pawn.create(V.of(3, 7));
    }

    @Test
    public void getWinnerFromOne() throws Exception {
        assertThat(world.isGameOver()).isTrue();
        assertThat(world.findWinner()).isEqualTo(player1);
    }

    @Test
    public void getWinnerFromMany() throws Exception {
        Pawn player2 = Pawn.create(V.of(2, 2));
        assertThat(world.isGameOver()).isFalse();

        player2.destroy();
        assertThat(world.isGameOver()).isTrue();
        assertThat(world.findWinner()).isEqualTo(player1);
    }


    @Test
    public void serialization() throws Exception {
        Throwable ex = catchThrowable(() -> JsonHelper.toJson(world));
        assertThat(ex).isNull();
    }

}