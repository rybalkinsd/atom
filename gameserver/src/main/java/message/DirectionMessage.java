package message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameobjects.Movable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectionMessage {
    private final Movable.Direction direction;
    private static final Logger log = LoggerFactory.getLogger(DirectionMessage.class);

    @JsonCreator
    public DirectionMessage(@JsonProperty("direction") Movable.Direction direction) {
        this.direction = direction;
    }

    public Movable.Direction getDirection() {
        log.info("got direction");
        return direction;
    }

}
