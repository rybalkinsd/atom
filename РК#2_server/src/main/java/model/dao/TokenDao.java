package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import model.data.Token;
import model.data.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by venik on 04.11.16.
 */
public class TokenDao implements Dao<Token>{
    private static final Logger log = LogManager.getLogger(UserDao.class);

    @Override
    public List<Token> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from Tokens").list());
    }

    @Override
    public List<Token> getAllWhere(String... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Tokens where " + totalCondition).list());
    }

    /**
     * @param token
     */
    @Override
    public void insert(Token token) {
        Database.doTransactional(session -> session.save(token));
    }

}
