package ru.atom.lecture08.websocket.dao;

import org.springframework.stereotype.Repository;
import ru.atom.lecture08.websocket.model.Message;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;


@Transactional
@Repository
public class MessageDao {

    @PersistenceContext
    private EntityManager em;

    @Resource(name = "msgQueue")
    private BlockingQueue<Message> msgQueue;

    @Resource(name = "saveQueue")
    private BlockingQueue<Message> saveQueue;

    public void save(Message msg) {
        saveQueue.offer(msg);
        msgQueue.offer(msg);
        if (msgQueue.size() > 100)
            msgQueue.poll();
    }


    public String update(Date date) {
        if (!msgQueue.peek().isLaterThan(date)) {
            return msgQueue.stream()
                    .filter(e -> e.isLaterThan(date))
                    .map(Message::getData)
                    .reduce("",(e1,e2) -> e1 + "\n" + e2);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> messageCriteria = cb.createQuery(Message.class);
        Root<Message> messageRoot = messageCriteria.from(Message.class);
        messageCriteria.select(messageRoot);
        messageCriteria.where(cb.greaterThan(messageRoot.get("time"),date));
        messageCriteria.orderBy(cb.asc(messageRoot.get("time")));
        List<Message> result = em.createQuery(messageCriteria).getResultList();
        if (result.size() == 0)
            return "";
        else return result.stream().filter(e -> e.isLaterThan(date))
                .map(Message::getData)
                .reduce("", (e1,e2) -> e1 + "\n" + e2);
    }

    public List<String> updateGetList(Date date) {
        if (!msgQueue.peek().isLaterThan(date)) {
            return msgQueue.stream()
                    .filter(e -> e.isLaterThan(date))
                    .map(Message::getData)
                    .collect(Collectors.toList());
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> messageCriteria = cb.createQuery(Message.class);
        Root<Message> messageRoot = messageCriteria.from(Message.class);
        messageCriteria.select(messageRoot);
        messageCriteria.where(cb.greaterThan(messageRoot.get("time"),date));
        messageCriteria.orderBy(cb.asc(messageRoot.get("time")));
        return em.createQuery(messageCriteria).getResultList().stream()
                .filter(e -> e.isLaterThan(date))
                .map(Message::getData)
                .collect(Collectors.toList());
    }


    public BlockingQueue<Message> loadHistory() {
        return msgQueue;
    }

    /*CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> messageCriteria = cb.createQuery(Message.class);
        Root<Message> messageRoot = messageCriteria.from(Message.class);
        messageCriteria.select(messageRoot);
        messageCriteria.orderBy(cb.asc(messageRoot.get("time")));
        List<Message> result = em.createQuery(messageCriteria).getResultList();
        if (result.size() == 0)
            return null;
        else return result; - Method for getting the whole history in chat*/

    public List<Message> getHistory(Date date) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> messageCriteria = cb.createQuery(Message.class);
        Root<Message> messageRoot = messageCriteria.from(Message.class);
        messageCriteria.select(messageRoot);
        messageCriteria.where(cb.lessThan(messageRoot.get("time"),date));
        messageCriteria.orderBy(cb.asc(messageRoot.get("time")));
        return em.createQuery(messageCriteria).getResultList();
    }

}
