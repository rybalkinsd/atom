package gs;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private static final Logger log = LogManager.getLogger(GameService.class);

    public long create(int playerCount) {
        return GameRepository.newSession(playerCount);
    }
}
