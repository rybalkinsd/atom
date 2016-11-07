package model.dao.leaderboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.Dao;
import model.dao.UserDao;
import model.data.LeaderBoardRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

/**
 * Created by svuatoslav on 11/6/16.
 */
public class LeaderBoardDao implements Dao<Integer> {
    private static final Logger log = LogManager.getLogger(LeaderBoardDao.class);
    private static final String SELECT_N_RECORDS =
            "SELECT * FROM leaderboard order by score desc LIMIT %d";

    private static final String INSERT_RECORD_TEMPLATE =
            "INSERT INTO leaderboard (\"user\", score) VALUES (%d, %d);";

    private static final String GET_RECORD_TEMPLATE =
            "SELECT * FROM leaderboard WHERE \"user\"=%d;";

    private static final String GET_NAME_TEMPLATE =
            "SELECT \"user\" FROM leaderboard WHERE \"user\"=%d;";
    private static final String GET_SCORE_TEMPLATE =
            "SELECT score FROM leaderboard WHERE \"user\"=%d;";

    private static final String UPDATE_SCORE_TEMPLATE =
            "UPDATE leaderboard SET score=%d WHERE \"user\"=%d;";

    private static final String CREATE_LEADERBOARD_TEMPLATE = "CREATE TABLE IF NOT EXISTS leaderboard " +
            "(" +
            "  \"user\" integer PRIMARY KEY NOT NULL," +
            "  score integer NOT NULL )";

    private static final String DELETE_RECORD_TEMPLATE =
            "DELETE FROM leaderboard WHERE \"user\"=%s;";

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<Integer> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Integer> getAllWhere(String... conditions) {
        throw new NotImplementedException();
    }

    /**
     * @param name
     */
    @Override
    public void insert(Integer name) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            createTable(con);
            Double b = random()*1000000;
            stm.execute(String.format(INSERT_RECORD_TEMPLATE, name, b.intValue() ));
        } catch (SQLException e) {
            log.error("Failed to add record {}", name, e);
        }
    }

    private void createTable(Connection con) throws SQLException
    {
        Statement stm = con.createStatement();
        stm.execute(CREATE_LEADERBOARD_TEMPLATE);
    }

    public String getN(int N) throws JsonProcessingException
    {
        List<LeaderBoardRecord> records = new ArrayList<>();
        UserDao ud = new UserDao();

        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(SELECT_N_RECORDS,N));
            while (rs.next()) {
                Integer id = rs.getInt("user");
                String name = ud.getAllWhere("id ="+ id).get(0).getName();
                records.add(new LeaderBoardRecord(name,rs.getInt("score")));
            }
        } catch (SQLException e) {
            log.error("Failed to getN.", e);
            return "";
        }

        return mapper.writeValueAsString(records);
    }

    public void addPoints(int user, int points)
    {
        int OldScore;
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(GET_SCORE_TEMPLATE, user));
            while (rs.next()){
                OldScore=rs.getInt("score");
                stm.executeQuery(String.format(UPDATE_SCORE_TEMPLATE, OldScore + points, user));
            }
        } catch (SQLException e) {
            log.error("Failed to give points to {}.", user,e);
        }
    }

    @Override
    public void delete (Integer name)
    {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.executeQuery(String.format(DELETE_RECORD_TEMPLATE, name));
        } catch (SQLException e) {
            log.error("Failed to delete {}.", name,e);
        }
    }
}
