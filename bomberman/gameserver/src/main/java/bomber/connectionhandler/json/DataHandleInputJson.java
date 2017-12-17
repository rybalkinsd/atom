package bomber.connectionhandler.json;

import bomber.connectionhandler.PlayerAction;

public class DataHandleInputJson {
    PlayerAction.EventType direction;

    public DataHandleInputJson() {
    }

    public DataHandleInputJson(PlayerAction.EventType eventType) {
        this.direction = eventType;
    }

    public PlayerAction.EventType getEventType() {
        return direction;
    }

    public void setEventType(PlayerAction.EventType eventType) {
        this.direction = eventType;
    }


}
