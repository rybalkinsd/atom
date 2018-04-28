package mm.playerdb.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;


import javax.persistence.*;
import javax.persistence.EntityManager;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;

@Repository
@Transactional
public class PlayerDbDao {
    @PersistenceContext
    private EntityManager em;

    public static PlayerDbDao getInstance() {
        return new PlayerDbDao();
    }

    private static final Logger log = LoggerFactory.getLogger(PlayerDbDao.class);

    @Transactional
    public Player get(String login) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Player> criteria = builder.createQuery(Player.class);
        Root<Player> from = criteria.from(Player.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("login"), login));
        TypedQuery<Player> typed = em.createQuery(criteria);
        Player player;
        try {
            player = typed.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return player;
    }

    @Transactional
    public void add(Player player) {
        log.info("{} Registered", player.getName());
        em.persist(player);
    }

    @Transactional
    public void changeRating(Player player,int ratingChange) {
        Player fromDb = get(player.getName());
        fromDb.changeRating(ratingChange);
        em.flush();
    }

    /* returns all players sorted by rating*/
    @Transactional
    public ArrayList<Player> getAll(){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Player> criteria = builder.createQuery(Player.class);
        Root<Player> from = criteria.from(Player.class);
        criteria.select(from);
        TypedQuery<Player> typed = em.createQuery(criteria);
        try {
            ArrayList<Player> result = new ArrayList<Player>(typed.getResultList());
            result.sort(new PlayerRatingComparator());
            return result;
        } catch (NoResultException e) {
            return null;
        }
    }

    private class PlayerRatingComparator implements Comparator<Player> {
        public int compare(Player a, Player b){
            return b.compareTo(a);
        }
    }
}