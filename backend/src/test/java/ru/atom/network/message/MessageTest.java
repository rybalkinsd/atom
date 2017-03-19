package ru.atom.network.message;

import org.junit.Before;
import org.junit.Test;
import ru.atom.model.input.Direction;
import ru.atom.model.input.Move;
import ru.atom.util.JsonHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/**
 * Created by sergey on 2/2/17.
 */
public class MessageTest {
    private Message msg;
    private Move move;

    @Before
    public void setUp() throws Exception {
        move = new Move(Direction.UP);
        msg = new Message(Topic.MOVE, JsonHelper.toJson(move));
    }

    @Test
    public void serialize() throws Exception {
        Throwable ex = catchThrowable(() -> JsonHelper.toJson(msg));
        assertThat(ex).isNull();
    }

    @Test
    public void deserialize() throws Exception {
        String rawMessage = "{\"topic\":\"MOVE\",\"data\":{\"direction\": \"UP\"}}";
        Throwable ex = catchThrowable(() -> JsonHelper.fromJson(rawMessage, Message.class));
        assertThat(ex).isNull();
    }

}