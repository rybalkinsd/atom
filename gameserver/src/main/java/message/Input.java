package message;

import boxes.InputQueue;
import message.Message;
import org.springframework.web.socket.WebSocketSession;

public class Input {
    private Message message;
    private WebSocketSession session;

    public Input(WebSocketSession session, Message message) {
        this.message = message;
        this.session = session;
    }

    public Message getMessage() {
        return message;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public static boolean hasMoveInputForPlayer(WebSocketSession session) {
        Input input;
        if (!InputQueue.getInstance().isEmpty()) {
            /*while (InputQueue.getInstance().iterator().hasNext()) {
                input = InputQueue.getInstance().iterator().next();*/
            Input[] queue = InputQueue.getInstance().toArray(new Input[InputQueue.getInstance().size()]);
            for (Input i : queue)
                if ((i.getSession() == session) && (i.getMessage().getTopic() == Topic.MOVE))
                    return true;
        }
        return false;
    }

    public static boolean hasBombInputForPlayer(WebSocketSession session) {
        Input input;
        if (!InputQueue.getInstance().isEmpty()) {
            /*while (InputQueue.getInstance().iterator().hasNext()) {
                input = InputQueue.getInstance().iterator().next();*/
            Input[] queue = InputQueue.getInstance().toArray(new Input[InputQueue.getInstance().size()]);
            for (Input i : queue)
                if ((i.getSession() == session) && (i.getMessage().getTopic() == Topic.PLANT_BOMB))
                    return true;
        }
        return false;
    }

    public static Input getInputForPlayer(WebSocketSession session) {
        Input input;
        Input[] queue = InputQueue.getInstance().toArray(new Input[InputQueue.getInstance().size()]);
        for (Input i : queue)
            if (i.getSession() == session)
                return i;
        return null;
        /*while (InputQueue.getInstance().iterator().hasNext()) {
            input = InputQueue.getInstance().iterator().next();
            if (input.getSession()==session)
                return input;
        }*/
    }
}
