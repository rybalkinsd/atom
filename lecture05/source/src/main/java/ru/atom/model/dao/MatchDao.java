package ru.atom.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.data.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by s.rybalkin on 17.10.2016.
 */
public class MatchDao implements Dao<Match> {
    private static final Logger log = LogManager.getLogger(MatchDao.class);

    @Override
    public List<Match> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Match> getAllWhere(String... conditions) {
        throw new NotImplementedException();
    }

    @Override
    public void insert(Match match) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format("INSERT INTO matches (a, b) VALUES(%d, %d);", match.getA(), match.getB()));
        } catch (SQLException e) {
            log.error("Failed to add match {}", match, e);
        }
    }
}
