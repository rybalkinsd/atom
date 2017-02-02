package ru.atom.network.message;

import org.junit.Before;
import org.junit.Test;
import ru.atom.util.JsonHelper;
import ru.atom.util.V;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sergey on 2/2/17.
 */
public class MessageTest {
    private Message<Move> message;
    private Move move;

    @Before
    public void setUp() throws Exception {
        move = new Move(V.of(1.0, 2.0));
        message = new Message<>(Move.class, JsonHelper.toJSON(move));
    }

    @Test
    public void internalize() throws Exception {
        assertThat(message.internalize()).isEqualTo(move);
    }

    @Test
    public void fromRawMessage() throws Exception {
        System.out.println(JsonHelper.toJSON(message));
    }

}