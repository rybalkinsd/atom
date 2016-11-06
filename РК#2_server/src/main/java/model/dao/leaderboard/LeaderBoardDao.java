package model.dao.leaderboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dao.Dao;
import model.data.LeaderBoardRecord;
import model.server.api.LeaderBoardProvider;
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
public class LeaderBoardDao implements Dao<String> {
    private static final Logger log = LogManager.getLogger(LeaderBoardDao.class);
    private static final String SELECT_N_RECORDS =
            "SELECT * FROM leaderboard order by score desc LIMIT %d";

    private static final String INSERT_RECORD_TEMPLATE =
            "INSERT INTO leaderboard (\"user\", score) VALUES ('%s', %d);";

    private static final String GET_RECORD_TEMPLATE =
            "SELECT * FROM leaderboard WHERE \"user\"='%s';";

    private static final String UPDATE_RECORD_TEMPLATE =
            "UPDATE leaderboard SET score=%d WHERE \"user\"='%s';";

    private static final String CREATE_LEADERBOARD_TEMPLATE = "CREATE TABLE IF NOT EXISTS leaderboard " +
            "( id serial NOT NULL," +
            "  \"user\" character varying(100) NOT NULL," +
            "  score integer NOT NULL )";

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<String> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<String> getAllWhere(String... conditions) {
        throw new NotImplementedException();
    }

    /**
     * @param name
     */
    @Override
    public void insert(String name) {
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

        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(SELECT_N_RECORDS,N));
            while (rs.next()) {
               records.add(mapToRecord(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getN.", e);
            return "";
        }

        return mapper.writeValueAsString(records);
    }

    public void addPoints(String user, int points)
    {
        LeaderBoardRecord record;
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(GET_RECORD_TEMPLATE, user));
            while (rs.next()){
                record = mapToRecord(rs);
                stm.executeQuery(String.format(UPDATE_RECORD_TEMPLATE, record.getScore() + points, user));
           }
        } catch (SQLException e) {
            log.error("Failed to give points to {}.", user,e);
        }
    }

    private LeaderBoardRecord mapToRecord(ResultSet rs) throws SQLException {
        return new LeaderBoardRecord(rs.getString("user"),rs.getInt("score"));
    }
}
