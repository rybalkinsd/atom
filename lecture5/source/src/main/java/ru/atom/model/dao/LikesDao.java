package ru.atom.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.data.Like;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;


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

    @Override
    public Optional<Like> findById(int id) {
        throw new NotImplementedException();
    }

    @Override
    public void insert(Like like) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(INSERT_LIKE_TEMPLATE, like.getSource(), like.getTarget()));
        } catch (SQLException e) {
            log.error("Failed to add like {}", like, e);
        }
    }

}


