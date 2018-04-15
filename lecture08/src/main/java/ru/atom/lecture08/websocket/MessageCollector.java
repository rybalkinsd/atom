package ru.atom.lecture08.websocket;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.atom.lecture08.websocket.model.Message;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.persistence.*;
import java.util.concurrent.BlockingQueue;

@Component
public class MessageCollector implements Runnable {

    @Resource(name = "saveQueue")
    private BlockingQueue<Message> queue;

    @PersistenceUnit
    private EntityManagerFactory factory;

    private EntityManager em;

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
                } catch (Exception e){
                    System.out.println("Fail!");
                }
            }
            try {
                Thread.sleep(10_000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private void write(BlockingQueue<Message> queue){
        for (int i = 0; i < 30; i++) {
            em.persist(queue.poll());
            System.out.println("persisted!");
        }
    }


    @PreDestroy
    public void endCollecting(){
        utx = em.getTransaction();
        utx.begin();
        for (int i = 0; i < 30; i++)
            em.persist(queue.poll());
        utx.commit();
    }
}
