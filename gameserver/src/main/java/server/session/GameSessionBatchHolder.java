package server.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Holder class.
 * Important for client - server data transfer
 */
public class GameSessionBatchHolder {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    private List<GameSession> sessions = new ArrayList<>();

    private GameSessionBatchHolder() { }

    public GameSessionBatchHolder(List<GameSession> sessions) {
        this.sessions = sessions;
    }

    public List<GameSession> getSessions() {
        return sessions;
    }

    public static GameSessionBatchHolder readJson(String json) throws IOException {
        return mapper.readValue(json, GameSessionBatchHolder.class);
    }

    public String writeJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

}

