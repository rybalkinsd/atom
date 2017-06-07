package dbhackaton.dao;

import dbhackaton.model.Token;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import dbhackaton.model.User;

import java.util.List;

/**
 * Created by ilnur on 17.04.17.
 */
public class TokenDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static TokenDao instance = new TokenDao();

    public static TokenDao getInstance() {
        return instance;
    }

    private TokenDao() {
    }

    public List<User> getAll(Session session) {
        return session.createCriteria(Token.class).list();
    }

    public void insert(Session session, Token token) {
        session.saveOrUpdate(token);
    }
}
