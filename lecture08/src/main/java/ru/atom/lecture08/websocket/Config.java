package ru.atom.lecture08.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.atom.lecture08.websocket.model.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
@EnableWebSocket
public class Config implements WebSocketConfigurer {

    @Autowired
    private ApplicationContext ctx;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(ctx.getBean(EventHandler.class), "/events")
                .setAllowedOrigins("*")
                .withSockJS()
        ;
    }


    @Bean(name = "msgQueue")
    public BlockingQueue getArrayList() {
        LinkedBlockingQueue<Message> queue = new LinkedBlockingQueue<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> messageCriteria = cb.createQuery(Message.class);
        Root<Message> messageRoot = messageCriteria.from(Message.class);
        messageCriteria.select(messageRoot);
        messageCriteria.orderBy(cb.asc(messageRoot.get("time")));
        List<Message> response = em.createQuery(messageCriteria).getResultList();
        int num = Math.min(100,response.size());
        for (int i = 0; i < num; i++)
            queue.offer(response.get(response.size() - num + i));
        return queue;
    }

    @Bean(name = "saveQueue")
    public BlockingQueue<Message> getSaveQueue() {
        return new LinkedBlockingQueue();
    }

}