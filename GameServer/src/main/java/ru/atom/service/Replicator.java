package ru.atom.service;

import ru.atom.message.Message;
import ru.atom.message.Topic;
import ru.atom.model.GameModel;
import ru.atom.model.TileMap;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Replicator {

    static Message getBackGroundReplica(GameModel gameModel) {
        TileMap map = gameModel.getTileMap();
        String replica = "{\"objects\":[";
        ArrayList<String> grassTiles = new ArrayList<String>(map.getHeight() * map.getWidth());
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                grassTiles.add("{\"position\":{\"x\":" + i + ",\"y\":" + j + "}" +
                        ",\"id\":" + GameModel.generateGameObjectId() +
                        ",\"type\":\"Grass\"" +
                        "}");
            }
        }
        replica = replica.concat(grassTiles.stream().collect(Collectors.joining(", ")).toString());
        replica = replica.concat("],\"deleted\":[");
        replica = replica.concat("],\"gameOver\":false}");
        return new Message(Topic.REPLICA, replica);

    }


    public static Message getReplica(GameModel gameModel) {
        String replica = "{\"objects\":[";

        replica = replica.concat(gameModel.changed.entrySet().stream().map(girlEntry ->
                girlEntry.getValue().toString()).collect(Collectors.joining(",")).toString());
        replica = replica.concat("],\"deleted\":[");
        replica = replica.concat(gameModel.deleted.entrySet().stream().map(girlEntry ->
                girlEntry.getValue().toString()).collect(Collectors.joining(",")).toString());
        replica = replica.concat("],\"gameOver\":false}");
        return new Message(Topic.REPLICA, replica);

    }


}
