package ru.atom.model.object.actor;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.atom.model.Level;
import ru.atom.model.World;
import ru.atom.util.V;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sergey on 2/2/17.
 */
@Ignore
public class ActorTest {
    private Actor actor;

    @Before
    public void setUp() throws Exception {
        new World(Level.STANDARD);
        actor = new Actor();
        actor.setPosition(V.of(1.0, 2.0));
        actor.setVelocity(V.of(-0.01, -0.02));

    }

    @Test
    public void move() throws Exception {
        IntStream.of(1, 5, 12, 17, 31, 34).forEach(x -> {
            actor.tick(x);
        });

        assertThat(actor.getPosition()).isEqualTo(V.of(0, 0));
    }

}