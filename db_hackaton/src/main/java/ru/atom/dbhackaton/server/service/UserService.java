package ru.atom.dbhackaton.server.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.dbhackaton.server.Dao.Database;
import ru.atom.dbhackaton.server.model.User;

/**
 * Created by pavel on 12.04.17.
 */
public class UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);

    public void register(String login, String password) throws UserException {
        Transaction tnx = null;
        try (Session session = Database.session()){
            tnx = session.beginTransaction();

            if (UserDao.getInstance().getByName(session, login) != null) {
                throw new UserException("Already registered");
            }

            User newUser = new User();
            newUser.setName(login);
            newUser.setPasswordHash(password.hashCode());

            UserDao.getInstance.register(session, newUser);

            tnx.commit();
        } catch (RuntimeException e) {
            if(tnx != null && tnx.isActive()) {
                tnx.rollback();
            }
        }
    }
}
