package model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import model.data.Match;
import model.data.Token;
import model.data.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by venik on 04.11.16.
 */
public class MatchDao implements Dao<Match> {
    private static final Logger log = LogManager.getLogger(MatchDao.class);

    @Override
    public List<Match> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from Matches").list());
    }

    @Override
    public List<Match> getAllWhere(String... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Matches where " + totalCondition).list());
    }

    /**
     * @param match
     */
    @Override
    public void insert(Match match) {
        Database.doTransactional(session -> session.save(match));
    }

    @Override
    public void delete (Match match){}


    public void delete (User user){
                Database.doTransactional(session -> session.createQuery("DELETE Matches WHERE users = :user")
                .setParameter("user", user.getId())
                .executeUpdate());
        }

    public void delete (Token token){
        Database.doTransactional(session -> session.createQuery("DELETE Matches WHERE token = :token")
                .setParameter("token", token.getId())
                .executeUpdate());
    }



}