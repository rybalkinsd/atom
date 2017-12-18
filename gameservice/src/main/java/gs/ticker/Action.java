package gs.ticker;

import gs.message.Topic;
import gs.model.Girl;
import gs.model.Movable;

public class Action {
    private Topic action;
    private Girl actor;
    private String data;

    public Action(Topic action, Girl actor, String data) {
        this.action = action;
        this.actor = actor;
        this.data = data;
    }

    public Topic getAction() {
        return action;
    }

    public Girl getActor() {
        return actor;
    }

    public Movable.Direction getData() {
        Movable.Direction direction;
        //System.out.println("DATA = " + data);
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
