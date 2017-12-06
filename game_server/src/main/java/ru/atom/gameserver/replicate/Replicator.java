package ru.atom.gameserver.replicate;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.atom.gameserver.message.Message;
import ru.atom.gameserver.message.Topic;
import ru.atom.gameserver.model.Bomb;
import ru.atom.gameserver.model.GameObject;
import ru.atom.gameserver.model.Girl;
import ru.atom.gameserver.util.JsonHelper;

import java.util.List;

/**
 * Created by Alexandr on 05.12.2017.
 */
public class Replicator {

    public void writeReplica(List<GameObject> objects, boolean gameOverFlag) {
        Message message = new Message(Topic.REPLICA, toJsonNode(objects, gameOverFlag));
        //pass the message to ConnectionHandler
    }

    private ObjectNode toJsonNode(List<GameObject> objects, boolean gameOverFlag) {
        ObjectNode rootObject = JsonHelper.nodeFactory.objectNode();
        ArrayNode jsonArrayNode = rootObject.putArray("objects");
        for (GameObject object : objects) {
            ObjectNode jsonObject = JsonHelper.nodeFactory.objectNode();
            jsonObject.putObject("position")
                    .put("x", object.getPosition().getX())
                    .put("y", object.getPosition().getY());
            jsonObject.put("id", object.getId());
            String gameObjectClassName = object.getClass().getName();
            switch (gameObjectClassName) {
                case "Wall":
                    jsonObject.put("type", gameObjectClassName);
                    break;
                case "Girl": {
                    jsonObject.put("type", "Pawn");
                    Girl girl = (Girl)object;
                    jsonObject.put("velocity", girl.getVelocity())
                            .put("maxBombs", girl.getMaxBombs())
                            .put("bombPower", girl.getBombPower())
                            .put("speedModifier", girl.getSpeedModifier());
                }
                    break;
                case "Bomb": {
                    jsonObject.put("type", gameObjectClassName);
                    Bomb bomb = (Bomb)object;
                    jsonObject.put("lifeTime", bomb.getLifetime())
                            .put("power", bomb.getPower());
                }
                    break;
                default:
                    break;
            }
            jsonArrayNode.add(jsonObject);
        }
        rootObject.put("gameOver", gameOverFlag);
        return rootObject;
    }

}
