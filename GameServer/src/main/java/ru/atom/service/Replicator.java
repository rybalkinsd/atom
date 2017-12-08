package ru.atom.service;

import ru.atom.message.Message;
import ru.atom.message.Topic;
import ru.atom.model.GameModel;

import java.util.stream.Collectors;

public class Replicator {

    public static Message getBackGround() {
        String replica = "{\"objects\":[";
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 13; j++) {
                replica = replica.concat("{\"position\":{\"x\":" + i + ",\"y\":" + j + "}" +
                        ",\"id\":" + (i*16+j + 10000) +
                        ",\"type\":\"Grass\"" +
                        "},");
            }
        }
        replica = replica.concat("{\"position\":{\"x\":" + 13 + ",\"y\":" + 17 + "}" +
                ",\"id\":" + (16*16+13+10000) +
                ",\"type\":\"Grass\"" +
                "}");

        replica = replica.concat("],\"gameOver\":false}");
        return new Message(Topic.REPLICA, replica);

    }

    public static Message getReplica(GameModel gameModel) {
        String replica = "{\"objects\":[";

        replica = replica.concat(gameModel.changed.entrySet().stream().map(girlEntry ->
                girlEntry.getValue().toString()).collect(Collectors.joining(",")).toString());

        replica = replica.concat("],\"gameOver\":false}");
        return new Message(Topic.REPLICA, replica);

    }


}
