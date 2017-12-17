package mm.dao;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonHistory {

    @JsonProperty("gameId")
    public long gameid;

    @JsonProperty("players")
    public List<JsonPlayer> players;

    static class JsonPlayer {
        @JsonProperty("login")
        String name;

        @JsonProperty("score")
        int score;
    }
}