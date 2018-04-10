package ru.atom.lecture07.server.dao;

import org.springframework.stereotype.Repository;
import ru.atom.lecture07.server.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

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
        return em.createQuery("Select m from " + Message.class.getSimpleName() + " m " + " order by time")
                .getResultList();
    }
}
