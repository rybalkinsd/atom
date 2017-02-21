package messageSystem;

import main.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by User on 26.11.2016.
 */
public class MessageSystem {
    private final static Logger log = LogManager.getLogger(MessageSystem.class);

    private final Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private final @NotNull Map<Class<?>, Service> services = new ConcurrentHashMap<>();


    public MessageSystem() {
        log.info("message system start");
    }

    public void registerService(Class<?> type, Service service) {
        services.put(type, service);
        messages.putIfAbsent(service.getAddress(), new ConcurrentLinkedQueue<>());
        log.info(service + " registered");
    }

    public <T extends Service> T getService(Class<T> type){
        return (T)services.get(type);
    }

    public Collection<Service> getServices(){
        return services.values();
    }

    public void sendMessage(Message message) {
        messages.get(message.getTo()).add(message);
    }

    public void executeForService(Service service) {
        ConcurrentLinkedQueue<Message> queue = messages.get(service.getAddress());
        while (!queue.isEmpty()) {
            Message message = queue.poll();
            message.execute(service);
        }
    }
}
