package ru.atom.gameserver.json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.atom.gameserver.message.Message;
import ru.atom.gameserver.message.Topic;
import ru.atom.gameserver.model.GameObject;
import ru.atom.gameserver.util.JsonHelper;

import java.util.List;

/**
 * Created by Alexandr on 05.12.2017.
 */
public class Replicator {

    public Message writeReplica(List<GameObject> objects, boolean gameOverFlag) {
        ObjectNode node = getJsonNode(objects, gameOverFlag);
        Message message = new Message(Topic.REPLICA, node);
        //pass the message to ConnectionHandler
        return message;
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
