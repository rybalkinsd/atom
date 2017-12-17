package bomber.connectionhandler;

public class PlayerAction {

    private Integer id;
    private EventType type;

    public enum EventType {
        UP, DOWN, RIGHT, LEFT, BOMB
    }

    public PlayerAction() {
    }

    public PlayerAction(final Integer id, final EventType type) {
        this.id = id;//Player id
        this.type = type;//U,R,L,D for MOVE, B for Bomb plant

    }

    public Integer getId() {
        return id;
    }

    public EventType getType() {
        return type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj instanceof PlayerAction) {
                PlayerAction playerAction = (PlayerAction) obj;
                return this.id == playerAction.id && this.type == playerAction.type;
            }
            return false;
        }
    }

    @Override
    public String toString() {
        return "{PlayerAction: {" +
                "\nid = " + id + "" +
                "\ntype = " + type +
                "\n}";
    }
}