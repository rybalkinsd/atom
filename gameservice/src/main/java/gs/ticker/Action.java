package gs.ticker;

import gs.message.Topic;
import gs.model.Girl;
import gs.model.Movable;

public class Action {
    public Action(Topic action, Girl actor, String data) {
        this.action = action;
        this.actor = actor;
        this.data = data;
    }

    private Topic action;
    private Girl actor;
    private String data;

    public Topic getAction() {
        return action;
    }

    public Girl getActor() {
        return actor;
    }

    public Movable.Direction getData() {
        Movable.Direction direction;
        switch(data) {
            case "UP" :
                direction = Movable.Direction.UP;
                break;
            case "RIGHT" :
                direction = Movable.Direction.RIGHT;
                break;
            case "LEFT" :
                direction = Movable.Direction.LEFT;
                break;
            case "DOWN" :
                direction = Movable.Direction.DOWN;
                break;
            case "IDLE" :
                direction = Movable.Direction.IDLE;
                break;
            default :
                direction = Movable.Direction.IDLE;
                break;
        }
        return direction;
    }
}
