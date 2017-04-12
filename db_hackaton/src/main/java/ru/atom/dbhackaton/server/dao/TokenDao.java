package ru.atom.dbhackaton.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by mkai on 4/12/17.
 */
public class TokenDao {
    private static final Logger log = LogManager.getLogger(TokenDao.class);

    private static TokenDao instance = new TokenDao();

    public static TokenDao getInstance() {
        return instance;
    }

    private TokenDao() {
    }
}
