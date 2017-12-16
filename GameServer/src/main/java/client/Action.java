package client;

public class Action {
    /*
    * Message with name of player, who send it
     */
    private Message message;
    private String name;

    public Action(Message message, String name) {
        this.message = message;
        this.name = name;
    }

    public Message getMessage() {
        return this.message;
    }

    public String getName() {
        return this.name;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }
}
