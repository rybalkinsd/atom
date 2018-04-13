package ru.atom.lecture08.websocket.dao;

import org.springframework.stereotype.Repository;
import ru.atom.lecture08.websocket.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
public class MessageDao {

    @PersistenceContext
    private EntityManager em;

    public void save(Message msg) {
        em.persist(msg);
    }


    public List<Message> loadHistory(Date date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> messageCriteria = cb.createQuery(Message.class);
        Root<Message> messageRoot = messageCriteria.from(Message.class);
        messageCriteria.select(messageRoot);
        messageCriteria.where(cb.greaterThan(messageRoot.get("time"),date));
        messageCriteria.orderBy(cb.asc(messageRoot.get("time")));
        List<Message> result = em.createQuery(messageCriteria).getResultList();
        if (result.size() == 0)
            return null;
        else return result;
    }


    public List<Message> loadHistory() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> messageCriteria = cb.createQuery(Message.class);
        Root<Message> messageRoot = messageCriteria.from(Message.class);
        messageCriteria.select(messageRoot);
        messageCriteria.orderBy(cb.asc(messageRoot.get("time")));
        List<Message> result = em.createQuery(messageCriteria).getResultList();
        if (result.size() == 0)
            return null;
        else return result;
    }


}
