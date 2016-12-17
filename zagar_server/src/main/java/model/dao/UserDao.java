package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by s on 10.11.16.
 */
public class UserDao implements Dao<User> {
    @Override
    public List<User> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from User").list());
    }

    @Override
    public List<User> getAllWhere(String... sqlCondidtions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(sqlCondidtions));
        return Database.selectTransactional(session ->session.createQuery("from User where " + totalCondition).list());
    }

    @Override
    public void insert(User user) {
        Database.doTransactional(session -> session.save(user));
    }

    @Override
    public void clear() {
        Database.doTransactional(session -> session.createQuery("delete from User").executeUpdate());
    }

    @Override
    public void remove(User user) {
        Database.doTransactional(session -> {session.update(user); return null;});
    }

    @Override
    public void update(User user) {
        Database.doTransactional(session -> {session.update(user); return null;});
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(
                getAllWhere("id=" + id).get(0)
        );
    }

}
