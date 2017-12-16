package ru.atom.thread.mm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatchMakerPriority {
    private Map gameSession = new HashMap<GameId, List<GameSession>>();

    public Object getList(GameId id) {
        return gameSession.get(id);
    }

    public void createGameSession(GameId id, Object list) {
        gameSession.put(id, list);
    }
}
