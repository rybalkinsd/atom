package matchmaker;


import matchmaker.monitoring.SessionData;
import matchmaker.monitoring.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    int getUserRank(String userName) {
        Object[] param = {userName};
        List<Integer> resultList = jdbcTemplate.query("SELECT rank FROM mm.users WHERE login = ?", param,
            (rs, rowNum) -> rs.getInt("rank"));
        if (resultList.size() == 0) {
            saveLogin(userName);
            return 0;
        }
        return resultList.get(0);
    }


    void saveGameSession(long sesionId, String[] userLogins) {
        Object[] sessionData = {sesionId, new Date()};
        jdbcTemplate.update("INSERT INTO mm.game_sessions (id, start_date_time) VALUES (?, ?)", sessionData);

        List<Object[]> params = new ArrayList<>(userLogins.length);
        for (String login : userLogins) {
            Object[] param = {sesionId, login};
            params.add(param);
        }

        jdbcTemplate.batchUpdate("INSERT INTO mm.game_sessions_to_users (game_session_id, user_id) " +
                        "SELECT ?, id FROM mm.users WHERE login = ?", params);
    }

    void saveLogin(String login) {
        Object[] param = {login};
        jdbcTemplate.update("INSERT INTO mm.users (login) VALUES (?)", param);
    }

    long getLastSessionId() {
        return jdbcTemplate.query("SELECT max(s.id) as res FROM mm.game_sessions s",
            (rs, num) -> rs.getLong("res")).get(0);
    }

    public List<SessionData> getSessionDataList() {
        return jdbcTemplate.query("SELECT s.id, s.start_date_time, u.login " +
                "FROM mm.game_sessions s JOIN mm.game_sessions_to_users stu " +
                "ON s.id = stu.game_session_id JOIN mm.users u ON " +
                "u.id = stu.user_id ORDER BY s.id", new SessionDataExtractor());
    }

    public List<UserData> getUserDataList() {
        return jdbcTemplate.query("select u.id, u.login, u.rank, sum(1) as games from mm.users u " +
                "join mm.game_sessions_to_users su on u.id = su.user_id group by u.id union all " +
                "select u.id, u.login, u.rank, 0 as games from mm.users u left join mm.game_sessions_to_users su " +
                "on u.id = su.user_id where su.game_session_id is null " +
                "order by id",
            (rs, num) -> new UserData(rs.getInt("id"), rs.getString("login"),
                rs.getInt("rank"), rs.getLong("games")));
    }

    class SessionDataExtractor implements ResultSetExtractor<List<SessionData>> {

        @Override
        public List<SessionData> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<SessionData> result = new ArrayList<>();
            while (rs.next()) {
                if (result.size() == 0) {
                    SessionData sd = new SessionData(rs.getLong("id"),
                            rs.getDate("start_date_time"),
                            new ArrayList<>());
                    sd.addPlayer(rs.getString("login"));
                    result.add(sd);
                } else {
                    long id = rs.getLong("id");
                    if (id == result.get(result.size() - 1).getSessionId()) {
                        result.get(result.size() - 1).addPlayer(rs.getString("login"));
                    } else {
                        SessionData sd = new SessionData(id, rs.getDate("start_date_time"),
                                new ArrayList<>());
                        sd.addPlayer(rs.getString("login"));
                        result.add(sd);
                    }
                }
            }
            return result;
        }
    }

}
