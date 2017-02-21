package messageSystem;

import main.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * @author e.shubin
 */
public abstract class Message {
    @NotNull
    protected final static Logger log = LogManager.getLogger(Message.class);

    protected static final MessageSystem messageSystem = ApplicationContext.instance().get(MessageSystem.class);

    private final Address from;
    private final Address to;

    public Message(Address from, Address to) {
        this.from = from;
        this.to = to;
    }

    protected static MessageSystem getMessageSystem() {
        return ApplicationContext.instance().get(MessageSystem.class);
    }

    public Address getFrom() {
        return from;
    }

    public Address getTo() {
        return to;
    }

    public abstract void exec(Abonent abonent);
}
