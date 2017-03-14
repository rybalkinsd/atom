package ru.atom.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.data.Like;
import ru.atom.model.data.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class LikesDao implements Dao<Like> {
    private static final Logger log = LogManager.getLogger(LikesDao.class);
    private static final String SELECT_ALL_LIKES =
            "SELECT * FROM likes;";

    private static final String INSERT_LIKE_TEMPLATE =
            "INSERT INTO likes (source, target) VALUES (%d, %d);";

    @Override
    public List<Like> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Like> getAllWhere(String... conditions) {
        throw new NotImplementedException();
    }

    /**
     * @param like
     */
    @Override
    public void insert(Like like) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(INSERT_LIKE_TEMPLATE, like.getSource(), like.getTarget()));
        } catch (SQLException e) {
            log.error("Failed to add like {}", like, e);
        }
    }

    /**
     * task
     * @return
     */
    public List<Match> getMatches() {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(
                    "SELECT l1.source AS source, l1.target AS target " +
                            "FROM (likes AS l1 INNER JOIN likes AS l2 ON l1.target = l2.source)" +
                            "WHERE l1.source = l2.target AND l1.source < l1.target;");

            List<Match> matches = new ArrayList<>();
            while (rs.next()) {
                matches.add(
                        new Match(rs.getInt("source"), rs.getInt("target"))
                );
            }
            return matches;
        } catch (SQLException e) {
            log.error("Get matches failed.",  e);
            return Collections.emptyList();
        }
    }
}


