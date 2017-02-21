package accountserver.database.leaderboard;

import accountserver.database.users.User;
import accountserver.database.users.UserDao;
import main.ApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import utils.SortedByValueMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Klissan on 06.11.2016.
 * JdbcLeaderboardStorage
 */
public class JdbcLeaderboardStorage
        implements LeaderboardDao {
    private static final Logger log = LogManager.getLogger(LeaderboardDao.class);

    @Override
    public void addUser(@NotNull User user) {
        final String query =
                "INSERT INTO leaderboard (user_id) " +
                        "VALUES (%d);";
        try (Connection con = JdbcDbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(query, user.getId()));
        } catch (SQLException e) {
            log.error("Failed to add user with id {}", user.getId(), e);
        }
    }

    /*
        UPDATE leaderboard
        SET score = score + add
        WHERE user = userId;
        */
    @Override
    public void updateScore(@NotNull User user, int scoreToAdd) {
        final String query =
                "UPDATE leaderboard " +
                        "SET score = score + %d " +
                        "WHERE user_id = %d;";
        try (Connection con = JdbcDbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(query, scoreToAdd, user.getId()));
        } catch (SQLException e) {
            log.error("Failed to add score {} to user with id {}", scoreToAdd, user.getId(), e);
        }
    }

    /*
    SELECT TOP count * FROM leaderboard
    ORDER BY score
    */
    @Override
    @NotNull
    public Map<User, Integer> getTopUsers(int count) {
        final String query =
                "SELECT * FROM leaderboard " +
                        "ORDER BY score DESC " +
                        "LIMIT %d;";

        Map<User, Integer> leaders = new HashMap<>();
        try (Connection con = JdbcDbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(query, count));

            while (rs.next()) {
                leaders.put(
                        ApplicationContext.instance()
                                .get(UserDao.class)
                                .getUserById(rs.getInt("user_id")),
                        rs.getInt("score")
                );
            }
            return SortedByValueMap.sortByValues(leaders);
        } catch (SQLException e) {
            log.error("Get leaders failed.", e);
            return leaders;
        }
    }

    @Override
    public void removeUser(@NotNull User user) {
        final String query =
                "DELETE FROM leaderboard " +
                        "WHERE user_id = %d";
        try (Connection con = JdbcDbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(query, user.getId()));
        } catch (SQLException e) {
            log.error("Remove user '{}' failed.", user.getId(), e);
        }
    }
}
