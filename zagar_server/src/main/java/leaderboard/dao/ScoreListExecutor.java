package leaderboard.dao;

import accountserver.dao.exceptions.DaoException;
import leaderboard.model.Score;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eugene on 12/16/16.
 */
public class ScoreListExecutor implements ResultSetExecutor<List<Score>> {
    @Override
    public List<Score> execute(ResultSet in) throws DaoException {
        List<Score> scores = new ArrayList<>();
        Score score;
        try {
            while (in.next()){
                score = new Score(in.getInt(2), in.getInt(3));
                scores.add(score);
            }
            return scores;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
