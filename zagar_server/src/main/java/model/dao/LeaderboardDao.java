package model.dao;

import model.LeaderBoard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by s on 12.11.16.
 */
public class LeaderboardDao implements Dao<LeaderBoard> {
    private static final Logger log = LogManager.getLogger(LeaderboardDao.class);
    private static final String SELECT_ALL_LEADERS =
            "SELECT * FROM leaderboard;";
    private static final String DELETE_ALL_LEADERS =
            "DELETE FROM leaderboard;";
    private static final String INSERT_LEADER_TEMPLATE =
            "INSERT INTO Leaderboard (id_user, score) VALUES (%d, %d);";
    private static final String DELETE_LEADER_TEMPLATE =
            "DELETE FROM Leaderboard WHERE id_user = %d;";
    private static final String UPDATE_LEADER_TEMPLATE =
            "UPDATE Leaderboard SET score = %d WHERE id_user = %d;";
    private static final String SELECT_N_LEADERS_TEMPLATE =
            "SELECT * FROM Leaderboard ORDER BY score DESC LIMIT %d;";

    @Override
    public List<LeaderBoard> getAll() {
        try (Connection con = DBConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_LEADERS);
            List<LeaderBoard> leaders = new ArrayList<>();
            while (rs.next()) {
                leaders.add(
                        new LeaderBoard(rs.getLong("id_user"), rs.getLong("score"))
                );
            }
            return leaders;
        } catch (SQLException e) {
            log.error("Get leaders failed.",  e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<LeaderBoard> getAllWhere(String... hqlCondidtions) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void insert(LeaderBoard leaderBoard) {
        try (Connection con = DBConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(INSERT_LEADER_TEMPLATE, leaderBoard.getId_user(), leaderBoard.getScore()));
        } catch (SQLException e) {
            log.error("Insert leader failed.",  e);
        }
    }

    @Override
    public void clear() {
        try (Connection con = DBConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(DELETE_ALL_LEADERS);
        } catch (SQLException e) {
            log.error("Clear leaders failed.",  e);
        }
    }

    @Override
    public void remove(LeaderBoard leaderBoard) {
        try (Connection con = DBConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(DELETE_LEADER_TEMPLATE, leaderBoard.getId_user()));
        } catch (SQLException e) {
            log.error("Remove leader failed.",  e);
        }
    }

    @Override
    public void update(LeaderBoard leaderBoard) {
        try (Connection con = DBConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(UPDATE_LEADER_TEMPLATE, leaderBoard.getScore(), leaderBoard.getId_user()));
        } catch (SQLException e) {
            log.error("Update leader failed.",  e);
        }

    }

    @Override
    public Optional<LeaderBoard> findById(Long id) {
        throw new UnsupportedOperationException();
    }

    public List<LeaderBoard> getFirstN(int N) {
        try (Connection con = DBConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(SELECT_N_LEADERS_TEMPLATE, N));
            List<LeaderBoard> leaders = new ArrayList<>();
            while (rs.next()) {
                leaders.add(
                        new LeaderBoard(rs.getLong("id_user"), rs.getLong("score"))
                );
            }
            return leaders;
        } catch (SQLException e) {
            log.error("Get N leaders failed.",  e);
            return Collections.emptyList();
        }
    }
}
