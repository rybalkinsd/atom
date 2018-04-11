package ru.atom.lecture07.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture07.server.model.Message;
import ru.atom.lecture07.server.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class MessageDaoImpl implements MessageDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Message message) {
        em.persist(message);
    }

    @Override
    public List<Message> findAll() {
        return em.createQuery("Select t from " + Message.class.getSimpleName() + " t").getResultList();
    }

}
