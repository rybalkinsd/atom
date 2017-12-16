package gameservice.service;

import gameservice.Sessions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    @Autowired
    Sessions sessions;

    public long create(int playerCount) {
        return sessions.addSession(playerCount);
    }
}
