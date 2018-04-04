package ru.atom.lecture07.server.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture07.server.model.Message;
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
public class MessageDaoImpl implements MessageDao {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(Message user) {
        em.persist(user);
    }

    @Override
    public List<Message> findAll() {
        return em.createQuery("Select t from " + Message.class.getSimpleName() + " t").getResultList();
    }
}
