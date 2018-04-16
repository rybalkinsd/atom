package ru.atom.lecture08.websocket;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture08.websocket.model.Message;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.persistence.PersistenceUnit;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.concurrent.BlockingQueue;

@Component
public class MessageCollector implements Runnable {

    @Resource(name = "saveQueue")
    private BlockingQueue<Message> queue;

    @PersistenceUnit
    private EntityManagerFactory factory;

    private EntityManager em;

    private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MessageCollector.class);

    @PostConstruct
    public void startCollecting() {
        Thread thread = new Thread(this);
        thread.start();
    }

    private EntityTransaction utx;

    @Override
    public void run() {
        em = factory.createEntityManager();

        while (!Thread.currentThread().isInterrupted()) {
            if (queue.size() > 30) {
                try {
                    utx = em.getTransaction();
                    utx.begin();
                    write(queue);
                    utx.commit();
                } catch (Exception e) {
                    log.error("Fail!");
                }
            }
            try {
                Thread.sleep(10_000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void write(BlockingQueue<Message> queue) {
        for (int i = 0; i < 30; i++)
            em.persist(queue.poll());

    }


    @PreDestroy
    public void endCollecting() {
        utx = em.getTransaction();
        utx.begin();
        for (int i = 0; i < 30; i++)
            em.persist(queue.poll());
        utx.commit();
    }
}
