package ru.atom.lecture07.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture07.server.model.OnlineState;
import ru.atom.lecture07.server.model.User;

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
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User getByLogin(String login) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> from = criteria.from(User.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("login"), login));
        TypedQuery<User> typed = em.createQuery(criteria);
        User user;
        try {
            user = typed.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return user;
    }

    @Override
    public OnlineState getStateByLogin(String login) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<OnlineState> criteria = builder.createQuery(OnlineState.class);
        Root<OnlineState> from = criteria.from(OnlineState.class);
        criteria.select(from);
        criteria.where(builder.equal(from.get("login"), login));
        TypedQuery<OnlineState> typed = em.createQuery(criteria);
        OnlineState state;
        try {
            state = typed.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return state;
    }

    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void saveState(OnlineState state) {
        em.persist(state);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("Select t from " + User.class.getSimpleName() + " t").getResultList();
    }

    @Override
    public void delete(User user) {
        em.remove(user);
    }

    @Override
    public void leave(OnlineState state) {
        em.remove(state);
    }
}
