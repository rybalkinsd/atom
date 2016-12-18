package messageSystem;

import main.Service;

/**
 * Created by User on 26.11.2016.
 */
public abstract class Message {
    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void execute(Service service);

}
