package ru.atom.lecture08.websocket.dao;

import org.springframework.stereotype.Repository;
import ru.atom.lecture08.websocket.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
@Repository
public class MessageDao {

    @PersistenceContext
    private EntityManager em;

    public void save(Message msg){
        em.persist(msg);
    }

}
