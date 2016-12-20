package protocol;

import org.jetbrains.annotations.NotNull;
import protocol.model.Cell;
import protocol.model.Food;

import java.io.Serializable;

/**
 * Created by artem on 19.12.16.
 */
public class CommandRespawn extends Command implements Serializable {
    @NotNull
    public static final String NAME = "respawn";

    public CommandRespawn() {
        super(NAME);
    }

}
