package ru.atom.matchmaker.service;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.atom.matchmaker.dao.GameDao;
import ru.atom.matchmaker.dao.GameStatusDao;
import ru.atom.matchmaker.dao.PlayerDao;
import ru.atom.matchmaker.dao.PlayerStatusDao;
import ru.atom.matchmaker.model.Game;
import ru.atom.matchmaker.model.Player;
import ru.atom.matchmaker.model.PlayerStatus;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by Alexandr on 25.11.2017.
 */
@Service
public class DatabaseService {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    @Autowired
    private GameDao gameDao;
    @Autowired
    private GameStatusDao gameStatusDao;
    @Autowired
    private PlayerDao playerDao;
    @Autowired
    private PlayerStatusDao playerStatusDao;

    @Transactional
    public boolean checkSignupLogin(@NotNull String login) {
        logger.info("check sign up");
        return playerDao.getByLogin(login) != null;
    }

    @Transactional
    public void signUp(@NotNull String login, @NotNull String password) {
        logger.info("sign up begin");
        Player player = new Player();
        player.setLogin(login)
                .setPassword(password)
                .setStatus(playerStatusDao.findOne(2))
                .setWins(0);
        playerDao.save(player);
        logger.info("sign up end");
    }

    @Nullable
    @Transactional
    public Player login(@NotNull String login, @NotNull String password) {
        Player player = playerDao.getByLoginAndPassword(login, password);
        if (player != null) {
            player.setStatus(playerStatusDao.findOne(1));
            playerDao.save(player);
        }
        return player;
    }

    @Transactional
    public void logout(@NotNull Player player) {
        player.setStatus(playerStatusDao.findOne(2));
        playerDao.save(player);
    }

    @Transactional
    public void insertNewGame(long gameId, Set<String> logins) {
        Set<Player> players = playerDao.findByLoginIn(logins);
        Game game = new Game();
        game.setId(gameId).setPlayers(players).setStatus(gameStatusDao.findOne(2));
        gameDao.save(game);
        logger.info("insert new game with id=" + gameId);
    }

    @Transactional
    public String getTop() {
        Set<Player> players = playerDao.findTop10ByOrderByWinsDesc();
        logger.info("get top players");
        return players.stream().map(player -> player.getLogin() + "=" + player.getWins()).collect(Collectors.joining(", "));
    }

    @Transactional
    public String getOnline() {
        PlayerStatus playerStatus = new PlayerStatus();
        playerStatus.setId(1);
        Set<Player> players = playerDao.findByStatusEquals(playerStatus);
        logger.info("get online players");
        return players.stream().map(player -> player.getLogin()).collect(Collectors.joining(", "));
    }
}
