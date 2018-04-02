package matchmaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by imakarychev on 01.04.18.
 */
@Repository
public class MatchMakerRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MatchMakerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Returns user's rank. If it is a new user, adds new user to DB and returns 0.
     */
    public int getUserRank(String userName) {
        Object[] param = {userName};
        List<Integer> resultList = jdbcTemplate.query("SELECT rank FROM users WHERE login = ?", param,
                (rs, rowNum) -> rs.getInt("rank"));
        if (resultList.size() == 0) {
            saveLogin(userName);
            return 0;
        }
        return resultList.get(0);
    }

    public void saveGameSession(long sesionId, String[] userLogins) {
        Object[] sessionData = {sesionId, new Date()};
        jdbcTemplate.update("INSERT INTO game_sessions (id, start_date_time) VALUES (?, ?)", sessionData);

        List<Object[]> params = new ArrayList<>(userLogins.length);
        for (String login : userLogins) {
            Object[] param = {sesionId, login};
            params.add(param);
        }
        jdbcTemplate.batchUpdate("INSERT INTO game_sessions_to_users (game_session_id, user_login) VALUES (?, ?)",
                params);
    }

    private void saveLogin(String login) {
        Object[] param = {login};
        jdbcTemplate.update("INSERT INTO users (login) VALUES (?)", param);
    }
}
