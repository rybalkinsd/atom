package ru.atom.model.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.model.data.Match;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class MatchDao implements Dao<Match> {
    private static final Logger log = LogManager.getLogger(MatchDao.class);

    @Override
    public List<Match> getAll() {
        throw new NotImplementedException();
    }

    @Override
    public List<Match> getAllWhere(String... hqlConditions) {
        throw new NotImplementedException();
    }

    @Override
    public void insert(Match match) {
    }
}
