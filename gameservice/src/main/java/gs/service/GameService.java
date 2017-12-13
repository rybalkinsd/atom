package gs.service;

import org.slf4j.LoggerFactory;
import gs.storage.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(GameService.class);

    @Autowired
    SessionStorage storage;

    public long create(int playerCount) {
        return storage.addSession(playerCount);
    }
}
