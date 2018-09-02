package ru.atom.service;

import ru.atom.message.Message;
import ru.atom.message.Topic;
import ru.atom.model.GameModel;
import ru.atom.model.TileMap;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Replicator {

    static String getBackGround(GameModel gameModel) {
        TileMap map = gameModel.getTileMap();
        String replica = "";
        ArrayList<String> grassTiles = new ArrayList<String>(map.getHeight() * map.getWidth());
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                grassTiles.add("{\"position\":{\"x\":" +
                        i * map.getTileWidth() + ",\"y\":" + j * map.getTileHeight() + "}" +
                        ",\"id\":" + GameModel.generateGameObjectId() +
                        ",\"type\":\"Grass\"" +
                        "}");
            }
        }
        replica = replica.concat(grassTiles.stream().collect(Collectors.joining(", ")).toString());
        return new String(replica);

    }

    public static Message getReplicaWithBack(GameModel gameModel) {
        String replica = "{\"objects\":[";
        replica = replica.concat(getBackGround(gameModel) + ",");
        replica = replica.concat(gameModel.changed.stream().map(girl ->
                girl.toString()).collect(Collectors.joining(",")).toString());
        replica = replica.concat("],\"deleted\":[");
        replica = replica.concat(gameModel.deleted.stream().map(girl ->
                girl.toString()).collect(Collectors.joining(",")).toString());
        replica = replica.concat("],\"gameOver\":" +
                gameModel.isGameOver() + ",\"winnerId\":" + gameModel.getWinner() + "}");

        return new Message(Topic.REPLICA, replica);
    }


    public static Message getReplica(GameModel gameModel) {
        String replica = "{\"objects\":[";
        replica = replica.concat(gameModel.changed.stream().map(girl ->
                girl.toString()).collect(Collectors.joining(",")).toString());
        replica = replica.concat("],\"deleted\":[");
        replica = replica.concat(gameModel.deleted.stream().map(girl ->
                girl.toString()).collect(Collectors.joining(",")).toString());
        replica = replica.concat("],\"gameOver\":" +
                gameModel.isGameOver() + ",\"winnerId\":" + gameModel.getWinner() + "}");

        return new Message(Topic.REPLICA, replica);

    }


}
