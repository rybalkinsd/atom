package ru.atom.model.dao;

import jersey.repackaged.com.google.common.base.Joiner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.atom.model.data.Like;
import ru.atom.model.data.Match;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class LikeDao implements Dao<Like> {
    @Override
    public List<Like> getAll() {
        return Database.selectTransactional(session -> session.createQuery("from Like").list());
    }

    @Override
    public List<Like> getAllWhere(String... hqlConditions) {
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from Like where " + totalCondition).list());
    }

    /**
     * @param like
     */
    @Override
    public void insert(Like like) {
        Database.doTransactional(session -> session.save(like));
    }

    /**
     * task
     * @return
     */
    public List<Match> getMatches() {
//        try (Connection con = Database.openSession();
//             Statement stm = con.createStatement()) {
//            ResultSet rs = stm.executeQuery(
//                    "SELECT l1.source AS source, l1.target AS target " +
//                            "FROM (likes AS l1 INNER JOIN likes AS l2 ON l1.target = l2.source)" +
//                            "WHERE l1.source = l2.target AND l1.source < l1.target;");
//
//            List<Match> matches = new ArrayList<>();
//            while (rs.next()) {
//                matches.add(
//                        new Match(rs.getInt("source"), rs.getInt("target"))
//                );
//            }
//            return matches;
//        } catch (SQLException e) {
//            log.error("Get matches failed.",  e);
//            return Collections.emptyList();
//        }
        return Collections.emptyList();
    }
}


