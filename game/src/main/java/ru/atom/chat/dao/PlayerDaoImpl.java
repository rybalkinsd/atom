package ru.atom.chat.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.chat.user.Player;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
@Repository
public class PlayerDaoImpl implements PlayerDao {
    @PersistenceContext
    private EntityManager em;

    private static Logger log = LoggerFactory.getLogger(PlayerDaoImpl.class);


    @Override
    public Player getByLogin(String login) {
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

    /*
    @Override
    public List<user> getByActive(Boolean active) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<user> criteria = builder.createQuery(user.class);
        Root<user> from = criteria.from(user.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("active"), active));
        TypedQuery<user> typed = em.createQuery(criteria);
        List<user> onlineUsers;
        try {
            onlineUsers = typed.getResultList();
        } catch (NoResultException e) {
            onlineUsers = new ArrayList<>();
        }
        return onlineUsers;
    }
    */

    @Override
    public void save(Player player) {
        em.persist(player);
    }

    @Override
    public void update(Player player) {
        em.persist(player);
    }

    @Override
    public void delete(Player player) {
        // em.remove(player);
        log.warn("I dont want to delete users");
    }

    @Override
    public List<Player> findAll() {
        return em.createQuery("Select t from " + Player.class.getSimpleName() + " t").getResultList();
    }
}
