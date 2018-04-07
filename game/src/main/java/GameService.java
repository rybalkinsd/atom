import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


@Service
public class GameService {
    private ConcurrentHashMap<GameSession,Long> gameSessionMap
            = new ConcurrentHashMap<>();

    private long currentGameSessionID = 0;

    public long create(int PlayerCount) {
        currentGameSessionID++;
        gameSessionMap.put(new GameSession(currentGameSessionID,PlayerCount),currentGameSessionID);
        return currentGameSessionID;
    }

    public void connect(String name, long gameID) {



    }
}
