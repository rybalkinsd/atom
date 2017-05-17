package ru.atom.websocket.util;

import com.fasterxml.jackson.annotation.JsonRawValue;

import java.util.HashMap;

/**
 * Created by BBPax on 19.04.17.
 */

public class Result {

    private int id;

    @JsonRawValue
    private final HashMap<String, Integer> result;

    public Result() {
        result = new HashMap<>();
    }

    public Result setgameId(int gameId) {
        id = gameId;
        return this;
    }

    public int getGameId() {
        return id;
    }

    public void addScore(String login, int result) {
        this.result.put(login, result);
    }

}
