package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import  model.data.User;

import java.util.Arrays;
import java.util.List;

/**
 * Created by venik on 04.11.16.
 */
public class UserDao implements Dao<User>{
    private static final Logger log = LogManager.getLogger(UserDao.class);

    @Override
    public List<User> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from Users").list());
    }

    @Override
    public List<User> getAllWhere(String... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Users where " + totalCondition).list());
    }

    /**
     * @param user
     */
    @Override
    public void insert(User user) {
        Database.doTransactional(session -> session.save(user));
    }

}
