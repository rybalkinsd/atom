package messageSystem;

import main.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author e.shubin
 */
public final class MessageSystem {
  private final static Logger log = LogManager.getLogger(MessageSystem.class);

  private final Map<Address, BlockingQueue<Message>> messages = new HashMap<>();
  private final @NotNull Map<Class<?>, Service> services = new ConcurrentHashMap<>();


  public MessageSystem() {
  }

  public void registerService(Class<?> type, Service service) {
    services.put(type, service);
    messages.putIfAbsent(service.getAddress(), new LinkedBlockingQueue<>());
    log.info(service + " registered");
  }

  public <T> T getService(Class<T> type){
    return (T)services.get(type);
  }

  public Collection<Service> getServices(){
    return services.values();
  }

  public void sendMessage(Message message) {
    messages.get(message.getTo()).add(message);
  }

  public void execForService(Service service) {
    BlockingQueue<Message> queue = messages.get(service.getAddress());
    while (!queue.isEmpty()) {
      Message message = queue.poll();
      message.exec(service);
    }
  }

  public void execOneForService(Service service) throws InterruptedException {
    BlockingQueue<Message> queue = this.messages.get(service.getAddress());
    queue.take().exec(service);
  }

  public void execOneForService(Service service, long timeout) throws InterruptedException {
    execOneForService(service, timeout, TimeUnit.MILLISECONDS);
  }

  public void execOneForService(Service service, long timeout, TimeUnit unit) throws InterruptedException {
    BlockingQueue<Message> queue = this.messages.get(service.getAddress());
    Message message = queue.poll(timeout, unit);
    if (message == null) return;
    message.exec(service);
  }
}
