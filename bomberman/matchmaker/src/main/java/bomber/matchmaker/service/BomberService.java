package bomber.matchmaker.service;


import bomber.matchmaker.connection.Connection;
import bomber.matchmaker.connection.ConnectionQueue;
import bomber.matchmaker.dao.GameSessionDao;
import bomber.matchmaker.dao.UserDao;
import bomber.matchmaker.model.GameSession;
import bomber.matchmaker.model.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.BlockingQueue;

@Service
public class BomberService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BomberService.class);

    @Autowired
    private GameSessionDao gameSessionDao;

    @Autowired
    private UserDao userDao;

    private BlockingQueue<Connection> queue;

    @Transactional
    public void addToDb(@NotNull Integer gameId, @NotNull Date date) {
        queue = ConnectionQueue.getInstance();
        GameSession gameSession = new GameSession(gameId, date);
        gameSession.setGameId(gameId);
        gameSession.setDate(date);
        gameSessionDao.save(gameSession);
        User user = new User();
        for (Connection connection : queue) {
            user.setGameSession(gameSessionDao.getByGameId(gameId));
            user.setId(connection.getPlayerId());
            user.setName(connection.getName());
            userDao.save(user);
        }
        log.info("Added a new line to table called gs : {}", gameSession);
        log.info("Added a new line to table called user: {}", user);
    }
}
