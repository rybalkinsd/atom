package ru.atom.lecture08.websocket.dao;

import ru.atom.lecture08.websocket.message.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class MessageDao {

    @PersistenceContext
    private EntityManager em;

    public void save(Message msg){
        em.persist(msg);
    }

}
