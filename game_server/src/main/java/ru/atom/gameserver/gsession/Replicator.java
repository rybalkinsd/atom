package ru.atom.gameserver.gsession;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.atom.gameserver.component.ConnectionHandler;
import ru.atom.gameserver.message.Message;
import ru.atom.gameserver.message.Topic;
import ru.atom.gameserver.model.GameObject;
import ru.atom.gameserver.util.JsonHelper;

import java.util.List;

public class Replicator {

    private final Long gameId;
    private final ConnectionHandler connectionHandler;

    public Replicator(Long gameId, ConnectionHandler connectionHandler) {
        this.gameId = gameId;
        this.connectionHandler = connectionHandler;
    }

    public void writeWinner(Integer winnerId) {
        connectionHandler.sendGameOver(gameId, winnerId);
    }

    public void writePossess(int possess, String login) {
        connectionHandler.sendMessage(gameId, login,
                new Message(Topic.POSSESS, JsonHelper.nodeFactory.numberNode(possess)));
    }

    public void writeReplica(List<GameObject> objects, boolean gameOverFlag) {
        ObjectNode node = getJsonNode(objects, gameOverFlag);
        Message message = new Message(Topic.REPLICA, node);
        connectionHandler.sendMessage(gameId, message);
    }

    private ObjectNode getJsonNode(List<GameObject> objects, boolean gameOverFlag) {
        ObjectNode rootObject = JsonHelper.nodeFactory.objectNode();
        ArrayNode jsonArrayNode = rootObject.putArray("objects");
        for (GameObject object : objects) {
            ObjectNode jsonObject = JsonHelper.getJsonNode(object);
            jsonObject.put("type", object.getClass().getSimpleName());
            jsonArrayNode.add(jsonObject);
        }
        rootObject.put("gameOver", gameOverFlag);
        return rootObject;
    }
}
