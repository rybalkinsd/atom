package ru.atom.dbhackaton.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.dao.Database;
import ru.atom.dbhackaton.dao.TokenDao;

import java.util.List;
import java.util.Map;

/**
 * Created by BBPax on 24.03.17.
 */
public class TokenStorage extends AbstractStorage<Long, Token> {
    private static final Logger log = LogManager.getLogger(TokenStorage.class);
    public Long getToken(User user) {
        for (Map.Entry<Long, Token> entry : memory.entrySet()) {
            if (entry.getValue().getUser().equals(user)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean validToken(String token) {
        Long tokenN = Long.parseLong(token);
        return memory.get(tokenN) != null;
    }

    public TokenStorage setUp() {
        Transaction txn = null;
        List<Token> db;
        try (Session session = Database.session()) {
            txn = session.beginTransaction();
            db = TokenDao.getInstance().getAll(session);
            for (Token i : db) {
                memory.put(i.getToken(), i);
            }
            txn.commit();
        } catch (RuntimeException e) {
            log.info("DataBase download failed");
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
        return this;
    }

    @Override
    public Token remove(Long aLong) throws NullPointerException {
        if (memory.get(aLong) == null) {
            throw new NullPointerException("not logined");
        }
        return super.remove(aLong);
    }
}
