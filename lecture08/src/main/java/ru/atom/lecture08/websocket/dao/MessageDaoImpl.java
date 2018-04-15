package ru.atom.lecture08.websocket.dao;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture08.websocket.model.Message;
import ru.atom.lecture08.websocket.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class MessageDaoImpl implements MessageDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void putMessage(String login, String msg, Date date) {
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
            System.out.println("no " + login + " found");
            return;
        }
        Message message = new Message();
        em.persist(message.setUser(user).setValue(msg).setTime(date));
    }

    @Override
    public List<Message> getAll() {
        return em.createQuery("Select t from " + Message.class.getSimpleName() + " t").getResultList();

    }

    @Override
    public Message getLast() {
        return (Message)em.createQuery("Select t from " + Message.class.getSimpleName() + " t order by time desc").setMaxResults(1).getSingleResult();

    }

}


