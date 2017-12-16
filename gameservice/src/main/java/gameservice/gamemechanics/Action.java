package gameservice.gamemechanics;

import gameservice.network.Topic;
import gameservice.model.Pawn;
import gameservice.model.Movable;
import org.slf4j.LoggerFactory;

public class Action {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Action.class);

    private Topic action;
    private Pawn actor;
    private String data;

    public Action(Topic action, Pawn actor, String data) {
        this.action = action;
        this.actor = actor;
        this.data = data;
    }

    public Topic getAction() {
        return action;
    }

    public Pawn getActor() {
        return actor;
    }

    public Movable.Direction getData() {
        Movable.Direction direction;
        log.info("data = " + data);
        switch (data) {
            case "{\"direction\":\"UP\"}":
                direction = Movable.Direction.UP;
                break;
            case "{\"direction\":\"RIGHT\"}":
                direction = Movable.Direction.RIGHT;
                break;
            case "{\"direction\":\"LEFT\"}":
                direction = Movable.Direction.LEFT;
                break;
            case "{\"direction\":\"DOWN\"}":
                direction = Movable.Direction.DOWN;
                break;
            case "{\"direction\":\"IDLE\"}":
                direction = Movable.Direction.IDLE;
                break;
            default:
                direction = Movable.Direction.IDLE;
                break;
        }
        return direction;
    }
}
