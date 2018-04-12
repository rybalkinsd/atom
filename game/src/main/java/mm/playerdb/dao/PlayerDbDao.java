package mm.playerdb.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import javax.persistence.*;
import javax.persistence.EntityManager;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

@Transactional
@Component
public class PlayerDbDao {
    @PersistenceContext
    private EntityManager em;

    public static PlayerDbDao getInstance() {
        return new PlayerDbDao();
    }

    private static final Logger log = LoggerFactory.getLogger(PlayerDbDao.class);

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

    public void add(Player player) {
        log.info("{} Registered", player.getName());
        em.persist(player);
    }

    public void changeRating(Player player,int ratingChange) {
        em.createQuery("UPDATE players_db SET rating = "
                + (player.getRating() - ratingChange) + " WHERE login = "
                + player.getName() + ";");
    }

}