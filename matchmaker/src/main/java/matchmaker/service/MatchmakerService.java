package matchmaker.service;

import matchmaker.connection.ConnectionQueue;
import matchmaker.connection.Connections;
import matchmaker.dao.GameSessionDao;
import matchmaker.dao.UserDao;
import matchmaker.model.GameSession;
import matchmaker.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MatchmakerService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MatchmakerService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    GameSessionDao gameSessionDao;

    public long join(@NotNull String name) {
        if (Connections.getInstance().containsKey(name)) {
            return Connections.getInstance().get(name);
        } else {
            ConnectionQueue.getInstance().offer(name);
            while (!Connections.getInstance().containsKey(name)) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    log.error("Interrupted");
                }
            }
            return Connections.getInstance().get(name);
        }
    }

    @NotNull
    @Transactional
    public void saveUser(@NotNull String name, @NotNull Long gameId) {
        User user = new User();
        user.setName(name);
        user.setGameSession(gameSessionDao.getById(gameId));
        userDao.save(user);
    }

    @NotNull
    @Transactional
    public void saveGameSession(@NotNull Long id, Integer playerCount) {
        GameSession gameSession = new GameSession();
        gameSession.setId(id);
        gameSession.setPlayerCount(playerCount);
        gameSessionDao.save(gameSession);
    }

    @Nullable
    @Transactional
    public User getLoggedIn(@NotNull String name) {
        return userDao.getByName(name);
    }
}
