package leaderboard;

import accountserver.dao.exceptions.DaoException;
import leaderboard.dao.ScoreDAO;
import leaderboard.model.Score;
import model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by eugene on 12/16/16.
 */
public class LeaderBoard {
    private final int gsId;
    private final int initialScore;
    private ScoreDAO dao;

    public LeaderBoard(int gsId, int initialScore) throws DaoException {
        this.gsId = gsId;
        this.initialScore = initialScore;

        dao = new ScoreDAO(gsId);
        dao.flush();
    }

    public void registerPlayer(@NotNull Player player) throws DaoException {
        dao.insert(new Score(player.getId(), initialScore));
    }

    public void addPoints(@NotNull Player player, int points) throws DaoException {
        dao.addPoints((long) player.getId(), points);
    }

    public List<Integer> getLeaders(int n) throws DaoException {
        return getLeaders().subList(0, n);
    }

    public List<Integer> getLeaders() throws DaoException {
        return dao.getAll().stream().mapToInt(Score::getPlayerId).boxed().collect(Collectors.toList());
    }
}
