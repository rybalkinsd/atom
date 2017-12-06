package gs.message;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gs.message.Message;
import gs.message.Topic;
import gs.model.Bomb;
import gs.model.GameObject;
import gs.model.Girl;
import gs.util.JsonHelper;

import java.util.List;

public class Replica {

    public void writeReplica(List<GameObject> objects, boolean gameOverFlag) {
        Message message = new Message(Topic.REPLICA, toJsonNode(objects, gameOverFlag));
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
                    jsonObject.put("speed", girl.getSpeed())
                            .put("BombCapacity", girl.getBombCapacity())
                            .put("BombRange", girl.getBombRange());
                }
                break;
                case "Bomb": {
                    jsonObject.put("type", gameObjectClassName);
                    Bomb bomb = (Bomb)object;
                    jsonObject.put("Lifetime", bomb.getLifetime())
                            .put("RangeOfExplosion", bomb.getRangeOfExplosion());
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