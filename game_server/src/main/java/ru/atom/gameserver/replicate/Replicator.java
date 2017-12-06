package ru.atom.gameserver.replicate;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import ru.atom.gameserver.message.Message;
import ru.atom.gameserver.message.Topic;
import ru.atom.gameserver.model.Bomb;
import ru.atom.gameserver.model.Box;
import ru.atom.gameserver.model.Buff;
import ru.atom.gameserver.model.Explosion;
import ru.atom.gameserver.model.GameObject;
import ru.atom.gameserver.model.Pawn;
import ru.atom.gameserver.util.JsonHelper;

import java.util.List;

/**
 * Created by Alexandr on 05.12.2017.
 */
public class Replicator {

    public void writeReplica(List<GameObject> objects, boolean gameOverFlag) {
        ObjectNode node = toJsonNode(objects, gameOverFlag);
        Message message = new Message(Topic.REPLICA, node);
        System.out.println(node);
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
            String gameObjectClassName = object.getClass().getSimpleName();
            jsonObject.put("type", gameObjectClassName);
            switch (gameObjectClassName) {
                case "Wall":
                    break;
                case "Pawn": {
                    Pawn girl = (Pawn)object;
                    jsonObject.put("velocity", girl.getVelocity())
                            .put("maxBombs", girl.getMaxBombs())
                            .put("bombPower", girl.getBombPower())
                            .put("speedModifier", girl.getSpeedModifier());
                }
                    break;
                case "Bomb": {
                    Bomb bomb = (Bomb)object;
                    jsonObject.put("lifeTime", bomb.getLifetime())
                            .put("power", bomb.getPower());
                }
                    break;
                case "Box": {
                    Box box = (Box)object;
                    boolean containsBuff = box.containsBuff();
                    jsonObject.put("containsBuff", containsBuff);
                    if (containsBuff) {
                        jsonObject.put("buffType", box.getBuffType().name());
                    }
                }
                    break;
                case "Buff": {
                    Buff buff = (Buff)object;
                    jsonObject.put("buffType", buff.getType().name());
                }
                    break;
                case "Explosion": {
                    Explosion explosion = (Explosion)object;
                    jsonObject.put("lifetime", explosion.getLifetime());
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
