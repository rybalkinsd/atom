package ru.atom.lecture07.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture07.server.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by imakarycheva on 09.04.18.
 */
@Transactional
@Repository
public class MessageDaoImpl implements MessageDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Message msg) {
        em.persist(msg);
    }

    @Override
    public List<Message> findAll() {
        return em.createQuery("Select t from " + Message.class.getSimpleName() + " t").getResultList();
    }
}
