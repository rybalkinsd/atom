package ru.atom.lecture08.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture08.websocket.model.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.concurrent.BlockingQueue;

@Component
public class MessageCollector implements Runnable {

    @Resource(name = "saveQueue")
    private BlockingQueue<Message> queue;

    @Autowired
    private EntityManager em;

    @PostConstruct
    public void startCollecting() {
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    @Transactional
    public void run() {
        EntityTransaction tx;
        while (!Thread.currentThread().isInterrupted()) {
            if (queue.size() > 30) {
                write(queue);
            }
            try {
                Thread.sleep(10_000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Transactional
    private void write(BlockingQueue<Message> queue) {
        for (int i = 0; i < 30; i++)
            em.persist(queue.poll());
    }


    @PreDestroy
    public void endCollecting() {
        EntityTransaction tx;
        tx = em.getTransaction();
        tx.begin();
        for (int i = 0; i < 30; i++)
            em.persist(queue.poll());
        tx.commit();
    }
}
