package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import model.Token;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by s on 10.11.16.
 */
public class TokenDao implements Dao<Token> {

    @Override
    public List<Token> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from Token").list());
    }

    @Override
    public List<Token> getAllWhere(String... sqlCondidtions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(sqlCondidtions));
        return Database.selectTransactional(session ->session.createQuery("from Token where " + totalCondition).list());
    }

    @Override
    public void insert(Token token) {
        Database.doTransactional(session -> session.save(token));
    }

    @Override
    public void clear(){
        Database.doTransactional(session -> session.createQuery("delete from Token").executeUpdate());
    }

    @Override
    public void remove(Token token) {
        Database.doTransactional(session -> {session.delete(token); return null;});
    }

    @Override
    public void update(Token token) {
        Database.doTransactional(session -> {session.update(token); return null;});
    }

    @Override
    public Optional<Token> findById(Long id) {
        return Optional.ofNullable(
                getAllWhere("id=" + id).get(0)
        );
    }
}
